import java.util.Map;
import java.util.Scanner;


public class DataMaker {

	private int assignedStorage;
	private Data[] memory;
	private int baseAddr;
	private Map<String, Integer> symbol;
	
	public DataMaker(Data[] memory, int base, Map<String, Integer> symbol) {
		this.memory = memory;
		baseAddr = base;
		assignedStorage = 0;
		this.symbol = symbol;
	}
	
	public void alloc_byte(Scanner scanner) {
		
		symbol.put(scanner.next(), baseAddr + assignedStorage);
		memory[baseAddr + assignedStorage] = new Data(scanner.nextInt());
		assignedStorage++;
		
	}
	
	public int getAssignedStorageSize() {
		return assignedStorage;
	}

}
