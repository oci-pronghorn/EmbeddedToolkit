# What you will need before you start:
-[Java 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) 

-[Maven](https://maven.apache.org/install.html), which downloads and manages the libraries and APIs needed to get the Grove device working.

-[Git](https://git-scm.com/), which clones a template Maven project with the necessary dependencies already set up.

# Starting your Maven project: 
[Starting a mvn project](https://github.com/oci-pronghorn/FogLighter/blob/master/README.md)

# Example project:
 
The following sketch reads and prints the X and Y values of the Joystick. In addition, it detects presses.
 
Demo code: 
```java
import static com.ociweb.iot.grove.AnalogDigitalTwig.*;
import com.ociweb.iot.maker.*;
import static com.ociweb.iot.maker.Port.*;
import com.ociweb.gl.api.GreenCommandChannel;

public class IoTApp implements IoTSetup
{
	private static final Port THUMBJOYSTICK_PORT_X = A0;
	private static final Port THUMBJOYSTICK_PORT_Y = A1;

	@Override
	public void declareConnections(Hardware c) {
		c.connect(ThumbJoystick, THUMBJOYSTICK_PORT_X);
		c.connect(ThumbJoystick, THUMBJOYSTICK_PORT_Y);
	}


	@Override
	public void declareBehavior(DeviceRuntime runtime) {
		final CommandChannel channel1 = runtime.newCommandChannel(GreenCommandChannel.DYNAMIC_MESSAGING);
		runtime.addAnalogListener((port, time, durationMillis, average, value)->{
			switch (port){
			case A0:
				if (value < 1023){
					System.out.println("X: "+value);
				}
				else {
					System.out.println("Pressed");
				}
				break;
				
			case A1:
				System.out.println("Y: "+value);
				break;
				
			default:
				System.out.println("Please ensure that you are connecting to the correct physical port (A0)");
				break;
			}
		});
	}
}

```         

The Joystick is made out of two potentiameters rotating in two orthogonal (X and Y) planes. They are physically constrasined so that their values would read between around 200 to 800. When the joystick is pressed, the X value will read 1023 and can be used to detect presses.

The lambda passed into ```runtime.addAnalogListener()``` first identifies which Port (the X or Y port) triggered the analog event, and prints out the value accordingly. There is an addition conditional logic for X to determine whether the joystick was pressed.

 
 
 
 
 
 
