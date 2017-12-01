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
    int counter  =0;

    public SJFScheduler() {

    }

    @Override
    public void schedule() {
        System.out.println("schedule is being called " + ++counter );
        allCores = Weeboo.allCores();

        for (int i =0;i<Weeboo.processManager().readyQueue().size(); i++) {
            System.out.println("SLKDJFSLKJFLSKJDFLSKJDFLSKDJFLKJSDLKFJSLKDFJ beginininginging" + Weeboo.processManager().readyQueue().get(i).getProcessName());
        }
        sjQueue = coreHelperArrayL(shortestJobScheduling(Weeboo.processManager().readyQueue()));

    }

    //SHORTEST JOB FIRST
    public ArrayList<ProcessControlBlock> shortestJobScheduling(ArrayList<ProcessControlBlock> q)
    {
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

    //ASSIGN CORES
    public ArrayList<ProcessControlBlock> coreHelperArrayL(ArrayList<ProcessControlBlock> q)
    {
        Iterator<CPUCore> coreIterator = allCores.iterator();
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
                    System.out.println("THISSSSSSSSSSSSSSSSSSS ISSSSSS THE PRCESSSSSSSSSS BEING GOTTEN OUT" + q.get(0).getProcessName());
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
    }}
//next time you call corehelperarrayL....it needs to have just the remaining processes


