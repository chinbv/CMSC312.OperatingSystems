import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;


public class ProcessControlBlock {

	public enum processState {
		NEW, READY, RUN, WAIT, EXIT;
	};

	public enum scriptCommands {
		CALCULATE, IO, YIELD, OUT;
	};

	private int _processID;
	private int _priority;
	private int remainingBurstTime;
	private int burstTime;
	processState _currentState;
	ArrayList<VMPageInfo> _memoryAllocations;
	private int simulationJobTicksRemaining;	// for simulation purposes, how long to run
	public ArrayList<ProcessOperation> processOperations;

	scriptCommands currentScript;

	public ProcessControlBlock(int id, int priority) {

		this._processID = id;
		this._priority = priority;
		this.remainingBurstTime = 0;
		this.burstTime = 0;
		this._currentState = processState.NEW;
		this._memoryAllocations = new ArrayList<VMPageInfo>();
		this.processOperations = new ArrayList<>();
	}
	

	

	public int processID() {
		return _processID;
	}

	public void setId(int id) {
		this._processID = id;
	}
	
	public int priority() {
		return _priority;
	}
	
	public void setPriority(int priority) {
		this._priority = priority;
	}

	public processState getProcessState() {
		return _currentState;
	}
	
	public void setProcessState(processState currentState) {
		this._currentState = currentState;
		
		// for simulation
		simulationJobTicksRemaining = 5;
	}

	public void calculateBurstTime() {
		for (int i = 0; i < processOperations.size(); i++) {
			if(processOperations.get(i).getOpType() == scriptCommands.CALCULATE) {
				burstTime += processOperations.get(i).getRunTime();
			}
		}
		this.remainingBurstTime = burstTime;
	}

	public int getRemainingBurstTime() {
		return this.remainingBurstTime;
	}

	public void setRemainingBurstTime(int rbt) {
		this.remainingBurstTime = rbt;
	}

	public int getBurstTime() {
		return this.burstTime;
	}
	
	public void loadExecutable(String fileName) throws IOException {
		Scanner processLine = new Scanner(new File(fileName));
		while (processLine.hasNextLine()) {
			processOperations.add(new ProcessOperation(processLine.nextLine()));
		}
		calculateBurstTime();
	}
	
	synchronized public void executeTick() {
		System.out.println("executing process ID: " + _processID);
		
		// pretend work
		for( int counter = 0; counter < 50; counter++ ) {
			int something = 2;
			something *= something;
		}
		
		simulationJobTicksRemaining--;
		if( simulationJobTicksRemaining == 0) {
			this.setProcessState(ProcessControlBlock.processState.READY);
		}
		
	}

	public boolean allocateMemory(long amount) {
		ArrayList<VMPageInfo> newPagesList = Weeboo.memoryManager().malloc(this, amount);
		
		if( newPagesList != null ) {
			_memoryAllocations.addAll(newPagesList);
			return true;
		}
		// else
		return false;
		
	}

	public String stringForProcessState() {
		//States: NEW(1), READY(2), RUN(3), WAIT(4), EXIT(5)

		String returnString = null;
		switch(_currentState) {
			case NEW:  returnString = "NEW";
				break;

			case READY: returnString = "READY";
				break;

			case RUN: returnString = "RUN";
				break;

			case WAIT: returnString = "WAIT";//Blocked
				break;

			case EXIT: returnString = "EXIT";
				break;

			default: returnString = "Unknown";
				break;
		}
		return returnString;
	}

	public String stringScriptOfOperations() {
		//States: Calculate(1), I/O(2), Yield(3), OUT(4)

		String returnString = null;
		switch(currentScript) {
			case CALCULATE:  returnString = "RUN";
				break;

			case IO: returnString = "WAIT";//Blocked
				break;

			case YIELD: returnString = "INTERRUPT";//INTERRUPT, state needs to be written
				break;

			case OUT: returnString = "NEEDS TO BE LIKE PROC";//NEEDS TO BE FINISHED
				break;

			default: returnString = "Unknown";
				break;
		}
		return returnString;
	}
	
}
