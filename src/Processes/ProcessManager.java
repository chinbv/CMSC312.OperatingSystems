package Processes;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Enum.ProcessState;

public class ProcessManager {

	public ArrayList<Process> listOfProcesses;
	int lastAssignedProcessId = 0;
	int processPriority = 0;

	public ProcessManager() {
		listOfProcesses = new ArrayList<>();
	}

	public void createProcess(String process_file, int cyclesNum) {
		Process newProcess = new Process(process_file, cyclesNum, nextAvailableProcessId(), assigningPriority());
		listOfProcesses.add(newProcess);
		// load process into memory here
		newProcess.setProcessState(ProcessState.READY);

	}

	public void resetAllProcesses() {
		listOfProcesses.clear();
	}

	private int nextAvailableProcessId() {
		// needs to be fixed to deal with wrap around and already allocated Id's
		return ++lastAssignedProcessId;
	}
	
	private int assigningPriority() {
		// needs to be fixed to deal with wrap around and already allocated Id's
		processPriority +=1;
		return processPriority;
	}
	

	public void dumpProcessArrayContents() {
		Iterator<Process> processListIterator = listOfProcesses.iterator();
		while(processListIterator.hasNext() == true) {
			Process aProcess = processListIterator.next();
			
			System.out.println("PID: " + aProcess.getProcessId() + " Priority: " + aProcess.getPriority() + " State: " + aProcess.getProcessState());
		}
	}

}



//	public Process createProcess(String ) throws IOException {
//
//		int[] memoryBlock;
//		String loadFilePath = "";
//
//		Path executablePath = FileSystems.getDefault().getPath(loadFilePath);
//
//		// stat call using file managment system to get size of file
//		long fileSize = Files.size(executablePath);
//		// buffered reader
//		// memoryBlock = Weeboo.memoryManager().allocation(fileSize);
//		// System.out.println("Max Size: " +
//		// Weeboo.memoryManager().maxMemorySize + " CFMS: "
//		// + Weeboo.memoryManager().currentFreeMemorySize);
//
//	}
