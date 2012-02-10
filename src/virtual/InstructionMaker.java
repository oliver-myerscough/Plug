package virtual;
import instructions.AddDirect;
import instructions.AddIndirect;
import instructions.Goto;
import instructions.Ifneg;
import instructions.Ifzer;
import instructions.LoadDirect;
import instructions.LoadIndirect;
import instructions.Stop;
import instructions.StoreDirect;
import instructions.StoreIndirect;
import instructions.SubDirect;
import instructions.SubIndirect;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InstructionMaker {
	
	public static void main(String args[]) {
		
		Map<String, Integer> symbol = new HashMap<String, Integer>();
		
		symbol.put("tony", -10000);
		
		InstructionMaker im = new InstructionMaker(symbol);
		
		System.out.println(im.resolveAddr("tony + 1"));
		
		
	}

	private Map<String, Integer> symbol;
	
	public InstructionMaker(Map<String, Integer> symbol) {
		if(symbol == null) {
			System.out.println("InstructionMaker: given a null symbol table");
		}
		this.symbol = symbol;
	}
	
	private String stripAddressString(String s) {
		return s.substring(1, s.length()-1);
	}
	
	private Integer getRegAddr(String s) {
		if(s.equals("a")) {
			return 0;
		} else if(s.equals("b")) {
			return 1;
		} else if(s.equals("c")) {
			return 2;
		} else if(s.equals("d")) {
			return 3;
		}
		return null; // not a register
	}
	
	private Integer getMemAddr(String s) {
		
		try {
			return Integer.parseInt(s);
		} catch(NumberFormatException e) {
			// not an integer, try to resolve with labels
			return resolveAddr(s);
		}
	}
	
	public Integer resolveAddr(String s) {
		
		Matcher matcher = Pattern.compile("[0-9]+|[A-Z]+|\\+|\\-").matcher(s);
		int acc = 0;
		LinkedList<Integer> valList = new LinkedList<Integer>();
		LinkedList<String> opList = new LinkedList<String>();
		
		if(symbol == null) {
			System.out.println("no symbol table");
		}
		
		while(matcher.find()){
			String tok = matcher.group(); 
			
			if(tok.matches("[A-Z]+")) {
				if(symbol.containsKey(tok)) {
					valList.add(symbol.get(tok));
				} else {
					System.out.println("name " + tok + " is not in my symbol table");
					return null;
				}
			} else if(tok.matches("[0-9]+")) {
				valList.add(Integer.parseInt(tok));
			} else if(tok.equals("+") || tok.equals("-")) {
				opList.add(tok);
			} else {
				// error
				System.out.println("encountered an error on token " + tok + " of " + s);
				return null;
			}
			
		}
		
		acc = valList.pollFirst();
		
		for(int nVal : valList) {
			String tok = opList.poll();
			
			if(tok == null) {
				
				return null;
			}
			
			if(tok.equals("+")) {
				acc += nVal;
			} else if (tok.equals("-")) {
				acc -= nVal;
			} 
		}

		return acc; 
	}
	
	private int nextIndex(Scanner scanner) {
		String s = scanner.next();
		int res;
		
		try {
			res = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			res = resolveAddr(s);
		}
		
		return res;
	}
	
	public Data create_const(Scanner scanner) {
		return new Data(nextIndex(scanner));
	}
	
	public Data create_goto(Scanner scanner){
		int addr = getMemAddr(scanner.next());
		return new Goto(addr);
	}
	
	public Data create_ifzer(Scanner scanner) {
		int reg = getRegAddr(scanner.next());
		int addr = getMemAddr(scanner.next());
		return new Ifzer(reg, addr);
	}

	public Data create_ifneg(Scanner scanner) {
		int reg = getRegAddr(scanner.next());
		int addr = getMemAddr(scanner.next());
		return new Ifneg(reg, addr);
	}
	public Data create_add(Scanner scanner){
		int r1 = getRegAddr(scanner.next());
		String s = stripAddressString(scanner.next());
		
		Integer r2 = getRegAddr(s);
		if(r2 != null) {
			return new AddIndirect(r1, r2);
		}
		
		Integer addr = getMemAddr(s);
		if(addr != null) {
			return new AddDirect(r1, addr);
		}
		
		return null;
		
	}
	
	public Data create_sub(Scanner scanner){
		int r1 = getRegAddr(scanner.next());
		String s = stripAddressString(scanner.next());
		
		Integer r2 = getRegAddr(s);
		if(r2 != null) {
			return new SubIndirect(r1, r2);
		}
		
		Integer addr = getMemAddr(s);
		if(addr != null) {
			return new SubDirect(r1, addr);
		}
		
		return null;
		
	}
	
	public Data create_load(Scanner scanner){
		int r1 = getRegAddr(scanner.next());
		String s = stripAddressString(scanner.next());
		
		Integer r2 = getRegAddr(s);
		if(r2 != null) {
			return new LoadIndirect(r1, r2);
		}
		
		Integer addr = getMemAddr(s);
		if(addr != null) {
			return new LoadDirect(r1, addr);
		}
		
		return null;
	}
	
	public Data create_store(Scanner scanner){
		int r1 = getRegAddr(scanner.next());
		String s = stripAddressString(scanner.next());
		
		Integer r2 = getRegAddr(s);
		if(r2 != null) {
			return new StoreIndirect(r1, r2);
		}
		
		Integer addr = getMemAddr(s);
		if(addr != null) {
			return new StoreDirect(r1, addr);
		}
		
		return null;
	}
	
	public Data create_stop(Scanner scanner) {
		return new Stop();
	}
}
