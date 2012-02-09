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
	
	public static void main(String args[]) {
		Map<String, Integer> symbol = new HashMap<String, Integer>();
		symbol.put("bob", 10);
		InstructionMaker im = new InstructionMaker(symbol);
		System.out.println(im.resolveAddr("135 + bob - 1"));
	}
	public InstructionMaker(Map<String, Integer> symbol) {
		this.symbol = symbol;
	}
	
	public int resolveAddr(String s) {
		
		Matcher matcher = Pattern.compile("[0-9]+|[a-z]+|\\+|\\-").matcher(s);
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
