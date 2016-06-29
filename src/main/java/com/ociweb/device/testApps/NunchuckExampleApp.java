package com.ociweb.device.testApps;

import com.ociweb.pronghorn.iot.i2c.impl.I2CNativeLinuxBacking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple app that demonstrates interacting with a Wii Nunchuck via the Grove.
 *
 * All of the I2C commands used in this example was derived from the following
 * webpages:
 *
 * http://rts.lab.asu.edu/web_325/CSE325_Assignment_6_F10.pdf
 *
 * http://www.robotshop.com/media/files/PDF/inex-zx-nunchuck-datasheet.pdf
 *
 * @author Brandon Sanders [brandon@alicorn.io]
 */

/**
 * Running the grove pi with the nuchuck framework
 * 
 * i2c protocol found here: https://www.raspberrypi.org/forums/download/file.php?id=8272
 * 
 * @author alexherriott
 *
 */
public class NunchuckExampleApp {
    private static final Logger logger = LoggerFactory.getLogger(NunchuckExampleApp.class);

    // Create a connection to the native Linux I2C lines.
    private static final I2CNativeLinuxBacking i2c = new I2CNativeLinuxBacking((byte) 1);

    // Address of the nunchuck.
    public static final byte NUNCHUCK_ADDR = 0x04;

    public static void main(String[] args) {
        logger.info("Starting light blinky example app.");

        // Write 0x40 and 0x00 to initialize the nunchuck.
//        logger.info("Initializing Nunchuck.");
//        i2c.write(NUNCHUCK_ADDR, (byte) 0x40, (byte) 0x00);
//        logger.info("Nunchuck initialized.");

        // Loop forever, reading from the nunchuck.
        System.out.println("#### Writing data ####");
        System.out.println("");
        pinMode(3, 0);
        pinMode(4,1);
        byte[] response;
        int val = 255;
        while (true) {
            
//        	response = digRead(3);
//        	System.out.println(response[0]);
            
            
           //digWrite(2, 1);
        	pwmWrite(3,val);
        	val = (val+10)%255;
           
            // Sleep for a bit.
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            
//            digWrite(2, 0);
//            try {
//                Thread.sleep(250);
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            }
        }
    }
    
    static void digWrite(int pin, int val){
    	i2c.write(NUNCHUCK_ADDR, (byte) 0x01);
        i2c.write(NUNCHUCK_ADDR, (byte) 0x02);
        i2c.write(NUNCHUCK_ADDR, (byte) pin);
        i2c.write(NUNCHUCK_ADDR, (byte) val);
        //i2c.write(NUNCHUCK_ADDR, (byte) 0x00);
        
    }
    
    static byte[] digRead(int pin){
    	i2c.write(NUNCHUCK_ADDR, (byte) 0x01);
        i2c.write(NUNCHUCK_ADDR, (byte) 0x01);
        i2c.write(NUNCHUCK_ADDR, (byte) pin);
        i2c.write(NUNCHUCK_ADDR, (byte) 0x00);
        return i2c.read(NUNCHUCK_ADDR, 1);
    }
    
    static void pinMode(int pin, int val){
    	i2c.write(NUNCHUCK_ADDR, (byte) 0x01);
        i2c.write(NUNCHUCK_ADDR, (byte) 0x05);
        i2c.write(NUNCHUCK_ADDR, (byte) pin);
        i2c.write(NUNCHUCK_ADDR, (byte) val);
        //i2c.write(NUNCHUCK_ADDR, (byte) 0x00);
    }
    
    static void pwmWrite(int pin, int val){
    	i2c.write(NUNCHUCK_ADDR, (byte) 0x01);
        i2c.write(NUNCHUCK_ADDR, (byte) 0x04);
        i2c.write(NUNCHUCK_ADDR, (byte) pin);
        i2c.write(NUNCHUCK_ADDR, (byte) val);
    }
//    public static void main(String[] args) {
//        logger.info("Starting Wii Nunchuck example application.");
//
//        // Write 0x40 and 0x00 to initialize the nunchuck.
//        logger.info("Initializing Nunchuck.");
//        i2c.write(NUNCHUCK_ADDR, (byte) 0x40, (byte) 0x00);
//        logger.info("Nunchuck initialized.");
//
//        // Loop forever, reading from the nunchuck.
//        System.out.println("#### Wii Nunchuck Tracking Data ####");
//        System.out.println("");
//        byte[] response;
//        while (true) {
//            // Write 0x00 to the nunchuck to request data.
//            i2c.write(NUNCHUCK_ADDR, (byte) 0x00);
//
//            // Read the 6-byte response from the nunchuck.
//            response = i2c.read(NUNCHUCK_ADDR, 6);
//
//            // Decode response by XOR 0x17 and add 0x17.
//            for (int i = 0; i < response.length; i++) {
//                response[i] = (byte) ((response[i] ^ 0x17) + 0x17);
//            }
//
//            // Clear the most recent line so that we don't spew out tons of lines of content.
//            System.out.print("\r");
//            for (int i = 0; i < 100; i++) System.out.print(" ");
//            System.out.print("\r");
//
//            // Print out tracking data.
//            System.out.print(String.format("Stick (X %d Y %d) | Gyro (X %d Y %d Z %d) | Buttons (%d)",
//                                             response[0], response[1], response[2], response[3],
//                                             response[4], response[5]));
//
//            // Sleep for a bit.
//            try {
//                Thread.sleep(250);
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//    }
}
