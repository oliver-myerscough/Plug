package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class SubIndirect extends Instruction {

	private int r1, r2;
	
	public SubIndirect(int r1, int r2) {
		super(BitUtils.createInstrValTwo(12, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}
	
	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		if(sv.debug) {
			System.out.println("r" + r1 + " -= mem[r" + r2 + "]");
		}
		
		registers[r1] -= memory[registers[r2]].getVal();
	}

}
