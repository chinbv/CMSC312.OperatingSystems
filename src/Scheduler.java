import java.lang.reflect.Array;
import java.util.*;

public class Scheduler
{
    private int quantum_time = 8;
    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    CPUCore aCore;
    ProcessControlBlock nextp;
    public ArrayList<ProcessControlBlock> rrQueue = new ArrayList<>();
    public ArrayList<ProcessControlBlock> sjQueue = new ArrayList<>();
    public ArrayList<ProcessControlBlock> ffQueue = new ArrayList<>();

    public Scheduler() {
    }

    //Method Called that will return a scheduled queue of the selected algorithm
    //and will assign processes to the core(s)
    public void initializeScheduler(ArrayList<ProcessControlBlock> q) {
        allCores = Weeboo.allCores();
        ffQueue = coreHelperArrayL(fifo(q));
        System.out.println("sdlfjsldjfsdkf");
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
    //SHORTEST JOB FIRST
    //
    //
    public ArrayList<ProcessControlBlock> shortestJobScheduling(ArrayList<ProcessControlBlock> q)
    {
        for (int i = 0; i<q.size(); i++)
        {
            sjQueue.add(q.get(i));
        }

        Collections.sort(sjQueue, shortJobComparator);
        return sjQueue;
    }

    public static Comparator<ProcessControlBlock> shortJobComparator = new Comparator<ProcessControlBlock>()
    {
        @Override
        public int compare(ProcessControlBlock p1, ProcessControlBlock p2) {
            double process1 = (double)p1.getBurstTime();
            double process2 = (double)p2.getBurstTime();
            return Double.compare(process1, process2);
        }};
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