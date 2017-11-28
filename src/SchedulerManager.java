import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;


public abstract class SchedulerManager {
	
	public abstract Queue<ProcessControlBlock> schedule(ArrayList<ProcessControlBlock> listOfPRocesses);
	
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
	
	private static ProcessManager _processManager = new ProcessManager();
	
	public ArrayList<ProcessControlBlock> readyQueue;
	public ArrayList<ProcessControlBlock> waitingQueue;
	public ArrayList<ProcessControlBlock> runningQueue;
	
	public SchedulerManager() {
		
	}
	
	
	
	public void addWaitTime(int time) {
		waitingTime += time;
		roundWaitingTime+=time;
	}
	
	public void nextRound() {
		roundWaitingTime = 0;
		
	
		
	}
	
}
