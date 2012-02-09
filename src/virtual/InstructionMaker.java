package virtual;
import instructions.AddR;
import instructions.Goto;
import instructions.LoadR;
import instructions.Stop;
import instructions.StoreR;

import java.util.Map;
import java.util.Scanner;


public class InstructionMaker {

	private Map<String, Integer> symbol;
	
	public InstructionMaker(Map<String, Integer> symbol) {
		this.symbol = symbol;
	}
	
	public Data create_const(Scanner scanner) {
		return new Data(scanner.nextInt());
	}
	
	public Data create_jump(Scanner scanner){
		int addr = scanner.nextInt();
		return new Goto(addr);
	}

	public Data create_add(Scanner scanner){
		int r1 = scanner.nextInt();
		int r2 = scanner.nextInt();		
		return new AddR(r1, r2);
	}
	
	public Data create_load(Scanner scanner){
		int r = scanner.nextInt();
		int m = scanner.nextInt();
		return new LoadR(r, m);
	}
	
	public Data create_store(Scanner scanner){
		int r = scanner.nextInt();
		int m = scanner.nextInt();
		return new StoreR(r, m);
	}
	
	public Data create_stop(Scanner scanner) {
		return new Stop();
	}
}
