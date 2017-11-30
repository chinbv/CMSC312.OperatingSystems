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
                processManager.createProcess(file_program_toExecute, numOfCycles);
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
                commandEntered = "RESET"; // all processes are terminated & simulator clock is 0
                break;
            case "EXIT":
                System.out.println("EXIT entered");
                commandEntered = "EXIT";
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

        for (Process p : queue) {
            processManager.PCBInfo();
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
}
