package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;
import cz.muni.fi.pv243.websocket.WebSocketServer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Singleton;
import javax.enterprise.inject.Alternative;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

/**
 * MOCK emulation
 *
 * @author Tomas Valka
 */
@Alternative
@Singleton(mappedName = "java:global/test/DevNullWebSocketServer")
@Slf4j
public class DevNullWebSocketServer implements WebSocketServer {

    @Getter
    private static TrackNotification lastItem;

    @Override
    public void open(Session session, @PathParam("title") String title) {

    }

    @Override
    public void close(Session session) {

    }

    @Override
    public void onError(Throwable error) {

    }

    @Override
    public void handleMessage(Session session, String message) throws Exception {

    }

    @Override
    public void notifyAllSessions(TrackNotification trackNotification) {
        lastItem = trackNotification;
    }
}
