package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;

/**
 * Created by Wallecnik on 30.05.16.
 */
public interface TrackNotificationProcessor {

    void process(TrackNotification notification);
}
