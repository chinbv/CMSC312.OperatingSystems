import java.util.ArrayList;
import java.util.HashMap;

public class VirtualMemoryManager {

        private final static long maxMemorySize = 4096;
        private static long _currentFreeMemory = maxMemorySize;
        private static long memoryUsed = 0;
        private ArrayList<VMPageInfo> _allocatedPagesList = null;
        private int hashMapKey = 0;
        
        public VirtualMemoryManager() {
        	_allocatedPagesList = new ArrayList<VMPageInfo>();
        	
        }
        
        
        
        public long getCurrFreeMemory()
        {
            return _currentFreeMemory;
        }
        public long getMemoryUsed()
        {
            return memoryUsed;
        }
        public long getmaxMemory()
        {
            return maxMemorySize;
        }
        
        
        public VMPageInfo allocate(Process aProcess, long allocationAmount)
        {
        	long nextFreeMemoryBlock = nextFreeMemoryBlockOfSize(allocationAmount);

        	VMPageInfo newPageAssignment = new VMPageInfo(0x0, nextFreeMemoryBlock, aProcess);
        	
            _currentFreeMemory -= allocationAmount;
            memoryUsed += allocationAmount;
            
        	_allocatedPagesList.add(newPageAssignment);

            return newPageAssignment;
        }
        
        private long nextFreeMemoryBlockOfSize(long allocationAmount) {
        	//TODO this has to be done for real
        	
			return _currentFreeMemory;
		}
        
        private void PageFile() {
        	HashMap pageFile = new HashMap();
        	
        }

        public long dealloc(long value)
        {
            _currentFreeMemory += value;
            memoryUsed -= value;
            if (memoryUsed < 0)
            {
                memoryUsed = 0;
            }
            return memoryUsed;
        }


}

