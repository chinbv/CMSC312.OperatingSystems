/*
public class VirtualMemoryManager {

        private final static long maxMemorySize = 4096;
        private static long currentFreeMemory = maxMemorySize;
        private static long memoryUsed = 0;

        public long getCurrFreeMemory()
        {
            return currentFreeMemory;
        }
        public long getMemoryUsed()
        {
            return memoryUsed;
        }
        public long getmaxMemory()
        {
            return maxMemorySize;
        }
        public boolean allocate(long x)
        {
            currentFreeMemory -= x;
            memoryUsed += x;
            return memoryUsed;
        }

        public int dealloc(long value)
        {
            currentFreeMemory += value;
            memoryUsed -= value;
            if (memoryUsed < 0)
            {
                memoryUsed = 0;
            }
            return memoryUsed;
        }


}
*/

public class VirtualMemoryManager {

	long maxMemorySize = 4096;
	VMPageInfo[] arrayOfPages = new VMPageInfo[(int) maxMemorySize];
	private int pageAddressCount = 0;
	
	long currentFreeMemorySize = maxMemorySize;
	int[] allocatedMemoryArray;
	
	public int[] allocate(long size) {
		
		if(size <= currentFreeMemorySize) {
			currentFreeMemorySize = currentFreeMemorySize - size;
			int[] allocatedMemoryArray = new int[(int) size];
		} else {
			System.out.println("Error: Not enough memory available");
		}
		return allocatedMemoryArray;
	}
	
	public void free(int[] allocation, int size) {
		currentFreeMemorySize = currentFreeMemorySize + size;
	}
	
	public void assigningPage() {
		
	}
	
	
	
}

