import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class OSRunLoop extends Thread {

	static int osClockTick = 0;
	boolean hasPendingUIAction = false;
	boolean complete = false;
	int _isAllowedToRun = 0;

	private HashMap<Integer, ArrayList<String>> _simulationJobs = null;
	
	private static Object _runLoopLock = null;


	public HashMap<Integer, ArrayList<String>>simulationJobs() {
		synchronized (_runLoopLock) {

			if( _simulationJobs == null ) {
				_simulationJobs = new HashMap<Integer, ArrayList<String>>();
			}
		}
		
		return _simulationJobs;
	}

	public void run() {

		// initialize the runLoopLock
		runLoopLock();


		while(!complete) {
			
		
			checkforUIAction();
			
			try {
				checkForSimulationAction();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			// schedule processes
			Weeboo.scheduler().schedule();
			
			//execute a process (calculate)
			// tell each core to execute a tick
			synchronized (ProcessManager.schedulerLock()) {
				ProcessManager.schedulerLock().notifyAll();
			}
			
			Weeboo.processManager().dumpProcessArrayContents();
			
			//exit
			System.out.println("Time: " + osClockTick);
			osClockTick++;
			
			if( _isAllowedToRun > 0) {
				_isAllowedToRun--;
			}
		}
	}
	
	private void checkforUIAction() {
		synchronized (runLoopLock()) {

			if( hasPendingUIAction == true ) {
			// then do that
			}
			
		}
		
		while( _isAllowedToRun == 0 ) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setAllowedToRun(int count) {
		_isAllowedToRun = count;
	}
	
	public  int isAllowToRun() {
		return _isAllowedToRun;
	}

	private void checkForSimulationAction() throws IOException {

		synchronized (runLoopLock()) {

			// should consult collection of jobs for any actions to be performed
			System.out.println("checking for jobs for tick " + osClockTick);
			ArrayList<String>jobsForTick = simulationJobs().get(osClockTick);
			
			if( jobsForTick != null ) {
				Iterator<String> jobIterator = jobsForTick.iterator();
				
				while( jobIterator.hasNext()) {
					String jobDescription = jobIterator.next();
					
					// jobs are likely to spawn processes
					ProcessControlBlock aPCB = Weeboo.processManager().createProcessControlBlock(jobDescription);
				}
			}
		}
		
	}
	
	public static Object runLoopLock() {
		if( _runLoopLock == null ) {
			_runLoopLock = new Object();
		}
		return _runLoopLock;
	}

}
