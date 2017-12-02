import java.util.ArrayList;
import java.util.Iterator;

public class FIFOScheduler  implements OSScheduler{

    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    ArrayList<ProcessControlBlock> ffQueue;
    CPUCore aCore;
    ProcessControlBlock nextp;
    Iterator<CPUCore> coreIterator = allCores.iterator();



    public FIFOScheduler() {
        ffQueue = new ArrayList<>();
    }

    @Override
    public void schedule() {

        allCores = Weeboo.allCores();
        ffQueue = coreHelperArrayL(Weeboo.processManager().readyQueue());
    }

    public ArrayList<ProcessControlBlock> coreHelperArrayL(ArrayList<ProcessControlBlock> q)
    {

        while( coreIterator.hasNext() && q.isEmpty()!=true) {
            aCore = coreIterator.next();
            previouslyAssignedPCB = aCore.assignedProcess();
            if (previouslyAssignedPCB != null) {
                if (previouslyAssignedPCB.getProcessState() == ProcessControlBlock.processState.EXIT) {
                    //
                    aCore.assignProcess(null);
                    previouslyAssignedPCB = null;
                }

            }

            if(previouslyAssignedPCB == null)
            {
                if(q.isEmpty()!=true)
                {
                    nextp = q.get(0);
                }
                if(nextp!=null)
                {
                    aCore.assignProcess(nextp);
                    nextp.setProcessState(ProcessControlBlock.processState.RUN);
                    q.remove(0);
                }
            }
        }

        return q;
    }

}