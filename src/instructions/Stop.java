package instructions;
import virtual.Data;
import virtual.StateVar;


public class Stop extends Instruction {
	
	public Stop() {
		super(0);
	}
	
	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		// nothing
	}

}
