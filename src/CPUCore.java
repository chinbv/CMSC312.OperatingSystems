

public class CPUCore extends Thread {

	private CPU parentCPU;
	@SuppressWarnings("unused")
	private ProcessControlBlock assignedPCB = null;
	private Object schedulerLock;
	private boolean stopRequested = false;
	private int coreIndex;
	
	public CPUCore(CPU aCPU, int aCoreIndex) {
		parentCPU = aCPU;
		schedulerLock = ProcessManager.schedulerLock();
		coreIndex = aCoreIndex;
	}

	synchronized public void assignProcess(ProcessControlBlock aPCB) {
//		System.out.println("process " + this.assignedPCB.processID() + " assigned");
		this.assignedPCB = aPCB;
		if( aPCB != null) {
			aPCB.setProcessState(ProcessControlBlock.processState.RUN);
		}
	}
	
	public ProcessControlBlock assignedProcess() {
		return assignedPCB;
	}
	

	public void run() {
		synchronized (schedulerLock) {
			try {
				while( stopRequested != true ) {
					//System.out.println(parentCPU.cpuID() + ":" + coreIndex + " before schedulerLock");
					schedulerLock.wait();
					//System.out.println(parentCPU.cpuID() + ":" + coreIndex + " after schedulerLock");
					
					if( assignedPCB != null ) {
						System.out.println(parentCPU.cpuID() + ":" + coreIndex + " executing tick");
						System.out.println(assignedPCB.getProcessName() + " boolean: "+(assignedPCB == null));
						assignedPCB.executeTick();
					} else {
						System.out.println(parentCPU.cpuID() + ":" + coreIndex + " no process to execute");
					}
				}
				
			}
			catch( InterruptedException e) {
				
			}
		}
	}
	
	synchronized public void requestStop() {
		stopRequested = true;
	}
	
}
