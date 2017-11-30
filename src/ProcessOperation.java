import java.util.Random;
import java.util.Scanner;

public class ProcessOperation {

    private OperationType opType;
    private int runTime;
    private Process parentProcess;

    public ProcessOperation(String line, Process p) {
        this.parentProcess = p;
        // System.out.println("command form file" + line);
        Scanner readLine = new Scanner(line);
        String command = readLine.next().toUpperCase();
        Random randomNum = new Random();
        int CalcRunTime = 0;
        if(readLine.hasNext()){
             CalcRunTime = readLine.nextInt();
        }

        switch (command) {
            case "CALCULATE":
                opType = OperationType.CALCULATE;
                runTime = CalcRunTime;
                break;
            case "IO":
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

        System.out.println(getOpType());
    }

    public OperationType getOpType() {
        return this.opType;
    }

    public int getRunTime() {
        return this.runTime;
    }
}