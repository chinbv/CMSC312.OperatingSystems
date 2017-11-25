import java.util.Scanner;

public class Terminal {
    private String commandEntered = "";
    private int numOfCycles = 1;
    private String file_program_toExecute = "";

    public Terminal() {

    }

    public String getCommandEntered() {
        return this.commandEntered;
    }

    public int getNumOfCycles() {
        return this.numOfCycles;
    }

    public String getFile_program_toExecute() {
        return this.file_program_toExecute;
    }

    public void initializeTerminal() {
        while (!commandEntered.toUpperCase().equals("EXIT")) {
            this.readCommand();
        }
        System.out.println("while loop exited");
    }

    private void readCommand() {
        System.out.print("Weeboo:~ welcome$ ");
        Scanner readLine = new Scanner(System.in);
        String commandRead = readLine.next();
        numOfCycles = readLine.nextInt();
        // file_program_toExecute = readLine.next();
        this.detectCommand(commandRead);
        System.out.println();
    }

    private void detectCommand(String command) {
        switch (command.toUpperCase()) {
            case "PROC":
                System.out.println("PROC entered");
                commandEntered = "PROC";
                break;
            case "MEM":
                System.out.println("MEM entered");
                commandEntered = "MEM";
                break;
            case "LOAD":
                System.out.println("LOAD entered");
                commandEntered = "LOAD";
                break;
            case "EXE":
                System.out.println("EXE entered");
                commandEntered = "EXE"; //
                break;
            case "RESET":
                System.out.println("RESET entered");
                commandEntered = "RESET"; // all processes are terminated & simulator clock is 0
                break;
            case "EXIT":
                System.out.println("EXIT entered");
                commandEntered = "EXIT";
                break;
            default:
                commandEntered = "INVALID";
        }

    }

}