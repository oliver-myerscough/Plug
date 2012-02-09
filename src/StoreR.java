
public class StoreR extends Instruction {

	private int memIndex;
	private int regIndex;
	
	public StoreR(int ri, int mi){
		super(BitUtils.createInstrValOne(2, ri, mi));
		memIndex = mi;
		regIndex = ri;
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
	//	System.out.println("storing " + regIndex + "(" + registers[regIndex] + ")" + " in " + memIndex);
		memory[memIndex] = new Data(registers[regIndex]);
	}

}
