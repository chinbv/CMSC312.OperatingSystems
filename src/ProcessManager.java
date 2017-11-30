import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

public class ProcessManager {

	private ArrayList<ProcessControlBlock> listOfProcesses;
	int lastAssignedProcessId = 1;
	int processPriority = 0;
	private static Object _schedulerLock = null;
	

	public ProcessManager() {
		listOfProcesses = new ArrayList<ProcessControlBlock>();
	}

	public ProcessControlBlock createProcessControlBlock(Path path) throws IOException {

		String executableFilename = "";

		Path executablePath = FileSystems.getDefault().getPath(executableFilename);

		int processId = nextAvailableProcessId();
		int priority = assigningPriority();
		//
		//States: NEW(1), READY(2), RUN(3), WAIT(4), EXIT(5)
		ProcessControlBlock newPCB = new ProcessControlBlock(processId, priority);
		newPCB.loadExecutable(executableFilename);
		// stat call using file managment system to get size of file
		long fileSize = Files.size(executablePath);
		// buffered reader
		newPCB.allocateMemory(fileSize);
		
        
		// System.out.println("Max Size: " +
		// Weeboo.memoryManager().maxMemorySize + " CFMS: "
		// + Weeboo.memoryManager().currentFreeMemorySize);
		listOfProcesses.add(newPCB);
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
	

	public void dumpProcessArrayContents() {

		Iterator<ProcessControlBlock> processListIterator = listOfProcesses.iterator();
		while(processListIterator.hasNext() == true) {
			ProcessControlBlock aProcess = processListIterator.next();
			
			System.out.println("PID: " + aProcess.processID() + " Priority: " + aProcess.priority() + " State: " + aProcess.stringForProcessState());
		}
	}

}