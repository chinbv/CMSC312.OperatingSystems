
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Process {
	private String processName;
	private int processId;
	private int arrivalTime;
	private int priority;
	private int burstTime;
	private int waitTime;
	private int turnAroundTime;
	private int remainingBurstTime;
	private int startTime;

	private int roundWaitTime;
	private int roundTurnAroundTime;
	private ArrayList<ProcessOperation> processOperations;
	private int numOfCycles;
	private ProcessState processState;

	public Process(String processFile, int cycleNum, int pid, int priority) {
		this.processName = processFile.substring(0,processFile.indexOf("."));
		this.numOfCycles = cycleNum;
		this.processId = pid;
		this.priority = priority;
		this.processOperations = new ArrayList<>();
		this.arrivalTime = OSClock.getClock();
		this.processState = ProcessState.NEW;
		this.burstTime = 0;
		this.startTime = 0;
		initializeProcess(processFile);
		calculateBurstTime();
		this.remainingBurstTime = burstTime;
	}


	public void initializeProcess(String processFile) {
		try{
			Scanner processLine = new Scanner(new File(processFile));
			while (processLine.hasNextLine()) {
				processOperations.add(new ProcessOperation(processLine.next(), this));

			}
		}catch (Exception e) {
			System.out.println("Error reading contents of the file...");
			e.printStackTrace();
		}
	}

	public void calculateBurstTime() {
		for (int i = 0; i < processOperations.size(); i++) {
			if(processOperations.get(i).getOpType() == OperationType.CALCULATE) {
				burstTime += processOperations.get(i).getRunTime();
			}
		}
	}

	synchronized public void executeTick() {
		System.out.println("executing process ID: " + processId);

		// pretend work
		for( int counter = 0; counter < 50; counter++ ) {
			int something = 2;
			something *= something;
		}

	}

	public void setProcessState(ProcessState pState){
		this.processState = pState;
	}

	public ProcessState getProcessState() {
		return this.processState;
	}

	public void addProcessOperations(ProcessOperation po) {
		processOperations.add(po);
	}

	public int getProcessId() {
		return this.processId;
	}

	public int getPriority() {
		return this.getPriority();
	}

	public String getProcessName() { return this.processName; }

	public int getArrivalTime() {
		return this.arrivalTime;
	}

	public int getBurstTime() {
		return this.burstTime;
	}

	public int getWaitTime() {
		return this.waitTime;
	}

	public int getTurnAroundTime() {
		return this.turnAroundTime;
	}

	public int getRoundWaitTime(){
		return this.roundWaitTime;
	}

	public int getRoundTurnAroundTime() {
		return this.roundTurnAroundTime;
	}

	public void setWaitTime(int wt) {
		this.waitTime = wt;
	}

	public void setTurnAroundTime(int turnAt) {
		this.turnAroundTime = turnAt;
	}

	public void setRoundWaitTime(int roundWt) {
		this.roundWaitTime = roundWt;
	}

	public void setRoundTurnAroundTime(int roundTurnAt) {
		this.roundTurnAroundTime = roundTurnAt;
	}

	public void setRemainingBurstTime(int remainingBT) {
		this.remainingBurstTime = remainingBT;
	}

	public int getRemainingBurstTime() { return this.remainingBurstTime; }

	public int getStartTime() {
		return this.startTime;
	}
	public void setStartTime(int st) {
		this.startTime = st;
	}

}
