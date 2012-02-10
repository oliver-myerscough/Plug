package util;

import instructions.AddDirect;
import instructions.AddIndirect;
import instructions.Goto;
import instructions.Ifneg;
import instructions.Ifzer;
import instructions.Instruction;
import instructions.LoadDirect;
import instructions.LoadIndirect;
import instructions.Stop;
import instructions.StoreDirect;
import instructions.StoreIndirect;
import instructions.SubDirect;
import instructions.SubIndirect;
import virtual.Data;

public class BitUtils {

	public static void main(String args[]){
		
		testHelper(BitUtils.createInstrValOne(0, 0, 0));
		testHelper(BitUtils.createInstrValOne(1, 0, 0));
		
	}
	
	public static void testHelper(int i) {
		System.out.println(Integer.toBinaryString(i));
	}
	
	public static int createInstrValOne(int opcode, int r, int addr) {
		return (opcode << 12) | (r << 10) | addr;
	}
	
	public static int createInstrValTwo(int opcode, int r1, int r2) {
		return (opcode << 12) | (r1 << 10) | (r2 << 8);
	}

	public static Instruction interpretInstruction(Data data) {
		
		int val = data.getVal();
		int opcode = val >> 12;
		int r1 = (val >> 10) & 3;
		int r2 = (val >> 8) & 3;
		int addr = val & 2^11 - 1; 
		
		switch(opcode) {
			case (0):
				return new Stop();
			case (1):
				return new LoadDirect(r1, addr);
			case (2):
				return new StoreDirect(r1, addr);
			case (3):
				return new AddDirect(r1, addr);
			case (4):
				return new SubDirect(r1, addr);
			case (5):
				return new Goto(addr);
			case (6):
				return new Ifzer(r1, addr);
			case (7):
				return new Ifneg(r1, addr);
			case (9):
				return new LoadIndirect(r1, r2);
			case (10):
				return new StoreIndirect(r1, r2);
			case (11):
				return new AddIndirect(r1, r2);
			case (12):
				return new SubIndirect(r1, r2);
			default:
				return null;
		}
		
	}

}
