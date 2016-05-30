package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;
import cz.muni.fi.pv243.websocket.WebSocketServer;

import javax.ejb.EJB;

/**
 * Created by Wallecnik on 30.05.16.
 */
public class TrackNotificationProcessorImpl implements TrackNotificationProcessor {

    @EJB
    private WebSocketServer webSocketServer;

    @Override
    public void process(TrackNotification notification) {
        webSocketServer.notifyAllSessions(notification);
    }
}
