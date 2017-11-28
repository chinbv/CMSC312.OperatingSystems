
public class Process {

	int id;
	int priority;
//	String executableName;
	processState currentState;
	
	scriptCommands currentScript;
	
	public enum processState {
		NEW, READY, RUN, WAIT, EXIT;
	};
	
	public enum scriptCommands {
		CALCULATE, IO, YIELD, OUT;
	};
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
//	public String getName() {
//		return executableName;
//	}
//	
//	public void setName(String executableName) {
//		this.executableName = executableName;
//	}
//	
	public processState getProcessState() {
		return currentState;
	}
	
	public void setProcessState(processState currentState) {
		this.currentState = currentState;
	}
	
	public scriptCommands getScriptCommands() {
		return currentScript;
	}
	
	public void setScriptCommands(scriptCommands currentScript) {
		this.currentScript = currentScript;
	}
	
	public String stringForProcessState() {
		//States: NEW(1), READY(2), RUN(3), WAIT(4), EXIT(5)

		String returnString = null;
		switch(currentState) {
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

//	
	
	public Process(int id, int priority) {
		
		this.id = id;
		this.priority = priority;
//		this.executableName = executableName;
		this.currentState = processState.NEW;
		
	}
	
}
