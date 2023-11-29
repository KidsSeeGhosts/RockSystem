import java.util.HashMap;

public class Merchant {
	
	String name;
	int money;
	Location location;
	HashMap<Buyable, Integer> inventory;
	enum EmotionalState {
        NEUTRAL,
        SCARED
    }
	EmotionalState emotionalState;
	public Merchant(String name, int money, Location location, HashMap<Buyable, Integer> inventory,
			Merchant.EmotionalState emotionalState) {
		super();
		this.name = name;
		this.money = money;
		this.location = location;
		this.inventory = inventory;
		this.emotionalState = emotionalState;
	}
	public boolean checkStock() {
		boolean itemsAvailable = false;
	    for (Buyable buyable : inventory.keySet()) {
	    	if (buyable instanceof Horse){
	    		Horse horse = (Horse) buyable;
	    		if (!horse.isOwned()){
	    			itemsAvailable=true;
	            	System.out.println("$"+buyable.price+" "+buyable.name+ " - Quantity: "+inventory.get(buyable));
	    		}
	    	}
	    	else {
	    		itemsAvailable=true;
	        	System.out.println("$"+buyable.price+" "+buyable.name+ " - Quantity: "+inventory.get(buyable));
	    	}
	    }
	    return itemsAvailable;
	}
	
	public Buyable getBuyable(String itemToBuy) {
        for (Buyable buyable : inventory.keySet()) {
            if (buyable.name.equalsIgnoreCase(itemToBuy)) {
            	return buyable;
            }
        }
        return null;
	}
	

	
	
}
