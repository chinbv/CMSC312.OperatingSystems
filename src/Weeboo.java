import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;


public class Weeboo {
	
	int processIdCounter = 1;
	
	private static VirtualMemoryManager _memoryManager = new VirtualMemoryManager();
	private static ProcessManager _processManager = new ProcessManager();
	private static DumbScheduler _systemScheduler = new DumbScheduler();

	private static ArrayList<CPU> cpuArray = null;

	static int numberOfCPUs = 2;
	static int numberOfCoresPerCPU = 4;

	static int osClockTick = 0;
	
	static boolean hasPendingUIAction = false;

	static HashMap<Integer, ArrayList<String>> simulationJobs;
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boolean complete = false;

		
		simulationJobs = new HashMap<Integer, ArrayList<String>>();
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Weeboo:~ Booting$ " + "\n");
		
		// initialize CPUs
		System.out.print("Initializing CPUs$ " + "\n");

		cpuArray = new ArrayList<CPU>();
		for( int cpuCounter = 0; cpuCounter < numberOfCPUs; cpuCounter++ ) {
			System.out.print("CPU: " + cpuCounter + "\n");

			CPU newCPU = new CPU("CPU-" + cpuCounter, numberOfCoresPerCPU );
			cpuArray.add(newCPU);
			newCPU.initialize();
		}
		
		//main loop


		while(!complete) {
			
		
			checkforUIAction();
			
			checkForSimulationAction();


			// schedule processes
			_systemScheduler.schedule();
			
			//execute a process (calculate)
			// tell each core to execute a tick
			synchronized (ProcessManager.schedulerLock()) {
				ProcessManager.schedulerLock().notifyAll();
			}
			
			Weeboo.processManager().dumpProcessArrayContents();
			
			//exit
			System.out.println("Time: " + osClockTick);
			if(osClockTick == 10) {
				
				complete = true;
			}
			osClockTick++;
		}
		
		System.out.println("Exiting");
	}

	private static void checkforUIAction() {
		if( hasPendingUIAction == true ) {
			// then do that
		}
	}

	private static void checkForSimulationAction() throws IOException {

		// should consult collection of jobs for any actions to be performed
		System.out.println("checking for jobs for tick " + osClockTick);
		ArrayList<String>jobsForTick = simulationJobs.get(osClockTick);
		
		if( jobsForTick != null ) {
			Iterator<String> jobIterator = jobsForTick.iterator();
			
			while( jobIterator.hasNext()) {
				String jobDescription = jobIterator.next();
				
				// jobs are likely to spawn processes
				ProcessControlBlock aPCB = Weeboo.processManager().createProcessControlBlock(jobDescription);
			}
		}
		
	}


	public static ArrayList<CPUCore> allCores() {
		Iterator<CPU> cpuIterator = cpuArray.iterator();
		ArrayList<CPUCore> allCoresList = new ArrayList<CPUCore>();
		
		while( cpuIterator.hasNext()) {
			CPU aCPU = cpuIterator.next();
			
			for( int coreIndex = 0; coreIndex < numberOfCoresPerCPU; coreIndex++) {
				CPUCore aCore = aCPU.coreForIndex(coreIndex);
				
				allCoresList.add(aCore);
			}
		}
		
		return allCoresList;
		
	}
	
	public void loadSimulationJobFile(String jobFileName) {
		// load job file
//		File directory = new File("./");
//		System.out.println(directory.getAbsolutePath());

		//System.out.print("Please enter file name: "+ "\n");

		//String nameOfFile = in.nextLine();

		String pathOfFileLoaded = "/Users/brandonc/Developer/workspace/CMSC312.OperatingSystem/src/" + jobFileName;
		System.out.println(pathOfFileLoaded);

		
		File loadFilePath = new File(pathOfFileLoaded);

//		Path myPath = testFile1;
		Path myPath = loadFilePath.toPath();
		System.out.print("Path: "+ myPath + "\n");
		
		// put each job into a collection of jobs with the right clock tick to spawn

		
		// create dictionary of jobs
		
//		Path testFile1 = Paths.get(nameOfFile);

	}
	
	
	
	/**
	 * @return the memoryManager
	 */
	public static VirtualMemoryManager memoryManager() {
		return _memoryManager;
	}

	/**
	 * @return the processManager
	 */
	public static ProcessManager processManager() {
		return _processManager;
	}

}
