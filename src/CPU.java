import java.util.ArrayList;
import java.util.Iterator;


public class CPU {
	private String _cpuID;
	private ArrayList<CPUCore> _cpuCores;
	
	public CPU(String aCPUID, int numberOfCores) {
		int coreCount = 0;
		
		_cpuID = aCPUID;
		_cpuCores = new ArrayList<CPUCore>();
		for( coreCount = 0; coreCount > numberOfCores; coreCount++ ) {
			CPUCore newCore = new CPUCore(this, coreCount);
			
			_cpuCores.add(newCore);
		}
	}
	
	public CPUCore coreForIndex(int aCoreIndex) {
		if( aCoreIndex < _cpuCores.size()) {
			return _cpuCores.get(aCoreIndex);
		}
		// else
		return null;
	}
	
	public void initialize() {
		Iterator<CPUCore> coreIterator = _cpuCores.iterator();
		while(coreIterator.hasNext()) {
			CPUCore aCore = coreIterator.next();
			aCore.start();
		}
	}
	
	public String cpuID() {
		return _cpuID;
	}
	
}
