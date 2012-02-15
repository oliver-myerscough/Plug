package instructions;
import util.BitUtils;
import virtual.Data;
import virtual.StateVar;


public class Goto extends Jump {

	public Goto(int j){
		super(BitUtils.createInstrValOne(5, 0, j), j);
	}

	@Override
	public void execute(Data[] memory, int[] registers, StateVar sv) {
	
		if(sv.debug) {
			System.out.println("goto " + getJumpLoc());
		}
		
	}

}
