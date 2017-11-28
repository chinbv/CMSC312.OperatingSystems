

public class CPUCore extends Thread {

	private CPU parentCPU;
	@SuppressWarnings("unused")
	private ProcessControlBlock assignedPCB = null;
	private Object schedulerLock;
	private boolean stopRequested = false;
	
	public CPUCore(CPU aCPU) {
		parentCPU = aCPU;
		schedulerLock = ProcessManager.schedulerLock();
	}

	synchronized public void assignProcess(ProcessControlBlock aPCB) {
		this.assignedPCB = aPCB;
	}
	

	public void run() {
		synchronized (schedulerLock) {
			try {
				while( stopRequested != true ) {
					System.out.println("Core: before schedulerLock");
					schedulerLock.wait();
					System.out.println("Core: before schedulerLock");
					
					if( assignedPCB != null ) {
						assignedPCB.executeTick();
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
