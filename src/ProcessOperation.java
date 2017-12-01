import java.util.Random;
import java.util.Scanner;


public class ProcessOperation {

    private ProcessControlBlock.scriptCommands opType;
    private int runTime;

    public ProcessOperation(String line) {
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
                opType = ProcessControlBlock.scriptCommands.CALCULATE;
                runTime = CalcRunTime;
                break;
            case "IO":
                opType = ProcessControlBlock.scriptCommands.IO;
                runTime = randomNum.nextInt(50) + 1;
                break;
            case "YIELD":
                opType = ProcessControlBlock.scriptCommands.YIELD;
                break;
            case "OUT":
                opType = ProcessControlBlock.scriptCommands.OUT;
                System.out.println("Process info & PCB info");
                break;
            default:
                opType = null;
        }

        System.out.println(getOpType());
    }

    public ProcessControlBlock.scriptCommands getOpType() {
        return this.opType;
    }

    public int getRunTime() {
        return this.runTime;
    }

    public void setRunTime(int rt) {
        this.runTime = rt;
    }
}