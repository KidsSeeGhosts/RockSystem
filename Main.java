import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//Written by Ruairi, November 2023

public class Main {
	
	static double maxDistance = 5;//distance for merchant to be in range
	
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
		
		List<Merchant> merchants = new ArrayList<>();
		merchants.add(generalStore);
		merchants.add(stable);

		
		mainPlayer.nearestMerchant=mainPlayer.findNearestMerchant(merchants);
		System.out.println("Set up complete"+", stats are now\nHealth: "
				+mainPlayer.health+" Stamina: "+mainPlayer.stamina+" Deadeye: "+mainPlayer.deadEye+"\n");
		
		System.out.println("\nStarting location: ("+currentLocation.x+","+currentLocation.y+")");
		System.out.println("General Store location: ("+storeLocation.x+","+storeLocation.y+")");
		System.out.println("Stable location: ("+stableLocation.x+","+stableLocation.y+")\n");
		Scanner scanner = new Scanner(System.in);


        while (true) {
        	if (Location.calculateDistance(mainPlayer.currentLocation,stable.location)<maxDistance 
        			&& mainPlayer.nearestMerchant instanceof Stable){
                System.out.println("\nAvailable Commands:");//stable in range
                System.out.println("1. Move");
                System.out.println("2. Use Item");
                System.out.println("3. Shoot");
                System.out.println("4. Pat Horse\n");
                System.out.println("5. Equip Horse");//command only available at stable
                System.out.println("6. Buy item");
                System.out.println("7. Sell item");
                System.out.println("8. Rob\n");
                System.out.println("9. Inventory");
                System.out.println("10. Stats");
                System.out.println("11. Exit\n");
        	}
        	else if (Location.calculateDistance(mainPlayer.currentLocation,storeLocation)<maxDistance) {
                System.out.println("\nAvailable Commands:");//store in range
                System.out.println("1. Move");
                System.out.println("2. Use Item");
                System.out.println("3. Shoot");
                System.out.println("4. Pat Horse\n");
                System.out.println("6. Buy item");
                System.out.println("7. Sell item");
                System.out.println("8. Rob\n");
                System.out.println("9. Inventory");
                System.out.println("10. Stats");
                System.out.println("11. Exit\n");
        	}
        	else {
                System.out.println("\nAvailable Commands:");
                System.out.println("1. Pat Horse");
                System.out.println("2. Use Item");
                System.out.println("3. Shoot\n");
                System.out.println("4. Inventory");
                System.out.println("5. Move");
                System.out.println("6. Stats");
                System.out.println("7. Exit\n");
        	}
            System.out.print("Enter your command: ");
            String command = scanner.nextLine();
            System.out.println();
            processCommand(scanner, command, mainPlayer, merchants, generalStore, stable);
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Game over");
                break;
            }
        }

        scanner.close();
	}
	
	
	private static void processCommand(Scanner scanner, String command, Player player, List<Merchant> merchants,
            GeneralStore generalStore, Stable stable) {
        switch (command.toLowerCase()) {
	        case "move":
	            System.out.println("Enter new x coordinate: ");
	            double newX = Double.parseDouble(scanner.nextLine());
	            System.out.println("Enter new y coordinate: ");
	            double newY = Double.parseDouble(scanner.nextLine());
	            System.out.println("Moving to (" + newX + ", " + newY + ")");
	            player.move(new Location(newX, newY), merchants);
	            break;
            case "use item":
            	boolean usedItem = false;
                System.out.println("Satchel contents:");
                for (Item item : player.satchel.keySet()) {
                	System.out.println(item.name+ " - Quantity: "+player.satchel.get(item));
                }
                System.out.println("\n\nPick item to use: ");
                String itemName = scanner.nextLine();
                for (Item item : player.satchel.keySet()) {
                    if (item.name.equalsIgnoreCase(itemName)) {
                        player.useItem(item);
                        usedItem=true;
                        break;
                    }
                }
                if (usedItem==false){
                    System.out.println("item was not found in inventory \n");
                }
                break;
            case "shoot":
            	player.shootGun();
            	break;
            case "pat horse":
                player.patHorse();
                break;
            case "buy item":
            	boolean boughtItem = false;
            	System.out.println("Player money: $"+player.money);
                Merchant nearestMerchant = player.nearestMerchant;
                double merchantDistance = Location.calculateDistance(player.currentLocation, nearestMerchant.location);
                if (merchantDistance>maxDistance){
                	System.out.println("Merchant "+nearestMerchant.name+" ("+nearestMerchant.location.x+", "+nearestMerchant.location.y+
                			") is more than 5m away, move closer to buy from them.");
                	break;
                }
                if (nearestMerchant.emotionalState==Merchant.EmotionalState.SCARED){
                	System.out.println("Merchant has been frightened, won't deal with you.");
                	break;
                }
                if (nearestMerchant.inventory.isEmpty()){
                	System.out.println("Merchant has no stock to sell");
                	break;
                }
                System.out.println("Nearest merchant "+nearestMerchant.name+" inventory for sale:\n");
                boolean itemsAvailable = false;
                for (Buyable buyable : player.nearestMerchant.inventory.keySet()) {
                	if (buyable instanceof Horse){
                		Horse horse = (Horse) buyable;
                		if (!horse.isOwned()){
                			itemsAvailable=true;
                        	System.out.println("$"+buyable.price+" "+buyable.name+ " - Quantity: "+nearestMerchant.inventory.get(buyable));
                		}
                	}
                	else {
                		itemsAvailable=true;
                    	System.out.println("$"+buyable.price+" "+buyable.name+ " - Quantity: "+nearestMerchant.inventory.get(buyable));
                	}
                }
                if (!itemsAvailable){
                	System.out.println("SOLD OUT");//checked this way to account for owned horses
                	break;
                }
                System.out.println("What do you buy?");
                String itemToBuy = scanner.nextLine();
                for (Buyable buyable : nearestMerchant.inventory.keySet()) {
                    if (buyable.name.equalsIgnoreCase(itemToBuy)) {
                        player.buyItem(nearestMerchant, buyable);
                        boughtItem=true;
                        break;
                    }
                }
                if (boughtItem==false){
                    System.out.println("Item not found\n");
                }
                break;
            case "sell item":
                System.out.println("Satchel contents:");
                for (Item item : player.satchel.keySet()) {
                	System.out.println(item.name+ " - Quantity: "+player.satchel.get(item));
                }
                System.out.println("Player horse: "+player.equippedHorse.name);
                System.out.println("\n\n What do you sell?: ");
                String itemToSell = scanner.nextLine();
                if (itemToSell.equalsIgnoreCase(player.equippedHorse.name)){//selling horse
                	System.out.println("oh dear");
                	player.sellItem(player.nearestMerchant, player.equippedHorse);
                	break;
                }//check satchel
                for (Item item : player.satchel.keySet()) {
                    if (item.name.equalsIgnoreCase(itemToSell)) {
                        player.sellItem(player.nearestMerchant, item);
                        break;
                    }
                }
                break;
            case "rob":
            	player.rob(player.nearestMerchant);
            	break;
            case "inventory":
                System.out.println("Satchel contents:");
                for (Item item : player.satchel.keySet()) {
                	System.out.println(item.name+ " - Quantity: "+player.satchel.get(item));
                }
                break;
            case "stats":
            	System.out.println("Money: $"+player.money);
				System.out.println("Health: "+player.health+" Stamina: "+player.stamina+" Deadeye: "+player.deadEye+"\n");
            	break;
            case "equip horse":
            	if (stable.emotionalState==Merchant.EmotionalState.SCARED){
            		System.out.println("Stable is scared and won't deal with you");
            		break;
            	}
                double stableDistance = Location.calculateDistance(player.currentLocation, stable.location);
                if (stableDistance>maxDistance){
                	System.out.println("Stable is more than 5m away, move closer to equip horse");
                	break;
                }
            	boolean ownedHorses = false;
            	
            	for (Buyable buyable : stable.inventory.keySet()) {
                	if (buyable instanceof Horse && buyable.owned){
                		ownedHorses = true;
                		Horse horse = (Horse) buyable;
                		System.out.println(horse.name + " - OWNED");
                	}
                }
            	if (!ownedHorses){
            		System.out.println("No owned horses in this stable");
            		break;
            	}
            	else {
            		System.out.println("Equip which horse?");
            		String horseToEquip = scanner.nextLine();
                    for (Buyable buyable : stable.inventory.keySet()) {
                        if (buyable.name.equalsIgnoreCase(horseToEquip)) {
                        	Horse chosenHorse = (Horse) buyable;
                            player.equipHorse(chosenHorse,stable);
                            break;
                        }
                    }
            	}
            	break;
            case "exit":
            	break;
            default:
                System.out.println("Invalid command, try again.");
        }
    }

}
