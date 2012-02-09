import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Plug {
	
	public static void main(String[] args) {
		
		String data = "byte tony 8";

		String program = "load 0 5 " +
				"add 0 0 " +
				"store 0 5 " +
				"jump -1";

		Plug vm = new Plug(program, data);
		
		System.out.println("starting...\n" + vm);
		
		vm.run();
		
		System.out.println("done\n");
		System.out.println(vm);
		
	}
	
	private Data[] memory;
	private int[] registers;
	private int progEnd;
	private StateVar sv;
	private int MEMLEN = 40;
	private int dataSize;
	private Map<String, Integer> symbol;
	private int dataBaseAddr;

	public Plug(String program, String data){
		sv = new StateVar();
		symbol = new HashMap<String, Integer>();
		dataBaseAddr = countInstructions(program);
		
		memory = new Data[MEMLEN];
		registers = new int[4];
		
		for(int i = 0; i < MEMLEN; i++) {
			memory[i] = new Data(1);
		}
		
		setupData(data);
		setupProgram(program);
		
	}
	
	private int countInstructions(String program) {
		int cnt = 0;
		Scanner scanner = new Scanner(program);
		Set<String> mnemonics = new HashSet<String>();
		
		while(scanner.hasNext()) {
			String s = scanner.next();
			if(mnemonics.contains(s)) {
				cnt++;
			}
		}
		
		return cnt;
	}
	
	public void run(){
		
		while (sv.pc != -1) {
			Instruction currInstruction = (Instruction) memory[sv.pc];
			sv.pc++;
			currInstruction.execute(memory, registers, sv);
		}
	}
	
	private void setupData(String data) {
		
		Scanner scanner = new Scanner(data);
		
		DataMaker dataMaker = new DataMaker(memory, dataBaseAddr, symbol);
		
		while(scanner.hasNext()) {
			
			String s = scanner.next();
			// assume bytes for now
			dataMaker.alloc_byte(scanner);
			
		}
		
		dataSize = dataMaker.getAssignedStorageSize();
		
	}

	private void setupProgram(String program) {
		
		Scanner scanner = new Scanner(program);
		
		while (scanner.hasNext()){
			
			memory[progEnd] = createInstruction(scanner);
			
			progEnd++;
		}
		
	}

	private Data createInstruction(Scanner scanner) {
		
		String name = scanner.next();

		InstructionMaker im = new InstructionMaker(null);
		
		try {
			return (Data) InstructionMaker.class.getMethod("create_" + name, Scanner.class).invoke(im, scanner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("PC: " + sv.pc + " CMP: " + sv.cmp + "\n0\t");

		for(int i = 0; i < memory.length; i++){
			sb.append( Integer.toBinaryString(memory[i].getVal()) + "\t\t" );
			if(i % 5 == 4) sb.append("\n" + (i+1) + "\t");
		}
		
		return sb.toString();
	}
	
	
}
