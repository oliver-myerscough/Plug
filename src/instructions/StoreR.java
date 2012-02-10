package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class StoreR extends Instruction {

	private int r1, r2;

	public StoreR(int r1, int r2){
		super(BitUtils.createInstrValTwo(10, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		memory[r2] = new Data(registers[r1]);
	}

}
