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
    	
    	// TODO more
    }

}
