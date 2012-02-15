package instructions;

import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class Ifzer extends Jump {

	private int reg;
	
	public Ifzer(int reg, int addr) {
		super(BitUtils.createInstrValOne(6, reg, addr), addr);
		this.reg = reg;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		if(registers[reg] == 0) {
			if(sv.debug) {
				System.out.println("r" + reg + " = 0, jump to " + getJumpLoc());
			}
			sv.pc = getJumpLoc();
		} else if(sv.debug) {
			System.out.println("r" + reg + " != 0, no jump");
		}
	}

}
