import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;


public class ProcessManager {

	private ArrayList<ProcessControlBlock> listOfProcesses;
	int lastAssignedProcessId = 0;
	int processPriority = 0;
	private static Object _schedulerLock = null;
	private ArrayList<ProcessControlBlock> _zombieProcesses;
	
	private ArrayList<VMPageInfo> _sharedLibraryPages = null;

	public ProcessManager() {
		listOfProcesses = new ArrayList<ProcessControlBlock>();
		
		// allocate shared memory library pages
		// there assume 64 megabytes
		_sharedLibraryPages = Weeboo.memoryManager().allocateSharedMemory((long)64 * 1024 * 1024);
		
		_zombieProcesses = new ArrayList<ProcessControlBlock>();
		
	}

	public ProcessControlBlock createProcessControlBlock(String pathString) throws IOException {

		System.out.println("creating process executing " + pathString);
		Path executablePath = FileSystems.getDefault().getPath(pathString);
		long fileSize;
		
		// stat call using file management system to get size of file
		try {
			fileSize = Files.size(executablePath);
			
			
		} catch (Exception e) {
			// need to handle this properly, but for now, just set fileSize statically
			fileSize = 252;
		}

		int processId = nextAvailableProcessId();
		int priority = assigningPriority();
		//
		//States: NEW(1), READY(2), RUN(3), WAIT(4), EXIT(5)
		ProcessControlBlock newPCB = new ProcessControlBlock(processId, priority);
		
		newPCB.setProcessState(ProcessControlBlock.processState.NEW);

		// allocate memory for the process
		//newPCB.allocateMemory(fileSize);
		newPCB.loadExecutable(pathString);
		newPCB.setSharedLibraryPages(_sharedLibraryPages);
   
		listOfProcesses.add(newPCB);
		
		newPCB.setProcessState(ProcessControlBlock.processState.READY);
		return newPCB;
	}
	
	public ArrayList<ProcessControlBlock> readyQueue() {
		ArrayList<ProcessControlBlock> readyProcesses = new ArrayList<ProcessControlBlock>();
		
		Iterator<ProcessControlBlock> processIterator = listOfProcesses.iterator();
		while( processIterator.hasNext()) {
			ProcessControlBlock aProcess = processIterator.next();
			
			if( aProcess.getProcessState() == ProcessControlBlock.processState.READY ) {
				readyProcesses.add(aProcess);
			}
		}
		return readyProcesses;
	}
	
	
	public ArrayList<ProcessControlBlock> waitingQueue() {
		ArrayList<ProcessControlBlock> readyProcesses = new ArrayList<ProcessControlBlock>();
		
		Iterator<ProcessControlBlock> processIterator = listOfProcesses.iterator();
		while( processIterator.hasNext()) {
			ProcessControlBlock aProcess = processIterator.next();
			
			if( aProcess.getProcessState() == ProcessControlBlock.processState.WAIT ) {
				readyProcesses.add(aProcess);
			}
		}
		return readyProcesses;
	}
	
	
	public static Object schedulerLock() {
		if( _schedulerLock == null ) {
			_schedulerLock = new Object();
		}
		return _schedulerLock;
	}

	private int nextAvailableProcessId() {
		// needs to be fixed to deal with wrap around and already allocated Id's
		int newProcessId = ++lastAssignedProcessId;
		return newProcessId;
	}
	
	private int assigningPriority() {
		// needs to be fixed to deal with wrap around and already allocated Id's
		processPriority = processPriority+1;
		return processPriority;
	}
	
	public void exitProcess(ProcessControlBlock aPCB) {
		_zombieProcesses.add(aPCB);
	}
	
	public void cleanUp() {
		listOfProcesses.removeAll(_zombieProcesses);
		_zombieProcesses.clear();
	}

	public void dumpProcessArrayContents() {
		Iterator<ProcessControlBlock> processListIterator = listOfProcesses.iterator();
		if (processListIterator.hasNext() == false) {
			System.out.println("No unfinished processes.");
		}
		while(processListIterator.hasNext() == true) {
			ProcessControlBlock aProcess = processListIterator.next();
//			int avgWaitTime = 0;
//			int avgTurnAroundtime = 0;
//
//			int pCounter =0;
			if (aProcess.getProcessState() != ProcessControlBlock.processState.EXIT) {
				aProcess.getProcessPCBInfo();
//				avgWaitTime +=aProcess.getWaitTime();
//				avgTurnAroundtime += aProcess.getTurnAroundTime();
//				pCounter++;
			}
//
//			System.out.print("Scheduler Info: \t");
//			System.out.print("Avg Wait Time: "+ (avgWaitTime/pCounter) + "\t");
//			System.out.println("Avg Turn Around Time: " + (avgTurnAroundtime/pCounter));
		}
	}

}
