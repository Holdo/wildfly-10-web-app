package cz.muni.fi.pv243.websocket;

import cz.muni.fi.pv243.model.TrackNotification;

import javax.websocket.*;
import javax.websocket.server.PathParam;

/**
 * Created by Wallecnik on 30.05.16.
 */
public interface WebSocketServer {
    @OnOpen
    void open(Session session, @PathParam("title") String title);

    @OnClose
    void close(Session session);

    @OnError
    void onError(Throwable error);

    @OnMessage
    void handleMessage(Session session, String message) throws Exception;

    void notifyAllSessions(TrackNotification trackNotification);
}
