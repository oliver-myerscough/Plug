package instructions;
import virtual.Data;
import virtual.StateVar;


public abstract class Instruction extends Data {

	public Instruction(int val) {
		super(val);
		// TODO Auto-generated constructor stub
	}

	public abstract void execute(Data[] memory, int[] registers, StateVar sv);
	
}
