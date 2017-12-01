import java.util.ArrayList;
import java.util.Iterator;

public class RoundRobinScheduler implements OSScheduler {

    private int quantum_time = 8;
    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    CPUCore aCore;
    ProcessControlBlock nextp;
    public ArrayList<ProcessControlBlock> rrQueue = new ArrayList<>();


    public RoundRobinScheduler() {

    }

    @Override
    public void schedule() {
        ArrayList<ProcessControlBlock> q = Weeboo.processManager().readyQueue();
        allCores = Weeboo.allCores();

        rrQueue = coreHelperArrayL(roundRobin(q));
    }

    //
    //
    // ROUND EOBIN
    //
    //
    public ArrayList<ProcessControlBlock> roundRobin(ArrayList<ProcessControlBlock> q)
    {
        ArrayList<ProcessControlBlock> roundWait = new ArrayList<>();
        ProcessControlBlock p;
        for(int i = 0; i<q.size();i++)
        {
            p = q.get(i);
            if(p.getRemainingBurstTime() > quantum_time)
            {
                rrQueue.add(p);
                p.setRemainingBurstTime(p.getRemainingBurstTime() - quantum_time);
                roundWait.add(p);
            }
            else if(p.getRemainingBurstTime() <= quantum_time)
            {
                rrQueue.add(p);
            }
        }

        if(!roundWait.isEmpty())
        {
            roundRobin(roundWait);
        }

        return rrQueue;
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