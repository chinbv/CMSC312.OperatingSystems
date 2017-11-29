import Processes.Process;
import Processes.ProcessManager;

import java.util.ArrayList;
import java.util.Queue;


public abstract class SchedulerManager {
	
	public abstract Queue<Process> schedule(ArrayList<Process> listOfPRocesses);
	
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
	
	public ArrayList<Process> readyQueue;
	public ArrayList<Process> waitingQueue;
	public ArrayList<Process> runningQueue;
	
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
