//max waiting time for IO = 50
import java.util.Random;
public class IO {
    private boolean busy;
    private int iofinish;
    CPUCore blockingCore;

    public IO(CPUCore aCore) {
        this.blockingCore = aCore;
    }

    private Random randomIO = new Random();

    public int generateIOBurst() {
        iofinish = OSClock.getClock() + ((randomIO.nextInt(50))); //double check 50
        busy = true;

        return iofinish;
    }

    public void IOExecution()
    {
        if (OSClock.getClock() >= iofinish && busy) {
            //cpu.interruptProcessor.setFlag(InterruptProcessor.IOComplete);//FIX THIS
            busy = false;
        }
    }

    public boolean IOAvailability() {
        return busy;
    }
}
