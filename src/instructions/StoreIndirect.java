package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class StoreIndirect extends Instruction {

	private int r1, r2;

	public StoreIndirect(int r1, int r2){
		super(BitUtils.createInstrValTwo(10, r1, r2));
		this.r1 = r1;
		this.r2 = r2;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		
		if(sv.debug) {
			System.out.println("store r" + r1 + " in mem[r" + r2 + "]");
		}
		
		memory[registers[r2]] = new Data(registers[r1]);
	}

}
