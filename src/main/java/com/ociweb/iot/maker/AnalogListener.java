package com.ociweb.iot.maker;

import com.ociweb.gl.api.Behavior;

/**
 * Functional interface for analog events registered with the
 * {@link FogRuntime}.
 *
 * @author Nathan Tippy
 */
@FunctionalInterface
public interface AnalogListener extends Behavior {

    /**
     * Invoked when the state of an analog device registered with the
     * {@link FogRuntime} changes.
     *
     * @param port {@link Port} of the analog device whose state changed.
     * @param time UNIX timestamp (milliseconds since the epoch) of when this event was received.
     * @param durationMillis TODO: What's this?
     * @param average Average value of the analog device based on TODO: what config?
     * @param value Current value of the analog device.
     */
    void analogEvent(Port port, long time, long durationMillis, int average, int value);
}
