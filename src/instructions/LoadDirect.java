package instructions;

import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class LoadDirect extends Instruction {
	
	private int rIndex, mIndex;

	public LoadDirect(int reg, int mem) {
		super(BitUtils.createInstrValOne(1, reg, mem));
		rIndex = reg;
		mIndex = mem;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		if(sv.debug) {
			System.out.println("load r" + rIndex + " from mem " + mIndex);
		}
		
		registers[rIndex] = memory[mIndex].getVal();
	}

}
