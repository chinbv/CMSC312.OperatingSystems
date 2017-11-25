
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
