import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    private String commandEntered = "";
    public Terminal() {

    }

    public void initializeTerminal() {
        while (!commandEntered.toUpperCase().equals("EXIT")) {
            this.readCommand();
        }
        System.out.println("while loop exited");
    }

    public void readCommand() {
    	System.out.print("Weeboo: user$ ");
        Scanner readLine = new Scanner(System.in);
        String commandLineEntered = readLine.nextLine();
        Scanner parseCommand = new Scanner(commandLineEntered);
        String commandRead = parseCommand.next();
        ArrayList<String> elementsEntered = new ArrayList<>();
        while (parseCommand.hasNext()) {
            elementsEntered.add(parseCommand.next());
        }
        switch (commandRead.toUpperCase()) {
            case "PROC":
                proc();
                commandEntered = "PROC";
                break;
            case "PAUSE":
                pause();
                commandEntered = "PAUSE";
                break;
                
            case "MEM":
                commandEntered = "MEM";
                mem();
                break;
            case "LOAD":
                commandEntered = "LOAD";
                String fileName = "";
                fileName = elementsEntered.get(0);
                load(fileName);
                break;
            case "EXE":
                int numOfCycles = -1;
                if (elementsEntered.size() == 1) {
                    numOfCycles = Integer.parseInt(elementsEntered.get(0));
                }
                exe(numOfCycles);
                commandEntered = "EXE";
                break;
            case "RESET":
                commandEntered = "RESET";
                reset();
                break;
            case "EXIT":
                commandEntered = "EXIT";
                exit();
                break;
            default:
                commandEntered = "INVALID";
                System.out.println("Please enter a valid command.");
        }
        System.out.println();
    }

    public void proc() {
        Weeboo.processManager().dumpProcessArrayContents();

    }

    public void exe(int numOfCycles){
        if (numOfCycles != 0) {
            Weeboo.osRunLoop().setAllowedToRun(numOfCycles);
        }
    }

    public void pause(){
        Weeboo.osRunLoop().setAllowedToRun(0);
    }
    
    public void  load(String file) {
        Weeboo.loadSimulationJobFile(file);
    }

    public void mem(){
    	long formatMemUsed = Weeboo.memoryManager().memoryUsed()/(1024 * 1024);
    	long formatMemFree = Weeboo.memoryManager().currentFreeMemory()/(1024 * 1024);
    	long formatMemSwap = Weeboo.memoryManager().pageFileUsed()/(1024 * 1024);
        System.out.println("Memory: Used "+ formatMemUsed + " MB | Free | " + formatMemFree 
        		+ " MB | Swap | " + formatMemSwap + " MB");
    }

    public void reset() {
        Weeboo.reset();
    }

    public void exit(){
        System.exit(0);
    }
}