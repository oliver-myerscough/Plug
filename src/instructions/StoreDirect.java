package instructions;

import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class StoreDirect extends Instruction {
	
	private int rIndex, mIndex;

	public StoreDirect(int rIndex, int mIndex) {
		super(BitUtils.createInstrValOne(2, rIndex, mIndex));
		this.rIndex = rIndex;
		this.mIndex = mIndex;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		if(sv.debug) {
			System.out.println("store r" + rIndex + " in mem " + mIndex);
		}
		
		memory[mIndex] = new Data(registers[rIndex]);
	}

}