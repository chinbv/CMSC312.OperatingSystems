
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
	private int finishTime;
	private int rwt;
	private int sjwaitTime;
	private int sjturnAroundTime;

	private int roundWaitTime;
	private int roundTurnAroundTime;
	private ArrayList<ProcessOperation> processOperations;
	private int numOfCycles;
	private ProcessState processState;
	ArrayList<VMPageInfo> _memoryAllocations;

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
				processOperations.add(new ProcessOperation(processLine.nextLine(), this));

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

	public boolean allocateMemory(long amount) {
		VMPageInfo newPage = Weeboo.memoryManager().allocate(this, amount);

		if( newPage != null ) {
			_memoryAllocations.add(newPage);
			return true;
		}
		// else
		return false;

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
		this.waitTime += wt;
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

	public void setSJWaitTime(int wt) {
		this.sjwaitTime += wt;
	}

	public void setSJTurnAroundTime(int turnAt) {
		this.sjturnAroundTime = turnAt;
	}
	public int getSJStartTime() {
		return this.startTime;
	}
	public void setSJStartTime(int st) {
		this.startTime = st;
	}

	public int getfStartTime() {
		return this.startTime;
	}
	public void setfStartTime(int st) {
		this.startTime = st;
	}


	public int getRStartTime() {
		return this.startTime;
	}
	public void setRStartTime(int st) {
		this.startTime = st;
	}

	public int getRFinishTime()
	{
		return this.finishTime;
	}

	public void setRFinishTime(int ft)
	{
		this.finishTime = ft;
	}

	public int getLastExecuted()
	{
		return this.finishTime;
	}

	public void setLastFinish(int ft)
	{
		this.finishTime = ft;
	}

	public void setroundHelp(int x)
	{
		rwt+=x;
	}
	public int getroundHelp()
	{
		return rwt;
	}

}
