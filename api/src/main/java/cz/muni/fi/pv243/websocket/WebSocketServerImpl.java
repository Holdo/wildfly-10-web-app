package cz.muni.fi.pv243.websocket;

/**
 * @author Diana Vilkolakova
 */
import cz.muni.fi.pv243.jms.TrackNotificationSender;
import cz.muni.fi.pv243.jms.service.DemoService;
import cz.muni.fi.pv243.model.Comment;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.TrackNotification;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Slf4j
@ServerEndpoint(value = "/websocket/{title}")
public class WebSocketServerImpl implements WebSocketServer /*extends ReceiverAdapter*/ {

	//private JChannel clusterChannel;
	private ConcurrentHashMap<String, List<Session>> rooms = new ConcurrentHashMap<>();

	@Inject
	private TrackNotificationSender trackNotificationSender;

	@Inject
	private DemoService demoService;

	/*@PostConstruct
	private void init() {
		try {
			clusterChannel = new JChannel("udp.xml");
			clusterChannel.setReceiver(this);
			clusterChannel.connect("TracksAppCluster");
			clusterChannel.getState(null, 10000);
		} catch (Exception e) {
			log.error("Unable to create JGroups cluster channel", e);
		}
	}

	@PreDestroy
	private void destroy() {
		clusterChannel.close();
	}

	@Override
	public void viewAccepted(View new_view) {
		log.info("New view: " + new_view);
	}

	@Override
	public void receive(Message msg) {
		TrackNotification trackNotification = (TrackNotification) msg.getObject();
		log.info("Received track notification for " + trackNotification.getTitle());
		notifyAllSessions(trackNotification);
	}*/

	@Override
	@OnOpen
	public void open(Session session, @PathParam("title") String title) {
		log.info("New session of {} registered with id {}", session.getUserPrincipal().getName(), session.getId());
		if (!rooms.containsKey(title)) rooms.put(title, new ArrayList<>());
		rooms.get(title).add(session);
		log.info("Current number of rooms " + rooms.size());
		log.info("Current number of sessions in room " + title + ": " + rooms.get(title).size());

		Demo demo = demoService.findDemo(title);
		List<Comment> comments = demo.getComments();
		for (Comment comment : comments) {
			JsonObject jsonComment = Json.createObjectBuilder()
					.add("author", comment.getAuthor())
					.add("comment", comment.getComment())
					.build();
			sendToSession(session, jsonComment);
		}
	}

	@Override
	@OnClose
	public void close(Session session) {
		removeSession(session);
	}

	@Override
	@OnError
	public void onError(Throwable error) {
		log.info("Error: " + error.getMessage());
	}

	@Override
	@OnMessage
	public void handleMessage(final Session session, final String message) throws Exception {
		JsonObject messageJson;
		try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
			messageJson = jsonReader.readObject();
		}
		Comment comment = new Comment();
		comment.setAuthor(messageJson.getString("author"));
		comment.setComment(messageJson.getString("comment"));
		addCommentToDemo(messageJson.getString("title"), comment);

		/*Message msg = new Message(null, null, new TrackNotification(messageJson.getString("title"), messageJson.getString("author"), messageJson.getString("comment")));
		log.info("Sending notification for {} track to cluster", messageJson.getString("title"));
		clusterChannel.send(msg);*/
		trackNotificationSender.sendNotification(new TrackNotification(messageJson.getString("title"), messageJson.getString("author"), messageJson.getString("comment")));
	}

	@Override
	public void notifyAllSessions(TrackNotification trackNotification) {
		log.info("NotifyAllSessions for track " + trackNotification.getTitle());
		JsonObject notification = Json.createObjectBuilder()
				.add("title", trackNotification.getTitle())
				.add("author", trackNotification.getAuthor())
				.add("comment", trackNotification.getComment())
				.build();
		List<Session> sessions = rooms.get(trackNotification.getTitle());
		if (sessions == null) return;
		log.info("Sending update to {} sessions", sessions.size());
		sessions.stream().forEach((sessionForTitle) -> sendToSession(sessionForTitle, notification));
	}

	private void removeSession(Session session) {
		log.info("Removing session of " + session.getUserPrincipal().getName() + " id: " + session.getId());
		rooms.keySet().stream().map((title)
				-> rooms.get(title)).filter((sessionsOpenedForTitle)
				-> (sessionsOpenedForTitle != null))
				.filter((sessionsOpenedForTitle)
						-> (sessionsOpenedForTitle.contains(session)))
				.forEach((sessionsOpenedForTitle) -> {
					sessionsOpenedForTitle.remove(session);
				});
	}

	private void sendToSession(Session session, JsonObject message) {
		if (session.isOpen()) {
			log.info("Sending " + message.toString() + " to session of: " + session.getUserPrincipal().getName());
			try {
				session.getBasicRemote().sendText(message.toString());
			} catch (IOException ex) {
				removeSession(session);
				log.info("Error: " + ex.getMessage());
			}
		}
	}

	private void addCommentToDemo(String title, Comment comment) {
		Demo demo = demoService.findDemo(title);
		List<Comment> comments = demo.getComments();
		comments.add(comment);
		demo.setComments(comments);
		demoService.updateDemo(demo);
	}
}
