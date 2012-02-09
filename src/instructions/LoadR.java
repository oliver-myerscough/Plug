package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class LoadR extends Instruction {

	private int memIndex;
	private int regIndex;
	
	public LoadR(int ri, int mi){
		super(BitUtils.createInstrValOne(1, ri, mi));
		memIndex = mi;
		regIndex = ri;
	}


	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
//		System.out.println("loading " + memIndex + " into " + regIndex);
		registers[regIndex] = memory[memIndex].getVal();
	}

}
