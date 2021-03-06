package com.ociweb.grove;


import com.ociweb.iot.maker.*;

import static com.ociweb.iot.grove.simple_digital.SimpleDigitalTwig.*;
import static com.ociweb.iot.grove.simple_analog.SimpleAnalogTwig.*;

import static com.ociweb.iot.maker.Port.*;

public class IoTApp implements FogApp
{
	private static final Port VIBRATION_SENSOR_PORT = A0;
	private static final Port BUZZER_PORT = D2;
	
	@Override
	public void declareConnections(Hardware c) {
		c.connect(Buzzer, BUZZER_PORT);
		c.connect(VibrationSensor, VIBRATION_SENSOR_PORT);
	}


	@Override
	public void declareBehavior(FogRuntime runtime) {
				
		runtime.addAnalogListener(new VibrationSensorBehavior(runtime)).includePorts(VIBRATION_SENSOR_PORT);
	}
}
