package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class LoadR extends Instruction {

	private int r1, r2;
	
	public LoadR(int r1, int r2){
		super(BitUtils.createInstrValTwo(9, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}


	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
//		System.out.println("loading " + memIndex + " into " + regIndex);
		registers[r1] = memory[registers[r2]].getVal();
	}

}
