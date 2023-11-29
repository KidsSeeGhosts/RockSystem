import java.util.HashMap;

public class GeneralStore extends Merchant{

	public GeneralStore(int money, Location location, HashMap<Buyable, Integer> inventory,
			Merchant.EmotionalState emotionalState) {
		super("General Store", money, location, inventory, emotionalState);
	}
	
}
