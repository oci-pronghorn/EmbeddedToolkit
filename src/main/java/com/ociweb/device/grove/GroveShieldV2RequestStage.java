package com.ociweb.device.grove;

import com.ociweb.device.config.GroveConnectionConfiguration;
import com.ociweb.device.grove.schema.GroveRequestSchema;
import com.ociweb.device.impl.EdisonPinManager;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

//TODO: should be Grove specific NOT edision specific
public class GroveShieldV2RequestStage extends PronghornStage {

	private static final short activeBits = 4; //we have a max of 16 physical ports to use on the groveShield
    private static final short activeSize = (short)(1<<activeBits);
    
    
    private int[][]    movingAverageHistory;
    private int[]      lastPublished;
    
    private int[]       rotaryRolling;
    private int[]       rotationState;
    private long[]      rotationLastCycle;
    
    //for devices that must poll frequently
    private int[]       frequentScriptConn;
    private Twig[] 		frequentScriptTwig;
    private int[]       frequentScriptLastPublished;
    private int         frequentScriptLength = 0;
    
    
    private final Pipe<GroveRequestSchema>[] requestPipes;
    private final GroveConnectionConfiguration config;
    

    
	  public GroveShieldV2RequestStage(GraphManager gm, Pipe<GroveRequestSchema> requestPipe, GroveConnectionConfiguration config) {
	  super(gm, requestPipe, NONE);
	  
	  this.requestPipes = new Pipe[]{requestPipe};
	  this.config = config;
	  GraphManager.addNota(gm, GraphManager.SCHEDULE_RATE, 10*1000*1000, this);
	  GraphManager.addNota(gm, GraphManager.PRODUCER, GraphManager.PRODUCER, this);        
	}
    
    public GroveShieldV2RequestStage(GraphManager gm, Pipe<GroveRequestSchema>[] requestPipes, GroveConnectionConfiguration config) {
        super(gm, requestPipes, NONE);
        
        this.requestPipes = requestPipes;
        this.config = config;
        
    }
    
        
    
    @Override
    public void startup() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        
        int j = config.maxAnalogMovingAverage()-1;
        movingAverageHistory = new int[j][]; 
        while (--j>=0) {
            movingAverageHistory[j] = new int[activeSize];            
        }
        lastPublished = new int[activeSize];
        
        rotaryRolling = new int[activeSize];
  //      Arrays.fill(rotaryRolling, 0xFFFFFFFF);
        rotationState = new int[activeSize];
        rotationLastCycle = new long[activeSize];
        
        //for devices that must poll frequently
        frequentScriptConn = new int[activeSize];
        frequentScriptTwig = new GroveTwig[activeSize];
        frequentScriptLastPublished = new int[activeSize];
        
        //before we setup the pins they must start in a known state
        //this is required for the ATD converters (eg any analog port usage)
        
        config.setToKnownStateFromColdStart();        
        
        
        
        //configure each sensor
        config.beginPinConfiguration();
        
        
        
        
        int i=config.digitalOutputs.length;
        while(--i>0){
        	config.configurePinsForDigitalOutput(config.digitalOutputs[i].connection);
        	Twig twig = config.digitalOutputs[i].twig;
        	frequentScriptConn[frequentScriptLength] = config.digitalOutputs[i].connection;
        	frequentScriptTwig[frequentScriptLength] = twig;  
        	frequentScriptLength++; 
        }
    	
    	
    //  i = config.pwmOutputs.length;
    //  while (--i>=0) {
//          configPWM(config.pwmOutputs[i]); //take from pipe and write, get type and field from pipe
//          
//          script[reverseBits(sliceCount++)] = ((MASK_DO_PORT&config.pwmOutputs[i])<<SHIFT_DO_PORT) |
//                                              ((MASK_DO_JOB&DO_DATA_WRITE)<<SHIFT_DO_JOB );
    //  }
        
        config.endPinConfiguration();
        
        
        System.out.println("Turn on ");
//        config.writeBit(4, 1);
    }
    
    
    @Override
    public void run() {
      
        int j = requestPipes.length;
        while (--j>=0) {
            processPipe(requestPipes[j]);
            
        }
        
    }

    private void processPipe(Pipe<GroveRequestSchema> requestPipe) {
        while (Pipe.hasContentToRead(requestPipe)) {
            
            //read the messages.
            int msg = Pipe.takeMsgIdx(requestPipe);
            
            switch (msg) {
                case GroveRequestSchema.MSG_DIGITALSET_110:
                {
                    int connector = Pipe.takeValue(requestPipe);
                    int value = Pipe.takeValue(requestPipe);     
                    
                    
                    config.writeBit(connector,value);
                    
       
                }   
                break;
                case GroveRequestSchema.MSG_DIGITALSET_120:
                {
                    int connector = Pipe.takeValue(requestPipe);
                    int value = Pipe.takeValue(requestPipe);
                    int duration = Pipe.takeValue(requestPipe); 
                    
                    //TODO write something to device
                    
                }   
                break;
                case GroveRequestSchema.MSG_ANALOGSET_140:
                { 
                    int connector = Pipe.takeValue(requestPipe);
                    int position = Pipe.takeValue(requestPipe); 
            
                    //TODO write something to device
                    
                }   
                break;    
                //shutodown? needs all pipes to agree???
                
                        
            }
            
            Pipe.confirmLowLevelRead(requestPipe, Pipe.sizeOf(requestPipe, msg));
            Pipe.releaseReadLock(requestPipe);
            
            
        }
    }

    @Override
    public void shutdown() {
        
        
    }
    
    
    
}
