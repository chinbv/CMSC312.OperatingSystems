package Main;

import Processes.Process;
import Processes.ProcessManager;

import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    private String commandEntered = "";
    private int numOfCycles;
    private String file_program_toExecute = "";
    ProcessManager processManager;
    CPU cpu;


    public Terminal() {
        processManager = new ProcessManager();
        cpu = new CPU();
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
                numOfCycles = Integer.parseInt(elementsEntered.get(0));
                System.out.println(numOfCycles);
                file_program_toExecute = elementsEntered.get(1);
                System.out.println(file_program_toExecute);
                processManager.createProcess(file_program_toExecute, numOfCycles);
                break;
            case "EXE":
                System.out.println("EXE entered");
                numOfCycles = Integer.parseInt(elementsEntered.get(0));
                cpu.run();
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
            System.out.print("Process Name: " + p.getProcessName() + "\t");
            System.out.print("State: " + p.getProcessState().toString() + "\t");
            System.out.print("Runtime: " + p.getBurstTime() + "\t");
            System.out.print("Remaining Burst Time: " + p.getRemainingBurstTime() + "\t");
            if (p.getPriority() != 0) {
                System.out.print("Priority: " + p.getPriority() + "\t");
            }
            System.out.print("Number of I/O bursts: " + p.getRemainingBurstTime() + "\t");
        }
    }
}
