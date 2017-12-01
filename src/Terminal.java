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

    private void readCommand() {
        System.out.print("Operating-System:~ welcome$ ");
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
                System.out.println("PROC entered");
                proc();
                commandEntered = "PROC";
                break;
            case "MEM":
                System.out.println("MEM entered");
                commandEntered = "MEM";
                mem();
                break;
            case "LOAD":
                System.out.println("LOAD entered");
                commandEntered = "LOAD";
                String fileName = "";
                int cycleNum = 0;
                if (elementsEntered.size() == 2) {
                    cycleNum = Integer.parseInt(elementsEntered.get(0));
                    fileName = elementsEntered.get(1);
                } else if(elementsEntered.size() == 1) {
                    fileName = elementsEntered.get(0);
                }
                load(fileName, cycleNum);
                break;
            case "EXE":
                System.out.println("EXE entered");
                int numOfCycles = 0;
                if (elementsEntered.size() == 1) {
                    numOfCycles = Integer.parseInt(elementsEntered.get(0));
                }
                exe(numOfCycles);
                commandEntered = "EXE";
                break;
            case "RESET":
                System.out.println("RESET entered");
                commandEntered = "RESET";
                reset();
                break;
            case "EXIT":
                System.out.println("EXIT entered");
                commandEntered = "EXIT";
                exit();
                break;
            default:
                commandEntered = "INVALID";
        }
        System.out.println();
    }

    public void proc() {
        Weeboo.processManager().dumpProcessArrayContents();

    }

    public void exe(int numOfCycles){
        if (numOfCycles != 0) {
            Weeboo.runSimulatenously = false;
            Weeboo.exeForNumOfCycles = numOfCycles;
        } else {
            Weeboo.runSimulatenously = true;
        }
    }

    public void  load(String file, int cycle) {

        try {
            Scanner readJobFile = new Scanner(new File(file));
            while(readJobFile.hasNextLine()) {
                ArrayList<String> elementsEntered = new ArrayList<>();
                String command = readJobFile.next();
                System.out.println(command);
                if(command.toUpperCase().equals("LOAD")) {
                    int cycleNum = readJobFile.nextInt();
                    String processFile = readJobFile.next();
                    System.out.println("read job file " + processFile + " " + cycleNum);
                    //Weeboo.loadSimulationJobFile(processFile, cycleNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void mem(){
        System.out.println("Memory: "+ Weeboo.memoryManager().memoryUsed() + "/" + Weeboo.memoryManager().physicalMemorySize());
    }

    public void reset() {
        // reset clock
        // reset scheduler
        // reset cpu
        // reset queues
        // all processes are terminated & simulator clock is 0
    }

    public void exit(){
        System.exit(0);
    }
}