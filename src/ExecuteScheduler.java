import java.util.ArrayList;
import java.util.Map;

public class ExecuteScheduler
{
    int totalRoundWait;

    public void fifo(ArrayList<Process> q)
    {
        Process p;
        for (int i =0; i<q.size();i++)
        {
            p = q.get(i);

            p.setfStartTime(OSClock.getClock());

            while(p.getProcessState() != ProcessState.WAIT)
            {
                ArrayList<ProcessOperation> ops = p.getops
                int numofCyclesCompleted = 0;
                int j = 0;
                while(numofCyclesCompleted <= p.getBurstTime()){
                    ProcessOperation currentOp = ops.get(j);
                    switch (currentOp.getOpType()) {
                        case OperationType.CALCULATE:
                            int runTime = currentOp.getRunTime();
                            if(runTime < p.getBurstTime())
                                numofCyclesCompleted += runTime;
                     else {
                                numofCyclesCompleted += p.getBurstTime();
                        currentOp.setRunTime(runTime - p.getBurstTime());
                    }
                    break;
                    case OperationType.IO:
                        //interrupt handling
                        break;
                }
                j++;
            }
        }
            p.setWaitTime(p.getfStartTime()-p.getArrivalTime());
            p.setTurnAroundTime(OSClock.getClock() - p.getArrivalTime());
        }
    }


    public void sJScheduleExecute(ArrayList<Process> q)
    {
        Process p;
        for (int i =0; i<q.size();i++)
        {
            p = q.get(i);

            p.setSJStartTime(OSClock.getClock());

            while(p.getProcessState() != ProcessState.WAIT)
            {
                ArrayList<ProcessOperation> ops = p.getops
                int numofCyclesCompleted = 0;
                int j = 0;
                while(numofCyclesCompleted <= p.getBurstTime()){
                    ProcessOperation currentOp = ops.get(j);
                    switch (currentOp.getOpType()) {
                        case OperationType.CALCULATE:
                            int runTime = currentOp.getRunTime();
                            if(runTime < p.getBurstTime())
                                numofCyclesCompleted += runTime;
                            else {
                                numofCyclesCompleted += p.getBurstTime();
                                currentOp.setRunTime(runTime - p.getBurstTime());
                            }
                            break;
                        case OperationType.IO:
                            //interrupt handling
                            break;
                    }
                    j++;
                }
            }
            p.setSJWaitTime(p.getSJStartTime()-p.getArrivalTime());
            p.setSJTurnAroundTime(OSClock.getClock() - p.getArrivalTime());
        }
    }

    public void RoundRobinScheduler(Map<Process, Integer> roundScheduled) {
        Map.Entry<Process,Integer> entry = roundScheduled.entrySet().iterator().next();///is entryset right?
        for (int i =0; i < roundScheduled.size(); i++) {
            Process p = entry.getKey();
            p.setRStartTime(OSClock.getClock()); //Setting the start time to the clock

            p.setroundHelp(p.getRStartTime()-p.getLastExecuted());

            while(p.getProcessState() != ProcessState.WAIT) {
                int numOfCycles = entry.getValue();
                ArrayList<ProcessOperation> ops = p.getProcessOperations(); //METHOD NEEDS TO BE CREATED??
                int numOfCyclesCompleted = 0;
                int j = 0;
                while(numOfCyclesCompleted <= numOfCycles) {
                    ProcessOperation currentOp = ops.get(j);
                    switch (currentOp.getOpType()) {
                        case OperationType.CALCULATE:
                            int runTime = currentOp.getRunTime();
                            if(runTime <= numOfCycles) {
                                numOfCyclesCompleted += runTime;
                            } else {
                                numOfCyclesCompleted += numOfCycles;
                                currentOp.setRunTime(runTime - numOfCycles);
                            }
                            break;
                        case OperationType.IO:
                            //interrupt handling
                            break;
                    }
                    j++;
                }
            }

            p.setLastFinish(OSClock.getClock());
        }
        //Plus any other wait times due to interrupt!!
        for (int i = 0; i<roundScheduled.size();i++)
        {
            entry.getKey().setRoundWaitTime(entry.getKey().getArrivalTime()-entry.getKey().getroundHelp());
            entry.getKey().setRoundTurnAroundTime(entry.getKey().getLastExecuted()-entry.getKey().getArrivalTime());
        }
        //ALL YOU HAVE TO DO IS TAKE AVEREAGE NOW

    }


}