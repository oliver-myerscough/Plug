package instructions;

import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class AddDirect extends Instruction {
	
	int rIndex, mIndex;

	public AddDirect(int rIndex, int mIndex) {
		super(BitUtils.createInstrValOne(3, rIndex, mIndex));
		this.rIndex = rIndex;
		this.mIndex = mIndex;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		registers[rIndex] += memory[mIndex].getVal();
	}

}
