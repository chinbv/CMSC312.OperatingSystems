import java.util.ArrayList;
import java.util.HashMap;

public class VirtualMemoryManager {

        private final static long maxMemorySize = 4096;
        private static long _currentFreeMemory = maxMemorySize;
        private static long memoryUsed = 0;
        private ArrayList<VMPageInfo> _allocatedPagesList = null;
        private int hashMapKey = 0;
    	HashMap pageFileMap = new HashMap();

        
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
        
        
        public VMPageInfo allocate(ProcessControlBlock aProcess, long allocationAmount)
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
        
//        
//        private void swapping() {
//        	if(_currentFreeMemory<=0) {
//        		swapIntoPageFile();
//        	}
//        }
//        
//        private void swapIntoPageFile() {
//        	VMPageInfo pageSwappedIn;
////        	pageFileMap.put(hashMapKey, pageSwappedIn);//process is put into the page file map
//        	_currentFreeMemory--;
//        	memoryUsed++;
//        	
//        }
//        
//        private VMPageInfo swapOutOfPageFile() {
//        	VMPageInfo pageSwappedOut = (VMPageInfo) pageFileMap.get(hashMapKey);
//        	_currentFreeMemory++;
//        	memoryUsed--;
//        	return pageSwappedOut;
//        }
        
        public void pageOut(VMPageInfo Page) {
        	
        	pageFileMap.put(hashMapKey, Page);
        	hashMapKey++;
        	
        	
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
