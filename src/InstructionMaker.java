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
		System.out.println("jump");
		int addr = scanner.nextInt();
		return new Goto(addr);
	}

	public Data create_add(Scanner scanner){
		System.out.println("add");
		int r1 = scanner.nextInt();
		int r2 = scanner.nextInt();		
		return new AddR(r1, r2);
	}
	
	public Data create_load(Scanner scanner){
		System.out.println("load");
		int r = scanner.nextInt();
		int m = scanner.nextInt();
		return new LoadR(r, m);
	}
	
	public Data create_store(Scanner scanner){
		System.out.println("store");
		int r = scanner.nextInt();
		int m = scanner.nextInt();
		return new StoreR(r, m);
	}
	
	public Data create_stop(Scanner scanner) {
		System.out.println("stop");
		return new Stop();
	}
}
