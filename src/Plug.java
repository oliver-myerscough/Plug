import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Plug {
	
	public static void main(String[] args) {
		
		

		String program = "load 0 5 " +
				"add 0 0 " +
				"store 0 5 " +
				"jump -1";

		Plug vm = new Plug(program);
		
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
	private Map<String, Integer> symbols;

	public Plug(String program){
		sv = new StateVar();
		symbols = new HashMap<String, Integer>();
		
		memory = new Data[MEMLEN];
		registers = new int[4];
		
		for(int i = 0; i < MEMLEN; i++) {
			memory[i] = new Data(1);
		}
		
		setupProgram(program);
		
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
		
		DataMaker dataMaker = new DataMaker();
		
		while(scanner.hasNext()) {
			
			
			
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
