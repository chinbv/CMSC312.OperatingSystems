import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;



public class DumbScheduler implements OSScheduler {
	
	//public abstract Queue<ProcessControlBlock> schedule(ArrayList<ProcessControlBlock> listOfPRocesses);
	
	private int waitingTime;
	private int turnAroundTime;
	private int responseTime;
	private int processCount;
	private int timeQuantum;
	
	private int roundTurnaroundTime;
    private int roundWaitingTime;
    private int roundResponseTime;
    private int roundProcessCount;
    private int roundQuanta;
	
	private static ProcessManager _processManager = Weeboo.processManager();
	
	public ArrayList<ProcessControlBlock> readyQueue;
	public ArrayList<ProcessControlBlock> waitingQueue;
	public ArrayList<ProcessControlBlock> runningQueue;
	
	public DumbScheduler() {
		
	}
	
	
	/* (non-Javadoc)
	 * @see OSScheduler#schedule()
	 */
	@Override
	public void schedule() {
		ArrayList<ProcessControlBlock> readyQueue = _processManager.readyQueue();
		ArrayList<CPUCore> allCores = Weeboo.allCores();
		
		Iterator<CPUCore> coreIterator = allCores.iterator();
		while( coreIterator.hasNext()) {
			CPUCore aCore = coreIterator.next();
			
			ProcessControlBlock previouslyAssignedPCB = aCore.assignedProcess();
			
			if( previouslyAssignedPCB != null) {
				if( previouslyAssignedPCB.getProcessState() == ProcessControlBlock.processState.RUN ) {
					// 
					//TODO unschedule based on quantum
				}
				
				// deschedule from core
				aCore.assignProcess(null);
				previouslyAssignedPCB = null;	// allow core to schedule below
			}
			
			
			if( previouslyAssignedPCB == null) {
				if( readyQueue.isEmpty() != true) {
					ProcessControlBlock nextProcess = readyQueue.get(0);
				
					if( nextProcess != null ) {
						aCore.assignProcess(nextProcess);
						readyQueue.remove(0);
					}
				}
			}
		}
	}
	
	/*
	public void addWaitTime(int time) {
		waitingTime += time;
		roundWaitingTime+=time;
	}
	
	public void nextRound() {
		roundWaitingTime = 0;
		
			}

	*/
	
		
	
}
