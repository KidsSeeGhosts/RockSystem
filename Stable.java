import java.util.HashMap;

public class Stable extends Merchant{
	
	String name = "Stable";
	
	public Stable(int money, Location location, HashMap<Buyable, Integer> inventory,
			Merchant.EmotionalState emotionalState) {
		super("Stable", money, location, inventory, emotionalState);
	}
	
	public boolean showOwnedHorses() {
		boolean ownedHorses = false;
    	for (Buyable buyable : inventory.keySet()) {
        	if (buyable instanceof Horse && buyable.owned){
        		ownedHorses = true;
        		Horse horse = (Horse) buyable;
        		System.out.println(horse.name + " - OWNED");
        	}
        }
    	if (!ownedHorses){
    		System.out.println("No owned horses in this stable");
    	}
    	return ownedHorses;
	}

	public Horse getHorse(String horseToEquip) {
        for (Buyable buyable : inventory.keySet()) {
            if (buyable.name.equalsIgnoreCase(horseToEquip)) {
            	Horse chosenHorse = (Horse) buyable;
                return chosenHorse;
            }
        }
        return null;
	}
	
}
