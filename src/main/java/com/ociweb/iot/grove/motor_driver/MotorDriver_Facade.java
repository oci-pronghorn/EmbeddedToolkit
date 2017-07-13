/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.ociweb.iot.grove.motor_driver;

import static com.ociweb.iot.grove.motor_driver.MotorDriver_Constants.*;
import com.ociweb.iot.maker.FogCommandChannel;
import com.ociweb.iot.maker.IODeviceFacade;
import com.ociweb.pronghorn.iot.schema.I2CCommandSchema;
import com.ociweb.pronghorn.pipe.DataOutputBlobWriter;

/**
 *
 * @author huydo
 */
public class MotorDriver_Facade implements IODeviceFacade{
    private final FogCommandChannel target;
    public int DRIVER_I2C_ADD = 0x0f; //default address of the driver
    
    public MotorDriver_Facade(FogCommandChannel ch){
        this.target = ch;
    }
    
    public MotorDriver_Facade(FogCommandChannel ch,int i2cAddress){
        this.target = ch;
        this.DRIVER_I2C_ADD = i2cAddress;
    }
    
    private void direction(int _direction){
        
            DataOutputBlobWriter<I2CCommandSchema> i2cPayloadWriter = target.i2cCommandOpen(DRIVER_I2C_ADD);
            
            i2cPayloadWriter.writeByte(DIR_REG);
            i2cPayloadWriter.writeByte(_direction);
            i2cPayloadWriter.writeByte(DUMMY_BYTE);
            
            target.i2cCommandClose();
            target.i2cFlushBatch();
            target.i2cDelay(DRIVER_I2C_ADD, 4000000);
  
    }
    /**
     * Set the velocity of both motors. The motor rotates clockwise if velocity > 0 and vice versa
     * @param motor1Vel integer between -255 and 255
     * @param motor2Vel integer between -255 and 255
     */
    public void setVelocity(int motor1Vel,int motor2Vel){
        if(motor1Vel >= 0 && motor2Vel >= 0){
            direction(M1CW_M2CW);
        }else if(motor1Vel < 0 && motor2Vel<0){
            direction(M1ACW_M2ACW);
        }else if(motor1Vel >= 0 && motor2Vel < 0){
            direction(M1CW_M2ACW);
        }else if(motor1Vel < 0 && motor2Vel >= 0){
            direction(M1ACW_M2CW);
        }
        motor1Vel = (Math.abs(motor1Vel)>255)?255:Math.abs(motor1Vel);
        motor2Vel = (Math.abs(motor2Vel)>255)?255:Math.abs(motor2Vel);
        
        DataOutputBlobWriter<I2CCommandSchema> i2cPayloadWriter = target.i2cCommandOpen(DRIVER_I2C_ADD);
        
        i2cPayloadWriter.writeByte(SPEED_REG);
        i2cPayloadWriter.writeByte(motor1Vel);
        i2cPayloadWriter.writeByte(motor2Vel);
        
        target.i2cCommandClose();
        target.i2cFlushBatch();
        target.i2cDelay(DRIVER_I2C_ADD, 4000000);
    }
    
    public void setFrequency(int frequency){
        byte _s;
        switch(frequency){
            case 31372:
                _s = F_31372Hz;
                break;
            case 3921:
                _s = F_3921Hz;
                break;
            case 490:
                _s = F_490Hz;
                break;
            case 122:
                _s = F_122Hz;
                break;
            case 30:
                _s = F_30Hz;
            default:
                _s = F_3921Hz;
        }
        DataOutputBlobWriter<I2CCommandSchema> i2cPayloadWriter = target.i2cCommandOpen(DRIVER_I2C_ADD);
        
        i2cPayloadWriter.writeByte(PWM_FREQ_REG);
        i2cPayloadWriter.writeByte(_s);
        i2cPayloadWriter.writeByte(DUMMY_BYTE);
        
        target.i2cCommandClose();
        target.i2cFlushBatch();
        target.i2cDelay(DRIVER_I2C_ADD, 4000000);
    }
    
    /**
     *      Drive a stepper motor
     * _step: -1024~1024, when _step>0, stepper motor runs clockwise; when _step is less than 0,
     * stepper motor runs anticlockwise; when _step is 512, the stepper motor will
     * run a complete turn; if step is 1024, the stepper motor will run 2 turns.
     * @param _step
     */
    public void StepperRun(int _step) {
        int _direction = 1;
        if (_step > 0) {
            _direction = 1; //clockwise
            _step = _step > 1024 ? 1024 : _step;
        }
        else if (_step < 0) {
            _direction = -1; //anti-clockwise
            _step = _step < -1024 ? 1024 : -(_step);
        }
        
        
        DataOutputBlobWriter<I2CCommandSchema> i2cPayloadWriter = target.i2cCommandOpen(DRIVER_I2C_ADD);
        
        i2cPayloadWriter.writeByte(SPEED_REG);
        i2cPayloadWriter.writeByte(255);
        i2cPayloadWriter.writeByte(255);
        
        target.i2cCommandClose();
        target.i2cFlushBatch();
        target.i2cDelay(DRIVER_I2C_ADD, 4000000);				                // wait
        
        if (_direction == 1) {
            for (int i=0; i<_step; i++) {
                direction(0b0001);
                direction(0b0011);
                direction(0b0010);
                direction(0b0110);
                direction(0b0100);
                direction(0b1100);
                direction(0b1000);
                direction(0b1001);
            }
        }
        else if (_direction == -1) {
            for (int i=0; i<_step; i++) {
                direction(0b1000);
                direction(0b1100);
                direction(0b0100);
                direction(0b0110);
                direction(0b0010);
                direction(0b0011);
                direction(0b0001);
                direction(0b1001);
            }
        }
    }
}
