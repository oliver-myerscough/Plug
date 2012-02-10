package virtual;
import instructions.Instruction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.BitUtils;


public class Plug {
	
	public static void main(String[] args) {
		
		String data = 
					"byte A 0 " +
					"byte B 4 " +
					"byte C 2 " +
					"byte Z 0 " +
					"byte O 1 " +
					"byte N -1";
		
		String program = 
				"load a [Z] " +
				"load b [C] " +
				"ifzer b 7 " +
				"ifneg b 7 " +
				"add a [B] " +
				"sub b [O] " +
				"goto 2 " +
				"store a [A] " +
				"stop ";
		
		
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
	private Map<String, Integer> symbol;
	private int dataBaseAddr;
	private InstructionMaker instrMaker;

	public Plug(String program, String data){
		sv = new StateVar();
		symbol = new HashMap<String, Integer>();
		dataBaseAddr = countInstructions(program);
		instrMaker = new InstructionMaker(symbol);
		memory = new Data[MEMLEN];
		registers = new int[4];
		sv.debug = true;
		
		for(int i = 0; i < MEMLEN; i++) {
			memory[i] = new Data(0);
		}
		
		setupData(data);
		System.out.println(this);
		setupProgram(program);
		
	}
	
	private int countInstructions(String program) {
		int cnt = 0;
		Scanner scanner = new Scanner(program);
		Set<String> mnemonics = new HashSet<String>();
		
		mnemonics.add("load");
		mnemonics.add("add");
		mnemonics.add("sub");
		mnemonics.add("store");
		mnemonics.add("goto");
		mnemonics.add("ifneg");
		mnemonics.add("ifzer");
		mnemonics.add("stop");
		
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
			Instruction currInstruction; 
			if(memory[sv.pc] instanceof Instruction) {
				currInstruction = (Instruction) memory[sv.pc];
			} else {
				currInstruction = BitUtils.interpretInstruction(memory[sv.pc]);
			}
			sv.pc++;
			currInstruction.execute(memory, registers, sv);
		}
	}
	
	private void setupData(String data) {
		
		Scanner scanner = new Scanner(data);
		
		DataMaker dataMaker = new DataMaker(memory, dataBaseAddr, symbol);
		
		while(scanner.hasNext()) {
			
			scanner.next();
			// assume bytes for now
			dataMaker.alloc_byte(scanner);
			
		}
		
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

		
		try {
			System.out.println("creating " + name + " instruction");
			return (Data) InstructionMaker.class.getMethod("create_" + name, Scanner.class).invoke(instrMaker, scanner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("PC: " + sv.pc + "\n0\t");

		for(int i = 0; i < memory.length; i++){
			String hex = Integer.toHexString(memory[i].getVal()); 
			sb.append(hex + (hex.length() == 8 ? "\t" : "\t\t"));
			if(i % 5 == 4 && i != memory.length-1) sb.append("\n" + (i+1) + "\t");
		}
		
		return sb.toString();
	}
	
	
}
