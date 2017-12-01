import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class InterruptHandler {
	
	HashMap<Integer, ArrayList<IORequest>> _outstandingIORequests = null;
	ArrayList<IORequest> _finishedIORequests = null;
	
	public InterruptHandler() {
		_outstandingIORequests = new HashMap<Integer, ArrayList<IORequest>>();
		_finishedIORequests = new ArrayList<IORequest>();
	}
	
	public void handleInterrupts() {
		
		// in a real system, external interrupts are generated and arrive 
		// synthesize here
		int osCurrentClockTick = Weeboo.osRunLoop().currentClockTick();

		ArrayList<IORequest>completedIORequests = _outstandingIORequests.get(osCurrentClockTick);
		_outstandingIORequests.remove(osCurrentClockTick);
		
		if( completedIORequests != null) {
		
			Iterator<IORequest>requestIterator = completedIORequests.iterator();
			while( requestIterator.hasNext()) {
				IORequest aRequest = requestIterator.next();
			
				_finishedIORequests.add(aRequest);
			}
		}
		
		
		IORequest request = null;
		// choose a core to handle the I/O request, we assume we can only handle one request per tick
		if(_finishedIORequests.isEmpty() == false) {
			request = _finishedIORequests.get(0);
			_finishedIORequests.remove(0);
			
			CPUCore idleCore = null;
			Iterator<CPUCore> coreIterator = Weeboo.allCores().iterator();
			while(idleCore == null && coreIterator.hasNext()) {
				CPUCore aCore = coreIterator.next();
				if(aCore.isIdle() == true) {
					idleCore = aCore;
					System.out.println("an idleCore is found named " + idleCore.getName() + " at id: " + idleCore.getId());
				}
			}
			
			if(idleCore == null) {
				idleCore = Weeboo.allCores().get(0);
				System.out.println("an idleCore is assigned to first core at " + idleCore.getName() + " at id: " + idleCore.getId());
			}
			
			idleCore.interrupt();
			System.out.println("idle core is interrupted at " + idleCore.getName() + " at id: " + idleCore.getId());
		}
		
		return;
		
	}
	
	public void addIO(IORequest newRequest) {
		
		// figure out the time tick when this request will complete
		int osCurrentClockTick = Weeboo.osRunLoop().currentClockTick();
		int completionClockTick = osCurrentClockTick + newRequest.ioCompletionTime();
		
		ArrayList<IORequest>ioList = _outstandingIORequests.get(completionClockTick);
		if( ioList == null ) {
			ioList = new ArrayList<IORequest>();
		}
		
		ioList.add(newRequest);
		
		_outstandingIORequests.put(completionClockTick, ioList);
		
	}
}
