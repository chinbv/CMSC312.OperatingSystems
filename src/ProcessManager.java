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
	private static Object schedulerLock = null;
	

	public ProcessManager() {
		listOfProcesses = new ArrayList<ProcessControlBlock>();
	}

	public ProcessControlBlock createProcess(Path path) throws IOException {

		int[] memoryBlock;
		String loadFilePath = "";

		Path executablePath = FileSystems.getDefault().getPath(loadFilePath);

		int processId = nextAvailableProcessId();
		int priority = assigningPriority();
		//
		//States: NEW(1), READY(2), RUN(3), WAIT(4), EXIT(5)
		ProcessControlBlock process = new ProcessControlBlock(processId, priority);
		// stat call using file managment system to get size of file
		long fileSize = Files.size(executablePath);
		// buffered reader
		memoryBlock = Weeboo.memoryManager().allocate(fileSize);
		// System.out.println("Max Size: " +
		// Weeboo.memoryManager().maxMemorySize + " CFMS: "
		// + Weeboo.memoryManager().currentFreeMemorySize);
		listOfProcesses.add(process);
		return process;
	}
	
	public static Object schedulerLock() {
		
		return schedulerLock;
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
