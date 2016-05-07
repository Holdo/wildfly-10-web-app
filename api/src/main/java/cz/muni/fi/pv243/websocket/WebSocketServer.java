package cz.muni.fi.pv243.websocket;

/**
 *
 * @author skylar
 */
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

    
@ApplicationScoped
@ServerEndpoint("/actions")
public class WebSocketServer {
    @OnOpen
        public void open(Session session) {
    }

    @OnClose
        public void close(Session session) {
    }

    @OnError
        public void onError(Throwable error) {
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
    }
}
