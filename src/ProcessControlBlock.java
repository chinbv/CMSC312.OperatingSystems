import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ProcessControlBlock {

	public enum processState {
		NEW, READY, RUN, WAIT, EXIT;
	};

	public enum scriptCommands {
		CALCULATE, IO, YIELD, OUT, EXE;
	};

	private int _processID;
	private int _priority;
	private String processName;
	private int remainingBurstTime;
	private int burstTime;
	processState _currentState;
	ArrayList<VMPageInfo> _memoryAllocations;
	private int simulationJobTicksRemaining;	// for simulation purposes, how long to run
	public ArrayList<ProcessOperation> processOperations;
	private int lastCommandReadIndex;

	scriptCommands currentScript;

	public ProcessControlBlock(int id, int priority) {

		this._processID = id;
		this._priority = priority;
		this.remainingBurstTime = 0;
		this.burstTime = 0;
		this.processName = "";
		lastCommandReadIndex = -1;
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

	public String getProcessName() {
		return this.processName;
	}

	public processState getProcessState() {
		return _currentState;
	}
	
	public void setProcessState(processState currentState) {
		this._currentState = currentState;
	}

	public void calculateBurstTime() {
		for (int i = 0; i < processOperations.size(); i++) {
			if(processOperations.get(i).getOpType() == scriptCommands.CALCULATE) {
				burstTime += processOperations.get(i).getRunTime();
			}
		}
		this.remainingBurstTime = burstTime;
		this.simulationJobTicksRemaining = remainingBurstTime;

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
		this.processName = fileName;
		Scanner processLine = new Scanner(new File(fileName));
		while (processLine.hasNextLine()) {

			processOperations.add(new ProcessOperation(processLine.nextLine()));
		}
		calculateBurstTime();
	}
	
	synchronized public void executeTick() {
		System.out.println("executing process ID: " + _processID);

		ProcessOperation op = processOperations.get(lastCommandReadIndex + 1);
		Random randomNum = new Random();
		// script command
		switch (op.getOpType()) {
			case CALCULATE:
				int decrementTime = op.getRunTime() -1;
				op.setRunTime(decrementTime);
				if (op.getRunTime() == 0) {
					lastCommandReadIndex++;
				}
				break;
			case IO:
				// IO Interrupt
				this.setProcessState(processState.WAIT);
				int ioTime = randomNum.nextInt(50) + 1;
				break;
			case YIELD:
				break;
			case OUT:
				this.processPCBInfo();
				break;
			case EXE:
				this.setProcessState(processState.EXIT);
				break;
		}
		
		simulationJobTicksRemaining--;
		if( simulationJobTicksRemaining == 0) {
			this.setProcessState(processState.EXIT);
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

	public void processPCBInfo() {
		System.out.println("");
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
