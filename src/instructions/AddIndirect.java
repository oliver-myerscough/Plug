package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class AddIndirect extends Instruction {

	private int r1, r2;
	
	public AddIndirect(int r1, int r2){
		super(BitUtils.createInstrValTwo(11, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}
	
	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		
		if(sv.debug) {
			System.out.println("r" + r1 + " += mem[r" + r2 + "]");
		}
		
	//	System.out.println("adding " + r2 + "(" + registers[r2] + ")" + " to " + r1+ " (" + registers[r1] + ")");
		registers[r1] += memory[registers[r2]].getVal();
	}

}
