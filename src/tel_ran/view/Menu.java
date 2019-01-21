package tel_ran.view;
import java.util.*;
public class Menu {
InputOutput inputOutput;
Iterable<Item> items;
public Menu(InputOutput inputOutput, Iterable<Item> items) {
	super();
	this.inputOutput = inputOutput;
	this.items = items;
}
public void runMenu(){
	ArrayList<Item> arrayItems=getArrayItems();
	int nItems=arrayItems.size();
	while(true){
		for(int i=1;i<=nItems;i++){
			Item item=arrayItems.get(i-1);
			inputOutput.put(i+". "+item.displayedName());
		}
		
		int selected=inputOutput.getInteger("select item number", 1, nItems+1);
		Item selectedItem=arrayItems.get(selected-1);
		selectedItem.perform();
		if(selectedItem.isExit())
			break;
	}
}
private ArrayList<Item> getArrayItems() {
	ArrayList<Item> result=new ArrayList<>();
	for(Item item:items){
		result.add(item);
	}
	return result;
}
}
