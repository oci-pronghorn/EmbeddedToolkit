package com.ociweb.grove;
import static com.ociweb.iot.grove.AnalogDigitalGroveTwig.*;
import com.ociweb.iot.maker.*;
import static com.ociweb.iot.maker.Port.*;

public class IoTApp implements FogApp
{
    private static final Port LIGHT_SENSOR_PORT= A2;
    private static final Port LED_PORT = D2;
   
    private static final int darkValue = 350; //Light reading that will turn on the light
    
    public static void main( String[] args ) {
        FogRuntime.run(new IoTApp());
    }
    @Override
    public void declareConnections(Hardware c) {         
        c.connect(LightSensor, LIGHT_SENSOR_PORT);
        c.connect(LED, LED_PORT, 200); //200 is the rate in milliseconds to update the device data
    }

    @Override
    public void declareBehavior(FogRuntime runtime) {
    	final FogCommandChannel lcdScreenChannel = runtime.newCommandChannel(DYNAMIC_MESSAGING);
    	runtime.addAnalogListener((port, time, durationMillis, average, value)->{
    		lcdScreenChannel.setValue(LED_PORT, value < darkValue);
    		System.out.println(value);
    	});
    }
}
