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
	private static InterruptHandler _systemInterruptHandler = null;
	private static Terminal terminal;
	public static GUI gui;
	private static ArrayList<CPU> cpuArray = null;
	private static OSRunLoop _osRunLoop = null;
	private static int schedulerChoosen;

	public static int exeForNumOfCycles = 0;
	public static boolean runSimulatenously = false;
	static int numberOfCPUs = 2;
	static int numberOfCoresPerCPU = 4;
		
	
	public static void main(String[] args) throws IOException {
		
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Weeboo:~ Booting$ " + "\n");
		
		// initialize CPUs
		System.out.print("Initializing CPUs$...." + "\n");
//		System.out.print("Weeboo: Welcome to Weeboo!$ " + "\n");

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
		launchGUI();
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
//		jobFileName = "TestFile1.txt";
		try {
			Scanner readJobFile = new Scanner(new File(jobFileName));

			synchronized (OSRunLoop.runLoopLock()) {
				HashMap<Integer, ArrayList<String>> simulationJobs = osRunLoop().simulationJobs();

				int schedulerDemarker = readJobFile.nextInt();
				changingScheduler(schedulerDemarker);
				
				while(readJobFile.hasNextLine()) {
					ArrayList<String> elementsEntered = new ArrayList<>();
					String command = readJobFile.next();
					System.out.println(command);
					if(command.toUpperCase().equals("LOAD")) {
						int cycleNum = readJobFile.nextInt();
						String processFile = readJobFile.next();
						System.out.println("processFile inside Weeboo:" + processFile);
						
							
						System.out.println("read job file " + processFile + " " + cycleNum);
						if (simulationJobs.containsKey(cycleNum)) {
							ArrayList<String> existingCycleTime = simulationJobs.get(cycleNum);
							existingCycleTime.add(processFile);
							simulationJobs.put(cycleNum, existingCycleTime);
						} else {
							ArrayList<String> newCycleTime = new ArrayList<String>();
							newCycleTime.add(processFile);
							simulationJobs.put(cycleNum, newCycleTime);
						}

					}
					
				}

				System.out.println(simulationJobs.keySet() + "\t " +simulationJobs.values());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void changingScheduler(int demarker) {
		
		switch(demarker) {
		case 1: _systemScheduler = new RoundRobinScheduler();
				schedulerChoosen = 1;
				System.out.println("Round Robin Scheduler selected.");
			break;
		
		case 2: _systemScheduler = new FIFOScheduler();
				schedulerChoosen = 2;
				System.out.println("First In, First Out Scheduler selected.");
			break;
			
		case 3: _systemScheduler = new SJFScheduler();
				schedulerChoosen=3;
				System.out.println("Shortest Job First Scheduler selected.");
			break;
			
		default: demarker = 2;
		} 
		
	}

	public static int getSchedulerChoosen() {
		return schedulerChoosen;
	}

	public static void launchTerminal(){
		terminal = new Terminal();
		terminal.initializeTerminal();
	}

	public static void launchGUI()
	{
		gui = new GUI();
		gui.setVisible(true);
	}
	
	public static void kernelPanic(String customMessage) {
		System.out.println("kernel message " + customMessage);
		
		// should simulate a kernel panic and stop
		return;
	}	
	
	public static void reset() {
		_memoryManager = new VirtualMemoryManager();
		_processManager = new ProcessManager();
		_systemScheduler = null;
		_systemInterruptHandler = null;
		// resert cpu cores
		 cpuArray = new ArrayList<CPU>();
		exeForNumOfCycles = 0;
		runSimulatenously = false;

		for( int cpuCounter = 0; cpuCounter < numberOfCPUs; cpuCounter++ ) {
			CPU newCPU = new CPU("CPU-" + cpuCounter, numberOfCoresPerCPU );
			cpuArray.add(newCPU);
			newCPU.initialize();
		}

		//reset loop
		_osRunLoop = new OSRunLoop();
		_osRunLoop.start();
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
			_systemScheduler = new FIFOScheduler();
		}
		return _systemScheduler;
	}
	
	public static InterruptHandler interruptHandler() {
		if( _systemInterruptHandler == null ) {
			_systemInterruptHandler = new InterruptHandler();
		}
		return _systemInterruptHandler;
	}
	
	
	public static OSRunLoop osRunLoop() {
		return _osRunLoop;
	}
}
