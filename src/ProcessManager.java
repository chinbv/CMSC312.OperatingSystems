
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;


public class ProcessManager {

	public ArrayList<Process> listOfProcesses;
	int lastAssignedProcessId = 0;
	int processPriority = 0;
	private static Object _schedulerLock = null;


	public ProcessManager() {
		listOfProcesses = new ArrayList<>();
	}

	public void createProcess(String process_file, int cyclesNum){
		Process newProcess = new Process(process_file, cyclesNum, nextAvailableProcessId(), assigningPriority());
		listOfProcesses.add(newProcess);
		// load process into memory here
		//Brandon's code. Is this
		int[] memoryBlock;
		String executableFilename = "";
		try {
			Path executablePath = FileSystems.getDefault().getPath(executableFilename);
			// stat call using file managment system to get size of file

			long fileSize = Files.size(executablePath);
			// buffered reader
			// memoryBlock = Weeboo.memoryManager().allocate(fileSize);
		} catch(Exception e) {
			e.printStackTrace();
		}



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

	public static Object schedulerLock() {
		if( _schedulerLock == null ) {
			_schedulerLock = new Object();
		}
		return _schedulerLock;
	}

	public ArrayList<Process> readyQueue() {
		ArrayList<Process> readyProcesses = new ArrayList<>();

		Iterator<Process> processIterator = listOfProcesses.iterator();
		while( processIterator.hasNext()) {
			Process aProcess = processIterator.next();

			if( aProcess.getProcessState() == ProcessState.READY ) {
				readyProcesses.add(aProcess);
			}
		}
		return readyProcesses;
	}

	public void PCBInfo() {
		Iterator<Process> processListIterator = listOfProcesses.iterator();
		while(processListIterator.hasNext() == true) {
			Process p = processListIterator.next();

			System.out.print("Process Name: " + p.getProcessName() + "\t");
			System.out.print("State: " + p.getProcessState().toString() + "\t");
			System.out.print("Runtime: " + p.getBurstTime() + "\t");
			System.out.print("Remaining Burst Time: " + p.getRemainingBurstTime() + "\t");
			if (p.getPriority() != 0) {
				System.out.print("Priority: " + p.getPriority() + "\t");
			}
			System.out.print("Number of I/O bursts: " + p.getRemainingBurstTime() + "\t");
		}
	}

}


