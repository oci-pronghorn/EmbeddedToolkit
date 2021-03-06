package com.ociweb.grove;

import static com.ociweb.iot.maker.FogRuntime.SERIAL_WRITER;

import com.ociweb.iot.grove.gps.GPS_Transducer;
import com.ociweb.iot.grove.gps.GeoCoordinateListener;
import com.ociweb.iot.maker.FogCommandChannel;
import com.ociweb.iot.maker.FogRuntime;

public class GPSBehavior implements GeoCoordinateListener {
	private FogCommandChannel ch;
	private GPS_Transducer gps;
	public GPSBehavior(FogRuntime rt){
		this.gps = new GPS_Transducer(rt.newCommandChannel(SERIAL_WRITER), this);

	}
	@Override
	public void coordinates(int longtitude, int lattitude) {
		System.out.println("Long:" + longtitude + ", Lat: " + lattitude);
	}
	
}
