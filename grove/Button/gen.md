

# Starting a FogLighter project using Maven: 
[Instructions here.](https://github.com/oci-pronghorn/FogLighter/blob/master/README.md)

# Example Project:
The following sketch demonstrates a simple application using the Button: whenever the Button is pressed, a relay will flash a light.

Demo code:
.include "./src/main/java/com/ociweb/grove/IoTApp.java"
Behavior class:
.include "./src/main/java/com/ociweb/grove/ButtonBehavior.java"

When executed, the above code will cause the relay on D7 (digital output 7) to turn on when the button on D3 (digital input 3) is pressed.

The ```digitalEvent()``` method passes a 1 as the value when the button is pressed, and 0 when it is released. In order to send a signal to the relay on the digital port, use the ```setValue()``` method to check if the value is equivalent to 1, and when it is, a signal will be sent.
