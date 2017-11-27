//max waiting time for IO = 50
import java.util.Random;
public class IO
{
    private boolean busy;
    private int iofinish;
    public IO(CPU cpu)
    {
        this.cpu = cpu;
    }

    private Random randomIO = new Random();

    public int generateIOBurst()
    {
        return iofinish = OSClock.getClock() + ((randomIO.nextInt(50))); //double check 50
        busy = true;
    }

    public void IOExecution
    {
        if (OSClock.getClock >= iofinish && busy)
        {
            cpu.interruptProcessor.setFlag(InterruptProcessor.IOComplete);//FIX THIS
            busy = false;
        }
    }
    public boolean IOAvailability()
    {
        return busy;
    }
        

}
