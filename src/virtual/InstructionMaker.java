package virtual;
import instructions.AddR;
import instructions.Goto;
import instructions.LoadR;
import instructions.Stop;
import instructions.StoreR;

import java.awt.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InstructionMaker {

	private Map<String, Integer> symbol;
	
	public InstructionMaker(Map<String, Integer> symbol) {
		this.symbol = symbol;
	}
	
	private int resolveAddr(String s) {
		
		String stripped;
		if(s.startsWith("[") && s.endsWith("]")) {
			stripped = s.substring(1, s.length()-1);
		} else {
			return -1; // invalid
		}
		
		Matcher matcher = Pattern.compile("[0-9]+|[a-z]+|\\+|\\-").matcher(stripped);
		int acc = 0;
		LinkedList<Integer> valList = new LinkedList<Integer>();
		LinkedList<String> opList = new LinkedList<String>();
		
		while(matcher.find()){
			String tok = matcher.group(); 
			System.out.println(tok);
			if(tok.matches("[a-z]+")) {
				System.out.println("name");
				valList.add(symbol.get(tok));
			} else if(tok.matches("[0-9]+")) {
				System.out.println("number");
				valList.add(Integer.parseInt(tok));
			} else if(tok.equals("+") || tok.equals("-")) {
				opList.add(tok);
			} else {
				// error
				System.out.println("encountered an error on token " + tok + " of " + s);
			}
			
		}
		
		acc = valList.pollFirst();
		
		for(int nVal : valList) {
			String tok = opList.poll();
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
	
	public Data create_jump(Scanner scanner){
		String s = scanner.next();
		int addr;
		try {
			addr = Integer.parseInt(s);
			return new Goto(addr);
		} catch (NumberFormatException e) {
			addr = resolveAddr(s);
			// return new GotoM(addr);
		}
		return null;
	}

	public Data create_add(Scanner scanner){
		int r1 = scanner.nextInt();
		String s = scanner.next();
		try {
			int r2 = Integer.parseInt(s);
			return new AddR(r1, r2);
		} catch (NumberFormatException e) {
			int addr = resolveAddr(s);
			// return new AddM(r1, addr);
		}
		return null;
	}
	
	public Data create_load(Scanner scanner){
		int r1 = scanner.nextInt();
		String s = scanner.next();
		try {
			int r2 = Integer.parseInt(s);
			return new LoadR(r1, r2);
		} catch (NumberFormatException e) {
			int addr = resolveAddr(s);
			// return new LoadM(r1, addr);
		}
		return null;
	}
	
	public Data create_store(Scanner scanner){
		int r1 = scanner.nextInt();
		String s = scanner.next();
		try {
			int r2 = Integer.parseInt(s);
			return new StoreR(r1, r2);
		} catch (NumberFormatException e) {
			int addr = resolveAddr(s);
			// return new StoreM(r1, addr);
		}
		return null;
	}
	
	public Data create_stop(Scanner scanner) {
		return new Stop();
	}
}
