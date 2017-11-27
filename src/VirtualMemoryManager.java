public class VMM {

        private final static int maxMemorySize = 256; //kb
        private static int currentFreeMemory = maxMemorySize;
        private static int memoryUsed = 0;

        public int getCurrFreeMemory()
        {
            return currentFreeMemory;
        }
        public int getMemoryUsed()
        {
            return memoryUsed;
        }
        public int getmaxMemory()
        {
            return maxMemorySize;
        }
        public int increaseMemoryUsed(int x)
        {
            currentFreeMemory -= x;
            memoryUsed += x;
            return memoryUsed;
        }

        public int decreaseMemoryUsed(int x)
        {
            currentFreeMemory + value;
            memoryUsed -= value;
            if (memoryUsed < 0)
            {
                memoryUsed = 0;
            }
            return memoryUsed;
        }


}

/**

public class VirtualMemoryManager {

	long maxMemorySize = 4096;
	
	
	long currentFreeMemorySize = maxMemorySize;
	int[] allocatedMemoryArray;
	
	public int[] allocation(long size) {
		
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
	
}
**/
