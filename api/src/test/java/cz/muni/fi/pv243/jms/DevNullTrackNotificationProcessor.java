package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * MOCK emulation
 *
 * @author Tomas Valka
 */
@Slf4j
public class DevNullTrackNotificationProcessor implements TrackNotificationProcessor {

    @Getter
    private static TrackNotification lastItem;

    @Override
    public void process(TrackNotification trackNotification) {
        lastItem = trackNotification;
    }
}
