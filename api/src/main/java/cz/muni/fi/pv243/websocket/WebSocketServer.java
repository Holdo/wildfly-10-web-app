package cz.muni.fi.pv243.websocket;

/**
 *
 * @author skylar
 */
import cz.muni.fi.pv243.jms.service.DemoService;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Comment;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.server.PathParam;

@Singleton
@ServerEndpoint(value = "/websocket/{title}")
public class WebSocketServer {

	@Inject
	private DemoService demoService;

	private ConcurrentHashMap<String, List<Session>> rooms = new ConcurrentHashMap<>();
	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@OnOpen
	public void open(Session session, @PathParam("title") String title) {
		log.info("New session of {} registered with id {}" + session.getId());
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

	@OnClose
	public void close(Session session) {
		removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
		log.info("Error: " + error.getMessage());
	}

	@OnMessage
	public void handleMessage(final Session session, final String message) {
		JsonObject messageJson;
		try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
			messageJson = jsonReader.readObject();
		}
		Comment comment = new Comment();
		comment.setAuthor(messageJson.getString("author"));
		comment.setComment(messageJson.getString("comment"));
		addCommentToDemo(messageJson.getString("title"), comment);

		List<Session> sessions = rooms.get(messageJson.getString("title"));
		JsonObject notification = Json.createObjectBuilder()
				.add("title", messageJson.getString("title"))
				.add("author", messageJson.getString("author"))
				.add("comment", messageJson.getString("comment"))
				.build();
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
