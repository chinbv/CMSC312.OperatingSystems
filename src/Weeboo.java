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
	private static Terminal terminal;
	private static ArrayList<CPU> cpuArray = null;

	public static int exeForNumOfCycles = 0;
	public static boolean runSimulatenously = false;
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
//			System.out.print("CPU: " + cpuCounter + "\n");

			CPU newCPU = new CPU("CPU-" + cpuCounter, numberOfCoresPerCPU );
			cpuArray.add(newCPU);
			newCPU.initialize();
		}

		//launchTerminal();
		// needs to load the job file manually instead of terminal
		loadSimulationJobFile("");
		System.out.println("Continues past terminal");
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
			if(osClockTick == 100) {
				complete = true;
			}
			osClockTick++;
		}
		
		// need to shut down cores (threads)
		
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
	
	public static void loadSimulationJobFile(String jobFileName) {

		/*
		if (simulationJobs.containsKey(cycleTime)) {
			ArrayList<String> existingCycleTime = simulationJobs.get(cycleTime);
			existingCycleTime.add(jobFileName);
			simulationJobs.put(cycleTime, existingCycleTime);
		} else {
			ArrayList<String> newCycleTime = new ArrayList<String>();
			newCycleTime.add(jobFileName);
			simulationJobs.put(cycleTime, newCycleTime);
		}
		System.out.println(simulationJobs.keySet() + "\t " +simulationJobs.values());
		*/
		
		
		// example setup, should be read in from file
		ArrayList<String>jobsArray1 = new ArrayList<String>();
		jobsArray1.add("executable1");
		simulationJobs.put(3, jobsArray1);
		
		ArrayList<String>jobsArray2 = new ArrayList<String>();
		jobsArray2.add("executable2");
		simulationJobs.put(7, jobsArray2);
		
		simulationJobs.put(12, jobsArray1);
		
	}

	public static void launchTerminal(){
		terminal = new Terminal();
		terminal.initializeTerminal();
	}
	
	public static void kernelPanic(String customMessage) {
		System.out.println(customMessage);
		
		// should simulate a kernel panic and stop
		return;
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
		System.out.println("reaches here");
		return _processManager;
	}

}
