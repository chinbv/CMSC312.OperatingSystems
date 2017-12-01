
public class VMPageInfo {

	public static long pagedOutSpecialAddress = 0xddeeffdd;	// magic number representing paged out
	public static long notInPageFileSpecialAddress = 0xdefdefde;	// magic number representing paged out

	private long _pageAddress;
	private long _physicalAddress;
	private long _pageFileLocation;

	private ProcessControlBlock _assignedProcess;

	public VMPageInfo(long pageAddress, long physicalAddress, ProcessControlBlock aProcess) {
		
		this._pageAddress = pageAddress;
		this._physicalAddress = physicalAddress;
		this._assignedProcess = aProcess;
		this._pageFileLocation = notInPageFileSpecialAddress;
	}
	
	public void setPhysicalAddress(long anAddress) {
		this._physicalAddress = anAddress;
	}
	
	
	public long physicalAddress() {
		return this._physicalAddress;
	}
	
	
	public boolean isInPhysicalMemory() {
		if( _physicalAddress == pagedOutSpecialAddress) {
			return true;
		}
		
		return false;
	}
	
	public void setPageFileLocation(long anOffset) {
		this._pageFileLocation = anOffset;
	}
	
	public long pageFileLocation() {
		return this._pageFileLocation;
	}
	
}
