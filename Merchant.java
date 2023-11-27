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
	

	
	
}
