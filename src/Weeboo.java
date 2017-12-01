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
	private static OSScheduler _systemScheduler = null;
	private static Terminal terminal;
	private static ArrayList<CPU> cpuArray = null;
	private static OSRunLoop _osRunLoop = null;

	public static int exeForNumOfCycles = 0;
	public static boolean runSimulatenously = false;
	static int numberOfCPUs = 2;
	static int numberOfCoresPerCPU = 4;
		
	
	public static void main(String[] args) throws IOException {
		
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Weeboo:~ Booting$ " + "\n");
		
		// initialize CPUs
		System.out.print("Initializing CPUs$ " + "\n");
		System.out.println("reaches here");
		cpuArray = new ArrayList<CPU>();

		for( int cpuCounter = 0; cpuCounter < numberOfCPUs; cpuCounter++ ) {
//			System.out.print("CPU: " + cpuCounter + "\n");

			CPU newCPU = new CPU("CPU-" + cpuCounter, numberOfCoresPerCPU );
			cpuArray.add(newCPU);
			newCPU.initialize();
		}

		// launch the OS in a separate thread
		_osRunLoop = new OSRunLoop();
		_osRunLoop.start();
		
		launchTerminal();

		
		// need to shut down cores (threads)
		
		System.out.println("Exiting");
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
		// hardcoded file for testing
		jobFileName = "TestFile1.txt";
		try {
			Scanner readJobFile = new Scanner(new File(jobFileName));
			
			synchronized (OSRunLoop.runLoopLock()) {
				HashMap<Integer, ArrayList<String>> simulationJobs = osRunLoop().simulationJobs();

				
				while(readJobFile.hasNextLine()) {
					ArrayList<String> elementsEntered = new ArrayList<>();
					String command = readJobFile.next();
					System.out.println(command);
					if(command.toUpperCase().equals("LOAD")) {
						int cycleNum = readJobFile.nextInt();
						String processFile = readJobFile.next();
						
							
						System.out.println("read job file " + processFile + " " + cycleNum);
						if (simulationJobs.containsKey(cycleNum)) {
							ArrayList<String> existingCycleTime = simulationJobs.get(cycleNum);
							existingCycleTime.add(jobFileName);
							simulationJobs.put(cycleNum, existingCycleTime);
						} else {
							ArrayList<String> newCycleTime = new ArrayList<String>();
							newCycleTime.add(jobFileName);
							simulationJobs.put(cycleNum, newCycleTime);
						}
					
					}
					
				}

				System.out.println(simulationJobs.keySet() + "\t " +simulationJobs.values());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		
		// example setup, should be read in from file
		/*
		ArrayList<String>jobsArray1 = new ArrayList<String>();
		jobsArray1.add("executable1");
		simulationJobs.put(3, jobsArray1);
		
		ArrayList<String>jobsArray2 = new ArrayList<String>();
		jobsArray2.add("executable2");
		simulationJobs.put(7, jobsArray2);
		
		simulationJobs.put(12, jobsArray1);
		*/
		
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
		return _processManager;
	}

	/**
	 * @return the Scheduler
	 */
	public static OSScheduler scheduler() {
		if( _systemScheduler == null ) {
			_systemScheduler = new DumbScheduler();
		}
		return _systemScheduler;
	}
	
	public static OSRunLoop osRunLoop() {
		return _osRunLoop;
	}
}
