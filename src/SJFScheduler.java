import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SJFScheduler implements OSScheduler {

    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    CPUCore aCore;
    ProcessControlBlock nextp;
    public ArrayList<ProcessControlBlock> sjQueue = new ArrayList<>();


    public SJFScheduler() {

    }

    @Override
    public void schedule() {
        ArrayList<ProcessControlBlock> q = Weeboo.processManager().readyQueue();
        allCores = Weeboo.allCores();

        sjQueue = coreHelperArrayL(shortestJobScheduling(q));
        System.out.println("____________________________________________SHORTEST JOB");

        for (int i = 0; i < sjQueue.size();i++)
        {
            System.out.print(sjQueue.size() + " sjQueue " +sjQueue.get(i).getProcessName());
        }
    }

    //
    //
    //SHORTEST JOB FIRST
    //
    //
    public ArrayList<ProcessControlBlock> shortestJobScheduling(ArrayList<ProcessControlBlock> q)
    {
//        for (int i = 0; i<q.size(); i++)
//        {
//            sjQueue.add(q.get(i));
//            System.out.println("---------size---------"+sjQueue.size());
//        }

        sjQueue = q;

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

