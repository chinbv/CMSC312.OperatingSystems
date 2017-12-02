//max waiting time for IO = 50
import java.util.Random;

public class IORequest {
    private int _ioWaitTicks;
    ProcessControlBlock _processControlBlock = null;

    public IORequest(ProcessControlBlock aPCB) {
        this._processControlBlock = aPCB;
    }


    public void requestIO(int ranNum) {
//         Random randomIO = new Random();

    	// synthesize the return interrupt for when the I/O finishes

//        int ranNum = randomIO.nextInt(50);
//        System.out.println("Random num for IO calc: "+ ranNum);
//        _processControlBlock.setNumOfIOBursts(ranNum);
    	_ioWaitTicks = OSClock.getClock() + ranNum; //double check 50
    	
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
