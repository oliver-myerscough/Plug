package virtual;
import instructions.Instruction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.BitUtils;


public class Plug {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner input;
		PrintStream output;
		
		if(args.length == 1) {
			output = System.out;
			input = new Scanner(new FileReader(new File(args[0])));
		} else if(args.length == 2) {
			input = new Scanner(new FileReader(new File(args[0])));
			output = System.out;
		} else {
			System.out.println("requires an input program to execute");
			return;
		};
		
		Plug vm = new Plug(input, output, false);
		
		vm.run();
		
		
	}
	
	private Data[] memory;
	private int[] registers;
	private int progEnd;
	private StateVar sv;
	private int MEMLEN = 40;
	private Map<String, Integer> symbol;
	private int dataBaseAddr;
	private InstructionMaker instrMaker;
	private PrintStream outputStream;
	

	public Plug(Scanner programInput, PrintStream outputStream, boolean debug){
		
		sv = new StateVar();
		symbol = new HashMap<String, Integer>();
		instrMaker = new InstructionMaker(symbol);
		memory = new Data[MEMLEN];
		registers = new int[4];
		sv.debug = debug;
		this.outputStream = outputStream;
		
		StringBuilder dataBuilder = new StringBuilder();
		StringBuilder programBuilder = new StringBuilder();
		
		
		boolean beforeProg = true;
		while(programInput.hasNext()) {
			String n = programInput.next();
			if(n.equals("program")){
				beforeProg = false;
			} else if(beforeProg) {
				dataBuilder.append(n + " ");
			} else {
				programBuilder.append(n + " ");
			}
		}
		
		String program = programBuilder.toString();
		String data = dataBuilder.toString();
		
		dataBaseAddr = countInstructions(program);
		
		for(int i = 0; i < MEMLEN; i++) {
			memory[i] = new Data(0);
		}
		
		setupData(data);
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
		
		outputStream.print(this);
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
