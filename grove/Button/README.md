

# Starting a FogLighter project using Maven: 
[Instructions here.](https://github.com/oci-pronghorn/FogLighter/blob/master/README.md)

# Example Project:
The following sketch demonstrates a simple application using the Button: whenever the Button is pressed, a relay will flash a light.

Demo code:

```java
package com.ociweb.grove;
import static com.ociweb.iot.grove.simple_digital.SimpleDigitalTwig.Button;
import static com.ociweb.iot.grove.simple_digital.SimpleDigitalTwig.Relay;
import static com.ociweb.iot.maker.Port.D3;
import static com.ociweb.iot.maker.Port.D7;

import com.ociweb.iot.maker.FogApp;
import com.ociweb.iot.maker.FogRuntime;
import com.ociweb.iot.maker.Hardware;
import com.ociweb.iot.maker.Port;

public class IoTApp implements FogApp
{
    private static final Port BUTTON_PORT = D3;
    private static final Port RELAY_PORT  = D7;
    
    @Override
    public void declareConnections(Hardware c) {
        c.connect(Button, BUTTON_PORT); 
        c.connect(Relay, RELAY_PORT);         
    }

    @Override
    public void declareBehavior(FogRuntime runtime) {
    
        runtime.addDigitalListener(new ButtonBehavior(runtime));
       
    }
}
```

Behavior class:

```java
package com.ociweb.grove;
import static com.ociweb.iot.maker.FogCommandChannel.PIN_WRITER;
import static com.ociweb.iot.maker.Port.D7;

import com.ociweb.iot.maker.DigitalListener;
import com.ociweb.iot.maker.FogCommandChannel;
import com.ociweb.iot.maker.FogRuntime;
import com.ociweb.iot.maker.Port;

public class ButtonBehavior implements DigitalListener {

	private static final Port RELAY_PORT = D7;
	
    final FogCommandChannel channel1;
	public ButtonBehavior(FogRuntime runtime) {

       channel1 = runtime.newCommandChannel(PIN_WRITER);

	}

	@Override
	public void digitalEvent(Port port, long time, long durationMillis, int value) {

        channel1.setValueAndBlock(RELAY_PORT, value == 1, 500); //500 is the amount of time in milliseconds that                                                                                         //delays a future action

	}

}
```


When executed, the above code will cause the relay on D7 (digital output 7) to turn on when the button on D3 (digital input 3) is pressed.

The ```digitalEvent()``` method passes a 1 as the value when the button is pressed, and 0 when it is released. In order to send a signal to the relay on the digital port, use the ```setValue()``` method to check if the value is equivalent to 1, and when it is, a signal will be sent.
