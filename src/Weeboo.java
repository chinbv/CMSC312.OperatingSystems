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
import java.util.Random;
import java.util.Scanner;


public class Weeboo {
	
	int processIdCounter = 1;
	
	private static VirtualMemoryManager _memoryManager = new VirtualMemoryManager();
	private static ProcessManager _processManager = new ProcessManager();
	private static ArrayList<CPU> cpuArray = null;

	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boolean complete = false;
		int numberOfCPUs = 2;
		int numberOfCoresPerCPU = 4;

		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Weeboo:~ Booting$ " + "\n");
		
		// initialize CPUs
		System.out.print("Initializing CPUs$ " + "\n");

		cpuArray = new ArrayList<CPU>();
		for( int cpuCounter = 0; cpuCounter < numberOfCPUs; cpuCounter++ ) {
			System.out.print("CPU: " + cpuCounter + "\n");

			CPU newCPU = new CPU(numberOfCoresPerCPU);
			cpuArray.add(newCPU);
			newCPU.initialize();
		}
		
		
//		File directory = new File("./");
//		System.out.println(directory.getAbsolutePath());

		System.out.print("Please enter file name: "+ "\n");

		String nameOfFile = in.nextLine();

		String pathOfFileLoaded = "/Users/brandonc/Developer/workspace/CMSC312.OperatingSystem/src/" + nameOfFile;
		System.out.println(pathOfFileLoaded);

		
		File loadFilePath = new File(pathOfFileLoaded);

//		Path testFile1 = Paths.get(nameOfFile);
		//main loop
		int osCounter = 0;
//		Path myPath = testFile1;
		Path myPath = loadFilePath.toPath();
		System.out.print("Path: "+ myPath + "\n");

		ProcessControlBlock process = Weeboo.processManager().createProcess(myPath);
		
		BufferedReader fileReader = new BufferedReader(new FileReader(loadFilePath));
		String line = null;
		while ((line = fileReader.readLine()) != null) {
			System.out.println(line);
		}
		
		while(!complete) {
			
		
			//load a process
			
			
			//execute a process (calculate)
			
			Weeboo.processManager().dumpProcessArrayContents();
			
			//exit
			System.out.println("Time: " + osCounter);
			if(osCounter == 10) {
				
				complete = true;
			}
			osCounter++;
		}
		
		System.out.println("Exiting");
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
