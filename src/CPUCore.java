import java.util.ArrayList;



public class CPUCore extends Thread {

	private CPU parentCPU;
	@SuppressWarnings("unused")
	private ProcessControlBlock assignedPCB = null;
	private Object schedulerLock;
	private boolean stopRequested = false;
	private boolean _interrupted = false;
	private int coreIndex;
	private IORequest _completedIORequest = null;
	
	private ArrayList<VMPageInfo> _coreCache = null;

	
	public CPUCore(CPU aCPU, int aCoreIndex) {
		parentCPU = aCPU;
		schedulerLock = ProcessManager.schedulerLock();
		coreIndex = aCoreIndex;
		
		_coreCache = new ArrayList<VMPageInfo>();
	}

	synchronized public void assignProcess(ProcessControlBlock aPCB) {
//		System.out.println("process " + this.assignedPCB.processID() + " assigned");
		
		if( assignedPCB != null && assignedPCB != aPCB ) {
			dequeueProcess();
		}
		
		
		this.assignedPCB = aPCB;

		if( aPCB != null) {
			// load pages for process into this core's cache
			_coreCache.addAll(aPCB.allMemoryPages());

			aPCB.setProcessState(ProcessControlBlock.processState.RUN);
		}
	}
	
	private void dequeueProcess() {
		// dequeue
		this.assignedPCB.setProcessState(ProcessControlBlock.processState.READY);
		this.assignedPCB = null;

		// flush cache
		_coreCache.clear();
	}
	
	public ProcessControlBlock assignedProcess() {
		return assignedPCB;
	}
	

	public void run() {
		synchronized (schedulerLock) {
			try {
				boolean roundRobinCycle = false;
				System.out.println("inside run method");
				while( stopRequested != true ) {
					//System.out.println(parentCPU.cpuID() + ":" + coreIndex + " before schedulerLock");
					schedulerLock.wait();
					//System.out.println(parentCPU.cpuID() + ":" + coreIndex + " after schedulerLock");

					if( assignedPCB != null) {
						System.out.println(parentCPU.cpuID() + ":" + coreIndex + " executing tick");

						assignedPCB.executeTick();
					} else {
						
						// process interrupt
						if( _interrupted == true ) {
							_completedIORequest.getPCB().ioCompletionHandler(_completedIORequest);
							_completedIORequest = null;
							_interrupted = false;
						}
						
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
	
	public void interruptCore(IORequest anIORequest) {
		_interrupted = true;
		
		// dequeue running process if any
		if( this.assignedPCB != null) {
			dequeueProcess();
			
		}
		
		_completedIORequest = anIORequest;
	}
	
	public boolean isIdle() {
		if( isInterrupted() == true || this.assignedPCB != null) {
			return false;
		}
		
		return true;
	}
}
