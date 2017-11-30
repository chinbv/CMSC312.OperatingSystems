import java.util.Random;
import java.util.Scanner;

public class ProcessOperation {

    private OperationType opType;
    private int runTime;
    private Process parentProcess;

    public ProcessOperation(String line, Process p) {
        this.parentProcess = p;

        Scanner readLine = new Scanner(line);
        String command = readLine.next();
        Random randomNum = new Random();

        switch (command) {
            case "CALCULATE":
                opType = OperationType.CALCULATE;
                runTime = readLine.nextInt();
                break;
            case "I/O":
                opType = OperationType.IO;
                runTime = randomNum.nextInt(50) + 1;
                break;
            case "YIELD":
                opType = OperationType.YIELD;
                break;
            case "OUT":
                opType = OperationType.OUT;
                System.out.println("Process info & PCB info");
                break;
            default:
                opType = null;
        }
    }

    public OperationType getOpType() {
        return this.opType;
    }

    public int getRunTime() {
        return this.runTime;
    }
}