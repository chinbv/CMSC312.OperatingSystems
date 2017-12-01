import java.util.ArrayList;
import java.util.Iterator;

public class FIFOScheduler  implements OSScheduler{

    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    CPUCore aCore;
    ProcessControlBlock nextp;
    public ArrayList<ProcessControlBlock> ffQueue = new ArrayList<>();


    public FIFOScheduler() {

    }

    @Override
    public void schedule() {
        ArrayList<ProcessControlBlock> q = Weeboo.processManager().readyQueue();
        allCores = Weeboo.allCores();

        ffQueue = coreHelperArrayL(fifo(q));
    }

    //
    //
    //FIRST IN FIRST OUT
    //
    //
    public ArrayList<ProcessControlBlock> fifo(ArrayList<ProcessControlBlock> q)
    {
        return ffQueue = (ArrayList<ProcessControlBlock>) q.clone();
    }

    //
    //
    //ASSIGN CORES
    //
    //
    public ArrayList<ProcessControlBlock> coreHelperArrayL(ArrayList<ProcessControlBlock> q)
    {
        Iterator<CPUCore> coreIterator = allCores.iterator();
        while( coreIterator.hasNext()) {
            aCore = coreIterator.next();
            previouslyAssignedPCB = aCore.assignedProcess();
            if (previouslyAssignedPCB != null) {
                if (previouslyAssignedPCB.getProcessState() == ProcessControlBlock.processState.EXIT) {
                    //
                    aCore.assignProcess(null);
                    previouslyAssignedPCB = null;
                }
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
                q.remove(0);
            }
        }
        return q;
    }}
//next time you call corehelperarrayL....it needs to have just the remaining processes