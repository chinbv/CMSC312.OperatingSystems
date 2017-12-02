import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ProcessControlBlock {

	public enum processState {
		NEW, READY, RUN, WAIT, EXIT;
	}

	;

	public enum scriptCommands {
		CALCULATE, IO, YIELD, OUT, EXE;
	}

	;

	private int _processID;
	private int _priority;
	private String processName;
	private int remainingBurstTime;
	private int burstTime;
	processState _currentState;
	ArrayList<VMPageInfo> _memoryAllocations;
	private int simulationJobTicksRemaining;    // for simulation purposes, how long to run
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
			if (processOperations.get(i).getOpType() == scriptCommands.CALCULATE) {
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
		int counter =0;
		while (processLine.hasNextLine()) {
			if (counter == 0){
				int processMemory = processLine.nextInt();
				
				allocateMemory((long)processMemory*1024*1024);
				processLine.nextLine();
			} else {
				String line = processLine.nextLine();
				System.out.println(line);
				processOperations.add(new ProcessOperation(line));
			}
			counter++;

		}
		calculateBurstTime();
	}

	synchronized public void executeTick() {

		//normally, pages are brought in individually on demand
		//but for the simulation, we bring all the pages in
		Weeboo.memoryManager().swapInProcess(this);

		System.out.println("executing process ID: " + _processID);
		//allocate memory in first tick
		ProcessOperation op = processOperations.get(lastCommandReadIndex + 1);
		Random randomNum = new Random();

		// script command
		switch (op.getOpType()) {
			case CALCULATE:
				int decrementTime = op.getRunTime() - 1;
				op.setRunTime(decrementTime);
				if (op.getRunTime() == 0) {
					lastCommandReadIndex++;
				}
				simulationJobTicksRemaining--;
				break;
			case IO:
				// IO Interrupt
				this.setProcessState(processState.WAIT);
				IORequest newIORequest = new IORequest(this);
				newIORequest.requestIO();
				break;
			case YIELD:
				break;
			case OUT:
				this.getProcessPCBInfo();
				break;
			case EXE:
				this.setProcessState(processState.EXIT);
				break;
		}


		if (simulationJobTicksRemaining == 0) {
			this.setProcessState(processState.EXIT);
		}

	}

	public boolean allocateMemory(long amount) {
		ArrayList<VMPageInfo> newPagesList = Weeboo.memoryManager().malloc(this, amount);

		if (newPagesList != null) {
			_memoryAllocations.addAll(newPagesList);
			return true;
		}
		// else
		return false;

	}

	public ArrayList<VMPageInfo> allMemoryPages() {
		return _memoryAllocations;
	}

	public int getSimulationJobTicksRemaining() {
		return this.simulationJobTicksRemaining;
	}

	public void getProcessPCBInfo() {
		System.out.print("PID: " + this.processID() + "\t");
		System.out.print("Process Name: " + this.getProcessName() + "\t");
		System.out.print("State: " + this.getProcessState().toString() + "\t");
		System.out.print("Runtime: " + this.getBurstTime() + "\t");
		System.out.print("Remaining Burst Time: " + this.getSimulationJobTicksRemaining() + "\t");
		if (this.priority() != 0) {
			System.out.print("Priority: " + this.priority() + "\t");
		}
		System.out.println("Number of I/O bursts: ");
	}
	
	public void ioCompletionHandler(IORequest _completedIORequest) {
	
		//process state is wait and needs to be set to ready
		this.setProcessState(processState.READY);
		
	}
	
}
