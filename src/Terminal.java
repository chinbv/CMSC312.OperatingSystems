import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    private String commandEntered = "";
    private int numOfCycles;
    private String file_program_toExecute = "";
    ProcessManager processManager;



    public Terminal() {
        processManager = new ProcessManager();

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
                if (elementsEntered.size() == 2) {
                    numOfCycles = Integer.parseInt(elementsEntered.get(0));
                    System.out.println(numOfCycles);
                    file_program_toExecute = elementsEntered.get(1);
                    System.out.println(file_program_toExecute);
                } else if(elementsEntered.size() == 1) {
                    file_program_toExecute = elementsEntered.get(0);
                    System.out.println(file_program_toExecute);
                }
                load();
                break;
            case "EXE":
                System.out.println("EXE entered");
                if (elementsEntered.size() == 1) {
                    numOfCycles = Integer.parseInt(elementsEntered.get(0));
                } else {
                    numOfCycles = 0;
                }
                exe(numOfCycles);
                commandEntered = "EXE";
                break;
            case "RESET":
                System.out.println("RESET entered");
                processManager.resetAllProcesses();
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
        ArrayList<Process> queue = processManager.listOfProcesses;

        if(queue.isEmpty()) {
            System.out.println("No processes currently in the system.");
        }

        processManager.PCBInfo();

    }

    public void  load() {

        try {
            Scanner readJobFile = new Scanner(new File(file_program_toExecute));
            while(readJobFile.hasNextLine()) {
                ArrayList<String> elementsEntered = new ArrayList<>();
                String command = readJobFile.next();
                System.out.println(command);
                if(command.toUpperCase().equals("LOAD")) {
                    int cycleNum = readJobFile.nextInt();
                    String processFile = readJobFile.next();
                    System.out.println("read job file " + processFile + " " + cycleNum);
                    processManager.createProcess(processFile,cycleNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exe(int numOfCycles){
        boolean nocycleNum;
        if (numOfCycles == 1) {
            nocycleNum = false;
        } else {
            nocycleNum = true;
        }
    }

    public void mem(){
        System.out.println("Memory: "+"amount of mem used" + "/" + "total mem available");
    }

    public void reset() {
        OSClock.resetClock();
        // reset scheduler
        // reset cpu
        // reset queues
        // all processes are terminated & simulator clock is 0
    }

    public void exit(){
        System.exit(0);
    }
    public ProcessManager getProcessManager(){
        return this.processManager;
    }
}
