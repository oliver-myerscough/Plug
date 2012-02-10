package instructions;


import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class SubDirect extends Instruction {

	int rIndex, mIndex;

	public SubDirect(int rIndex, int mIndex) {
		super(BitUtils.createInstrValOne(4, rIndex, mIndex));
		this.rIndex = rIndex;
		this.mIndex = mIndex;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		if(sv.debug) {
			System.out.println("r" + rIndex + " -= mem[" + mIndex + "]");
		}
		
		registers[rIndex] -= memory[mIndex].getVal();
	}
	
}
