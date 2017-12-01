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

    }

    @Override
    public void schedule() {

        allCores = Weeboo.allCores();

        //System.out.println("QUEUEUEUEUEUEUE b4 PROCESSES HAVE BEEN ASSIGNED TO CORRESS" + ffQueue.size());
        ffQueue = coreHelperArrayL(Weeboo.processManager().readyQueue());

        System.out.println("QUEUEUEUEUEUEUE AFTER PROCESSES HAVE BEEN ASSIGNED TO CORRESS" + ffQueue.size());

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
    }

}
