import java.util.ArrayList;
import java.util.Iterator;

public class RoundRobinScheduler implements OSScheduler {

    public int quantum_time = 8;
    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    CPUCore aCore;
    ProcessControlBlock nextp;
    public ArrayList<ProcessControlBlock> rrQueue = new ArrayList<ProcessControlBlock>();
    private ArrayList<ProcessControlBlock> roundWait = new ArrayList<>();


    public RoundRobinScheduler() {

    }

    @Override
    public void schedule() {
        allCores = Weeboo.allCores();

        roundRobin(Weeboo.processManager().readyQueue());
        //rrQueue = coreHelperArrayL(roundRobin(Weeboo.processManager().readyQueue()));
    }

    // ROUND EOBIN

    public ArrayList<ProcessControlBlock> roundRobin(ArrayList<ProcessControlBlock> q)
    {
        ProcessControlBlock p;
//        for (int i =0;i<q.size(); i++) {
//            System.out.println("SLKDJFSLKJFLSKJDFLSKJDFLSKDJFLKJSDLKFJSLKDFJ beginininginging" +q.get(i).getProcessName()
//                    + q.get(i).getRemainingBurstTime());
//        }

        for(int i = 0; i<q.size();i++)
        {
            p = q.get(i);
            if(p.getRemainingBurstTime() > quantum_time)
            {
                if(!roundWait.isEmpty() && roundWait.contains(p))
                {
                    roundWait.remove(p);
                }
                rrQueue.add(p);
                p.setRemainingBurstTime(p.getRemainingBurstTime() - quantum_time);
                roundWait.add(p);
            }
            else if(p.getRemainingBurstTime() <= quantum_time && p.getRemainingBurstTime()!=0)
            {
                rrQueue.add(p);
                Weeboo.processManager().readyQueue().remove(p);
                p.setRemainingBurstTime(p.getRemainingBurstTime() - p.getRemainingBurstTime());
                q.remove(p);
            }
        }
        /*while(!roundWait.isEmpty())
        {
            roundRobin(roundWait);
        }*/

        for (int i =0;i<rrQueue.size(); i++) {
            System.out.println("SLKDJFSLKJFLSKJDFLSKJDFLSKDJFLKJSDLKFJSLKDFJ in RR METHOD" + rrQueue.size() + "          " + rrQueue.get(i).getProcessName() + rrQueue.get(i).getRemainingBurstTime());
        }
        return rrQueue;


    }



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

