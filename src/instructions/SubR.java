package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class SubR extends Instruction {

	private int r1, r2;
	
	public SubR(int r1, int r2) {
		super(BitUtils.createInstrValTwo(4, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}
	
	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		registers[r1] -= registers[r2];
	}

}
