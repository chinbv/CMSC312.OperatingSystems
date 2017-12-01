//max waiting time for IO = 50
import java.util.Random;

public class IORequest {
    private int _ioWaitTicks;
    ProcessControlBlock _processControlBlock = null;

    public IORequest(ProcessControlBlock aPCB) {
        this._processControlBlock = aPCB;
    }


    public void requestIO() {
         Random randomIO = new Random();

    	// synthesize the return interrupt for when the I/O finishes
    	
    	_ioWaitTicks = OSClock.getClock() + ((randomIO.nextInt(50))); //double check 50
    	
    	// set process state to wait for I/O to complete
    	_processControlBlock.setProcessState(ProcessControlBlock.processState.WAIT);
    	
    	Weeboo.interruptHandler().addIO(this);
    }
    
    
    public ProcessControlBlock getPCB() {
    	return this._processControlBlock;
    }
    
    public int ioCompletionTime() {
    	return _ioWaitTicks;
    }

}
