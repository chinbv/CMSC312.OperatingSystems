
public class VMPageInfo {

	
	private long _pageAddress;
	private long _physicalAddress;

	private Process _assignedProcess;
	private String _Free;

	public VMPageInfo(long pageAddress, long physicalAddress, Process aProcess) {
		
		this._pageAddress = pageAddress;
		this._physicalAddress = physicalAddress;
		this._assignedProcess = aProcess;
	}
	
}
