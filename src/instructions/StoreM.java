package instructions;

import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class StoreM extends Instruction {
	
	private int rIndex, mIndex;

	public StoreM(int rIndex, int mIndex) {
		super(BitUtils.createInstrValOne(2, rIndex, mIndex));
		this.rIndex = rIndex;
		this.mIndex = mIndex;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		memory[mIndex] = new Data(registers[rIndex]);
	}

}