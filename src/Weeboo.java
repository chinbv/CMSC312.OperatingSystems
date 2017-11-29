//import Processes.Process;
//import Processes.ProcessManager;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.Scanner;
//
//
//public class Weeboo {
//
//	int processIdCounter = 1;
//
////	private static VirtualMemoryManager _memoryManager = new VirtualMemoryManager();
//	private static ProcessManager _processManager = new ProcessManager();
//
//
//	public static void main(String[] args) throws IOException {
//		// TODO Auto-generated method stub
//		boolean complete = false;
//
//
//		Scanner in = new Scanner(System.in);
//
//		System.out.print("Weeboo:~ welcome$ " + "\n");
//
////		File directory = new File("./");
////		System.out.println(directory.getAbsolutePath());
//
//		System.out.print("Please enter file name: "+ "\n");
//
//		String nameOfFile = in.nextLine();
//
//		String pathOfFileLoaded = "/Users/brandonc/Developer/workspace/CMSC312.OperatingSystem/src/" + nameOfFile;
//		System.out.println(pathOfFileLoaded);
//
//
//		File loadFilePath = new File(pathOfFileLoaded);
//
////		Path testFile1 = Paths.get(nameOfFile);
//		//main loop
//		int osCounter = 0;
////		Path myPath = testFile1;
//		Path myPath = loadFilePath.toPath();
//		System.out.print("Path: "+ myPath + "\n");
//
////		Process process = Weeboo.processManager().createProcess(myPath);
//
//		BufferedReader fileReader = new BufferedReader(new FileReader(loadFilePath));
//		String line = null;
//		while ((line = fileReader.readLine()) != null) {
//			System.out.println(line);
//		}
//
//		while(!complete) {
//
//
//			//load a process
//
//
//			//execute a process (calculate)
//
//			Weeboo.processManager().dumpProcessArrayContents();
//
//			//exit
//			System.out.println("Time: " + osCounter);
//			if(osCounter == 10) {
//
//				complete = true;
//			}
//			osCounter++;
//		}
//
//		System.out.println("Exiting");
//	}
//
//
//	/**
//	 * @return the memoryManager
//	 */
////	public static VirtualMemoryManager memoryManager() {
////		return _memoryManager;
////	}
//
//	/**
//	 * @return the processManager
//	 */
//	public static ProcessManager processManager() {
//		return _processManager;
//	}
//
//}
