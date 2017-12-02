import java.util.ArrayList;
import java.util.Iterator;

public class FIFOScheduler  implements OSScheduler {

    ArrayList<CPUCore> allCores = new ArrayList<CPUCore>();
    ProcessControlBlock previouslyAssignedPCB;
    public ArrayList<ProcessControlBlock> ffQueue = new ArrayList<>();
    CPUCore aCore;
    ProcessControlBlock nextp;
    Iterator<CPUCore> coreIterator = allCores.iterator();


    public FIFOScheduler() {

    }

    @Override
    public void schedule() {

        allCores = Weeboo.allCores();
        ffQueue = coreHelperArrayL(fifoScheduling(Weeboo.processManager().readyQueue()));

    }
    public ArrayList<ProcessControlBlock> fifoScheduling(ArrayList<ProcessControlBlock> q)
    {
        ffQueue = q;
        return ffQueue;
    }

    public ArrayList<ProcessControlBlock> coreHelperArrayL(ArrayList<ProcessControlBlock> q) {
        for (int i = 0; i < q.size(); i++) {
        }
        Iterator<CPUCore> coreIterator = allCores.iterator();
        while (coreIterator.hasNext() && q.isEmpty() != true) {
            aCore = coreIterator.next();
            previouslyAssignedPCB = aCore.assignedProcess();
            if (previouslyAssignedPCB != null) {
                if (previouslyAssignedPCB.getProcessState() == ProcessControlBlock.processState.EXIT) {
                    //
                    aCore.assignProcess(null);
                    previouslyAssignedPCB = null;
                }
            }

            if (previouslyAssignedPCB == null) {
                if (q.isEmpty() != true) {
                    nextp = q.get(0);
                }
                if (nextp != null) {
                    aCore.assignProcess(nextp);
                    nextp.setProcessState(ProcessControlBlock.processState.RUN);
                    q.remove(0);
                }
            }
        }
        return q;
    }
}