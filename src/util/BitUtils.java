package util;

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

}
