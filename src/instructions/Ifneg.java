package instructions;

import util.BitUtils;
import virtual.Data;
import virtual.StateVar;

public class Ifneg extends Instruction {
	
	private int reg, addr;

	public Ifneg(int reg, int addr) {
		super(BitUtils.createInstrValOne(7, reg, addr));
		this.reg = reg;
		this.addr = addr;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
		if(registers[reg] < 0) {
			sv.pc = addr;
		}
	}

}
