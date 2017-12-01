import java.util.ArrayList;
import java.util.HashMap;

public class VirtualMemoryManager {

        public final static long _totalPhysicalMemorySize = 4096 * 1024 * 1024;
        public final static long _vmPageSize = 4096;
        private static long _currentFreeMemory = _totalPhysicalMemorySize;
        private static long memoryUsed = 0;
        private ArrayList<VMPageInfo> _allocatedPagesList = null;
        private int nextPageFileFreeIndex = 0;
    	HashMap<Integer,VMPageInfo> pageFileMap = new HashMap<Integer,VMPageInfo>();

        
        public VirtualMemoryManager() {
        	_allocatedPagesList = new ArrayList<VMPageInfo>();
        	
        }
        
        
        public long currentFreeMemory()
        {
            return _currentFreeMemory;
        }
        public long memoryUsed()
        {
            return memoryUsed;
        }
        public long physicalMemorySize()
        {
            return _totalPhysicalMemorySize;
        }
        
        
        public ArrayList<VMPageInfo> malloc(ProcessControlBlock aProcess, long allocationAmount)
        {
        	
        	// figure out the number of pages for the allocation amount
        	int numberOfPages = (int)java.lang.Math.ceil(allocationAmount / _vmPageSize);
        	ArrayList<VMPageInfo>allocationList = new ArrayList<VMPageInfo>();
        
        	while( numberOfPages > 0 ) {
            	VMPageInfo newPageAssignment = new VMPageInfo(0x0, VMPageInfo.pagedOutSpecialAddress, aProcess);

        		allocatePhysicalMemoryForPage(newPageAssignment);
        		allocationList.add(newPageAssignment);
        	}

            return allocationList;
        }
        
        private void allocatePhysicalMemoryForPage(VMPageInfo aPage) {
        	
        	long nextFreeMemoryBlock = nextFreeMemoryBlockOfSize(_vmPageSize);
        	aPage.setPhysicalAddress(nextFreeMemoryBlock);
        	
            _currentFreeMemory -= _vmPageSize;
            memoryUsed += _vmPageSize;
            
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
        
        public void pageOut(VMPageInfo aPage) {
        	
        	// takes a page and puts it into the page file
        	// a real implementation would copy the contents of the page into the pagefile
        	// we simulate it here using a hashmap
        	
        	// save the physical address
        	long physicalAddress = aPage.physicalAddress();
        	
        	pageFileMap.put(nextPageFileFreeIndex, aPage);
        	aPage.setPageFileLocation(nextPageFileFreeIndex);
        	nextPageFileFreeIndex++;

        	
        	// take the page out of physical memory
        	// assign it to a value that cannot exist on a real system
        	aPage.setPhysicalAddress(VMPageInfo.pagedOutSpecialAddress);
        	        	
        	freePageAtPhysicalAddress(physicalAddress);

        	nextPageFileFreeIndex++;
        	
        }
        
		public void pageIn(VMPageInfo aPage) {
		    if( aPage.isInPhysicalMemory() == true ) {
		    	return;
		    }
		    
		    long pageFileLocation = aPage.pageFileLocation();
		    if( pageFileLocation == VMPageInfo.notInPageFileSpecialAddress) {
		    	Weeboo.kernelPanic("System Error: trying to page in a page without a valid page file location");
		    	return;
		    }
		    
		    // retrieve the page from page file
		    
		    // allocate new physical address
        	allocatePhysicalMemoryForPage(aPage);

		    // in the simulation, this is just getting the same pageinfo object back
        	// in reality, this would copy the page from page file into physical memory
		    aPage = pageFileMap.get(aPage.pageFileLocation());
		    pageFileMap.remove(aPage.pageFileLocation());
		    
        	aPage.setPageFileLocation(VMPageInfo.notInPageFileSpecialAddress);
		    
		}

        public long freePageAtPhysicalAddress(long value)
        {
        	//TODO probably completely wrong
            _currentFreeMemory += value;
            memoryUsed -= value;
            if (memoryUsed < 0)
            {
                memoryUsed = 0;
            }
            return memoryUsed;
        }


}
