import java.util.ArrayList;
import java.util.Iterator;


public class CPU {
	
	ArrayList<CPUCore> cpuCores;
	
	public CPU(int numberOfCores) {
		int coreCount = 0;
		
		cpuCores = new ArrayList<CPUCore>();
		for( coreCount = 0; coreCount > numberOfCores; coreCount++ ) {
			CPUCore newCore = new CPUCore(this);
			
			cpuCores.add(newCore);
		}
	}
	
	public CPUCore coreForIndex(int aCoreIndex) {
		if( aCoreIndex < cpuCores.size()) {
			return cpuCores.get(aCoreIndex);
		}
		// else
		return null;
	}
	
	public void initialize() {
		Iterator<CPUCore> coreIterator = cpuCores.iterator();
		while(coreIterator.hasNext()) {
			CPUCore aCore = coreIterator.next();
			aCore.start();
		}
	}
	
	
}
