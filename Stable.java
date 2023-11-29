import java.util.HashMap;

public class Stable extends Merchant{
	
	String name = "Stable";
	
	public Stable(int money, Location location, HashMap<Buyable, Integer> inventory,
			Merchant.EmotionalState emotionalState) {
		super("Stable", money, location, inventory, emotionalState);
	}
	
}
