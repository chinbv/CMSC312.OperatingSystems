import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class VirtualMemoryManager {

        private long _totalPhysicalMemorySize = 0;
        private long _vmPageSize = 4096;
        private long _currentFreeMemory = 0;
        private long memoryUsed = 0;
        private ArrayList<VMPageInfo> _allocatedPagesList = null;
        private int nextPageFileFreeIndex = 0;
    	HashMap<Integer,VMPageInfo> pageFileMap = new HashMap<Integer,VMPageInfo>();

        
        public VirtualMemoryManager() {
        	_allocatedPagesList = new ArrayList<VMPageInfo>();
        	
        	_totalPhysicalMemorySize = 4096;
        	_totalPhysicalMemorySize *= 1024;
        	_totalPhysicalMemorySize *= 1024;
        	_currentFreeMemory = _totalPhysicalMemorySize;
        	
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
        public long pageFileUsed()
        {
        	
        	long pageFileUsed = pageFileMap.size() * _vmPageSize;
        	
            return pageFileUsed;
        }
        
        
        public ArrayList<VMPageInfo> malloc(ProcessControlBlock aProcess, long allocationAmount)
        {
        	
        	// figure out the number of pages for the allocation amount
        	int numberOfPages = (int)java.lang.Math.ceil((double)allocationAmount / (double)_vmPageSize);
        	ArrayList<VMPageInfo>allocationList = new ArrayList<VMPageInfo>();
        
        	while( numberOfPages > 0 ) {
            	VMPageInfo newPageAssignment = new VMPageInfo(0x0, VMPageInfo.pagedOutSpecialAddress, aProcess);

        		if( allocatePhysicalMemoryForPage(newPageAssignment) == false ) {
        			// allocation failed
        			numberOfPages = 0;
        		} else {
        		
        			allocationList.add(newPageAssignment);
        		
        			numberOfPages--;
        		}
        	}

            return allocationList;
        }
        
        private boolean allocatePhysicalMemoryForPage(VMPageInfo aPage) {
        	
        	// should be a real address in a real system
        	long nextFreeMemoryBlock = nextFreeMemoryBlockOfSize(_vmPageSize);
        	
        	if( nextFreeMemoryBlock == 0) {
        		return false;
        	}
        	aPage.setPhysicalAddress(nextFreeMemoryBlock);
        	
            _currentFreeMemory -= _vmPageSize;
            memoryUsed += _vmPageSize;
            
			return true;
            
            
        }
        
        private long nextFreeMemoryBlockOfSize(long allocationAmount) {
        	//TODO this has to be done for real
        	
        	if( allocationAmount > _totalPhysicalMemorySize) {
        		Weeboo.kernelPanic("cannot allocate more memory than total system memory");
        		return 0;
        	}
        	
        	if( allocationAmount < _currentFreeMemory) {
        		return _currentFreeMemory;
        	}
        	
        	
        	// first try wait queue processes
    		ArrayList<ProcessControlBlock>waitQueue = Weeboo.processManager().waitingQueue();
        	int waitQueueIndex = waitQueue.size() - 1;
        	
        	while( waitQueueIndex >= 0 && allocationAmount > _currentFreeMemory) {
        		// need to free up physical memory by finding
        		
        		ProcessControlBlock aPCB = waitQueue.get(waitQueueIndex);
        		if( aPCB != null ) {
        			if( aPCB.isResidentInMemory() == true) {
        				swapOutProcess(aPCB);
        			}
        		}
        		
        		waitQueueIndex--;
        	}
        	
        	
        	// then try ready queue processes
    		ArrayList<ProcessControlBlock>readyQueue = Weeboo.processManager().readyQueue();
        	int readyQueueIndex = readyQueue.size() - 1;
        	while( readyQueueIndex >= 0 && allocationAmount > _currentFreeMemory) {
        		
        		ProcessControlBlock aPCB = readyQueue.get(readyQueueIndex);
        		if( aPCB != null ) {
        			if( aPCB.isResidentInMemory() == true) {
        				swapOutProcess(aPCB);
        			}
        		}
        		
        		readyQueueIndex--;

        	}
        	
        	if( allocationAmount > _currentFreeMemory ) {
            	// otherwise fail

        		return 0;
        	}
        	// figure out if there is enough free physical memory
        	
        	// if not, then choose a process to swap out
        	
        	//ProcessControlBlock processToSwap;
        	
        	//swapOutProcess(processToSwap);
        	
        	
			return _currentFreeMemory;
		}
        
        
    	public void swapOutProcess(ProcessControlBlock aProcess) {
    		// since we cannot simulate normal paging, we will swap in and out entire processes
    		
    		if( aProcess == null) {
    			return;
    		}
    		
    		System.out.println("Swapping out process " + aProcess.getProcessName());

    		/* grab all the pages for a process */
    		ArrayList<VMPageInfo>processPages = aProcess.allMemoryPages();
    		if( processPages != null && processPages.isEmpty() != true ) {
    			Iterator<VMPageInfo>pageIterator = processPages.iterator();
    			
    			while( pageIterator.hasNext()) {
    				pageOut(pageIterator.next());
    			}
    		}
    		
    	}
    	
    	
    	public boolean isProcessInPhysicalMemory(ProcessControlBlock aProcess) {
    		// since we cannot simulate normal paging, we will swap in and out entire processes
    		boolean isResident = true;
    		
    		if( aProcess == null) {
    			return false;
    		}
    		/* grab all the pages for a process */
    		ArrayList<VMPageInfo>processPages = aProcess.allMemoryPages();
    		if( processPages != null && processPages.isEmpty() != true ) {
    			Iterator<VMPageInfo>pageIterator = processPages.iterator();
    			
    			while( pageIterator.hasNext()) {
    				VMPageInfo aPage = pageIterator.next();
    				
    				
    				if( aPage.isInPhysicalMemory() == false) {
    					isResident = false;
    				}
    			}
    		}
    		return isResident;
    	}
    	
    	public void swapInProcess(ProcessControlBlock aProcess) {
    		
    		System.out.println("Swapping in process " + aProcess.getProcessName());
    		// since we cannot simulate normal paging, we will swap in and out entire processes
    		
    		if( aProcess == null) {
    			return;
    		}
    		/* grab all the pages for a process */
    		ArrayList<VMPageInfo>processPages = aProcess.allMemoryPages();
    		if( processPages != null && processPages.isEmpty() != true ) {
    			Iterator<VMPageInfo>pageIterator = processPages.iterator();
    			
    			while( pageIterator.hasNext()) {
    				VMPageInfo aPage = pageIterator.next();
    				
    				
    				pageIn(pageIterator.next());
    			}
    		}
    	}
    	

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
		    pageFileMap.get(aPage.pageFileLocation());
		    pageFileMap.remove(aPage.pageFileLocation());
		    
        	aPage.setPageFileLocation(VMPageInfo.notInPageFileSpecialAddress);
		    
		}

        public long freePageAtPhysicalAddress(long pageAddress)
        {
        	//TODO probably completely wrong
            _currentFreeMemory += _vmPageSize;
            memoryUsed -= _vmPageSize;
            if (memoryUsed < 0)
            {
                memoryUsed = 0;
            }
            return memoryUsed;
        }


}
