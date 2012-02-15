package instructions;

import virtual.Data;
import virtual.StateVar;

public abstract class Jump extends Instruction {
	
	private int jumpTo;

	public Jump(int val, int jumpTo) {
		super(val);
		this.jumpTo = jumpTo;
	}
	
	public int getJumpLoc() {
		return jumpTo;
	}

	public abstract void execute(Data[] memory, int[] registers, StateVar sv);
}