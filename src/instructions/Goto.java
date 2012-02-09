package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class Goto extends Instruction {

	private int jumpTo;
	
	public Goto(int j){
		super(BitUtils.createInstrValOne(5, 0, j));
		jumpTo = j;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		sv.pc = jumpTo;
	}

}
