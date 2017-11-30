import java.lang.reflect.Array;
import java.util.*;

public class Scheduler
{

    //public abstract Queue<ProcessControlBlock> schedule(ArrayList<ProcessControlBlock> listOfPRocesses);


    private static ProcessManager _processManager = Weeboo.processManager();

    public ArrayList<Process> readyQueue;
    Map<Process, Integer> roundRunning = new LinkedHashMap<>();

    public ArrayList<Process> shortJobQueue = new ArrayList<>();

    public Scheduler() {

    }

    public void roundRobinScheduling(ArrayList<Process> q)
    {
        ArrayList<Process> roundWait = new ArrayList<>();

        int quantum_time = 8;
        Process p;
        for(int i = 0; i<q.size();i++)
        {
            p = q.get(i);

            if(p.getRemainingBurstTime() > quantum_time)
            {
                roundRunning.put(p,quantum_time);
                p.setRemainingBurstTime(p.getRemainingBurstTime() - quantum_time);
                roundWait.add(p);
            }
            else if(p.getRemainingBurstTime() <= quantum_time)
            {
                roundRunning.put(p, p.getRemainingBurstTime());
            }
        }

        if(!roundWait.isEmpty())
        {
            roundRobinScheduling(roundWait);
        }
    }

    public void shortestJobScheduling(ArrayList<Process> q)
    {
        for (int i = 0; i<q.size(); i++)
        {
            shortJobQueue.add(q.get(i));
        }

        Collections.sort(shortJobQueue, shortJobComparator);

    }

    public static Comparator<Process> shortJobComparator = new Comparator<Process>()
    {
        @Override
        public int compare(Process p1, Process p2) {
            double process1 = (double)p1.getBurstTime();
            double process2 = (double)p2.getBurstTime();
            return Double.compare(process1, process2);
        }};


}



    /*public void schedule() {
        ArrayList<ProcessControlBlock> readyQueue2 = _processManager.readyQueue();
        ArrayList<CPUCore> allCores = Weeboo.allCores();

        Iterator<CPUCore> coreIterator = allCores.iterator();
        while( coreIterator.hasNext()) {
            CPUCore aCore = coreIterator.next();

            ProcessControlBlock previouslyAssignedPCB = aCore.assignedProcess();
            if( previouslyAssignedPCB != null) {

            }
        }
    }*/