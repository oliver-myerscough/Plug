package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class LoadIndirect extends Instruction {

	private int r1, r2;
	
	public LoadIndirect(int r1, int r2){
		super(BitUtils.createInstrValTwo(9, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}


	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		if(sv.debug) {
			System.out.println("load r" + r1 + " from mem[r" + r2 + "]");
		}
		
//		System.out.println("loading " + memIndex + " into " + regIndex);
		registers[r1] = memory[registers[r2]].getVal();
	}

}
