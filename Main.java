import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
	
	
	public static void main(String[] args) {
		Horse thoroughbredHorse = new Horse(100, true, "Thoroughbred",
				6,
				10,
				0,
				8,
				4,
				Horse.Handling.Race);
		
		
		Horse shireHorse = new Horse(200, false, "Shire",
				7,
				10,
				0,
				7,
				6,
				Horse.Handling.Heavy);
		
		Horse tennesseeHorse = new Horse(200, false, "Tennessee Walker",
				5,
				10,
				0,
				5,
				5,
				Horse.Handling.Standard);

		
		Item beans = new Item(10, true, "Beans", 0, 2, 0);
		Item cigarettes = new Item(20, true, "Cigarettes", 2, -1, 4);
		Item miracleTonic = new Item(40, false, "Miracle Tonic", 3, 3, 3);
		Item staminaGum = new Item(10, true, "Stamina gum", 3, 0, 0);
		
		HashMap<Item,Integer> satchel = new HashMap<Item,Integer>();
		
		HashMap<Buyable,Integer> storeInventory = new HashMap<Buyable,Integer>();
		Location storeLocation = new Location(5.0,1.0);
		//money, location, inventory, emotional state.
		GeneralStore generalStore = new GeneralStore(1000, storeLocation, storeInventory, Merchant.EmotionalState.NEUTRAL);
		
		HashMap<Buyable,Integer> stableInventory = new HashMap<Buyable,Integer>();
		Location stableLocation = new Location(30.0,12.0);
		Stable stable = new Stable(800, stableLocation, stableInventory, Merchant.EmotionalState.NEUTRAL);

		satchel.put(beans,3);
		satchel.put(cigarettes,3);
		satchel.put(staminaGum,3);
		
		storeInventory.put(beans, 10);
		storeInventory.put(cigarettes, 10);
		storeInventory.put(miracleTonic, 10);
		storeInventory.put(staminaGum, 10);
		
		stableInventory.put(shireHorse, 1);
		stableInventory.put(tennesseeHorse, 1);
		
		Location currentLocation = new Location(1.0,2.0);
		
		//inventory, horse, location, money, health, deadeye, stamina. ammo
		Player mainPlayer = new Player(satchel,
		thoroughbredHorse, 
		currentLocation,
		500,
		5,
		5,
		5,
		20);
		
		//initialise everything
		
		//start player location, find nearest merchant, if distance less than thing
		
		List<Merchant> merchants = new ArrayList<>();
		merchants.add(generalStore);
		merchants.add(stable);
		
		mainPlayer.nearestMerchant=mainPlayer.findNearestMerchant(merchants);
		System.out.println("Set up complete"+", stats are now Health:"
				+mainPlayer.health+" stamina: "+mainPlayer.stamina+" Deadeye: "+mainPlayer.deadEye);
		
		
		mainPlayer.patHorse();//equipped horse bond goes up
		mainPlayer.useItem(miracleTonic);//doesn't have it, can't use it
		mainPlayer.buyItem(generalStore, miracleTonic);//money changes, inventory changes.
		mainPlayer.useItem(miracleTonic);//it's used, stats change
		mainPlayer.useItem(beans);
		mainPlayer.buyItem(stable, shireHorse);//horse is now owned
		mainPlayer.move(stableLocation, merchants);
		mainPlayer.buyItem(stable, shireHorse);//horse is now owned
		mainPlayer.equipHorse(shireHorse, stable);//horse can be equipped
		mainPlayer.patHorse();
		mainPlayer.patHorse();
		mainPlayer.rob(generalStore);
		mainPlayer.buyItem(generalStore, beans);//service refused
		mainPlayer.shootGun();//no impact on stable guy, can still buy
		mainPlayer.move(stableLocation,merchants);
		mainPlayer.buyItem(stable, tennesseeHorse);//service refused
		mainPlayer.shootGun();
		mainPlayer.sellItem(stable, tennesseeHorse);//service refused, scared
		mainPlayer.rob(stable);
	}
	
	
	

	
	
	
	
}
