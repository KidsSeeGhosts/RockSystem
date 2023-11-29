import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//Written by Ruairi, November 2023

public class Main {
	
	static double maxDistance = 5;//distance for merchant to be in range
	static Horse thoroughbredHorse = new Horse(100, true, "Thoroughbred",
			6,//stamina
			10,//health
			0,//bond level
			8,//speed
			4,//acceleration
			Horse.Handling.Race);
	
	static Horse shireHorse = new Horse(200, false, "Shire",
			7,
			10,
			0,
			7,
			6,
			Horse.Handling.Heavy);
	
	static Horse tennesseeHorse = new Horse(200, false, "Tennessee Walker",
			5,
			10,
			0,
			5,
			5,
			Horse.Handling.Standard);
	
	static Item beans = new Item(10, true, "Beans", 0, 2, 0);
	static Item cigarettes = new Item(20, true, "Cigarettes", 2, -1, 4);
	static Item miracleTonic = new Item(40, false, "Miracle Tonic", 3, 3, 3);
	static Item staminaGum = new Item(10, true, "Stamina gum", 3, 0, 0);
	
	static Location storeLocation = new Location(5.0,1.0);
	static Location stableLocation = new Location(30.0,12.0);
	
	public static void main(String[] args) {
		
		HashMap<Buyable,Integer> storeInventory = new HashMap<Buyable,Integer>();
		//money, location, inventory, emotional state.
		GeneralStore generalStore = new GeneralStore(1000, storeLocation, storeInventory, Merchant.EmotionalState.NEUTRAL);
		
		HashMap<Buyable,Integer> stableInventory = new HashMap<Buyable,Integer>();
		Stable stable = new Stable(800, stableLocation, stableInventory, Merchant.EmotionalState.NEUTRAL);

		HashMap<Item,Integer> satchel = new HashMap<Item,Integer>();
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
		
		Player mainPlayer = new Player(satchel,
		thoroughbredHorse, 
		currentLocation,
		200,//money
		5,//health
		5,//deadeye
		5,//stamina
		20);//ammo
		
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
            System.out.println("\nAvailable Commands:");//store in range
            System.out.println("1. Move");
            System.out.println("2. Use Item");
            System.out.println("3. Shoot");
            System.out.println("4. Pat Horse\n");
            System.out.println("5. Inventory");
            System.out.println("6. Stats");
            System.out.println("7. Exit\n");
        	if (Location.calculateDistance(mainPlayer.currentLocation,stable.location)<maxDistance 
        			&& mainPlayer.nearestMerchant instanceof Stable){
                System.out.println("8 Equip Horse");//command only available at stable
                System.out.println("9 Buy item");
                System.out.println("10 Sell item");
                System.out.println("11 Rob\n");
        	}
        	else if (Location.calculateDistance(mainPlayer.currentLocation,storeLocation)<maxDistance) {
                System.out.println("8. Buy item");
                System.out.println("9. Sell item");
                System.out.println("10. Rob\n");
        	}
            System.out.print("Enter your command: ");
            String command = scanner.nextLine();
            processCommand(scanner, command, mainPlayer, merchants, generalStore, stable);
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exited game, thanks for playing.");
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
            	printInventory(player);
                System.out.println("\n\nPick item to use: ");
                String itemName = scanner.nextLine();
                Item item = getSatchelItem(player, itemName);
                if (item==null){
                    System.out.println("item was not found in inventory \n");
                    break;
                }
                player.useItem(item);
                break;
            case "shoot":
            	player.shootGun();
            	break;
            case "pat horse":
                player.patHorse();
                break;
            case "buy item":
            	System.out.println("Player money: $"+player.money);
                Merchant nearestMerchant = player.nearestMerchant;
                System.out.println("Nearest merchant "+nearestMerchant.name+" inventory for sale:\n");
                boolean itemsAvailable = nearestMerchant.checkStock();
                if (!itemsAvailable){
                	System.out.println("SOLD OUT");//checked this way to account for owned horses
                	break;
                }
                System.out.println("What do you buy?");
                String itemToBuy = scanner.nextLine();
                Buyable buyableItem = nearestMerchant.getBuyable(itemToBuy);
                if (buyableItem==null){
                    System.out.println("Invalid item input");
                }
                else {
                	player.buyItem(nearestMerchant, buyableItem);
                }
                break;
            case "sell item":
            	printInventory(player);
            	System.out.println("Equipped horse: "+player.equippedHorse.name);
                System.out.println("\n\n What do you sell?: ");
                String itemToSell = scanner.nextLine();
                if (itemToSell.equalsIgnoreCase(player.equippedHorse.name)){
                	player.sellItem(player.nearestMerchant, player.equippedHorse);
                	break;
                }
                Item sellableItem = getSatchelItem(player, itemToSell);
                if (sellableItem==null){
                    System.out.println("item was not found in inventory \n");
                    break;
                }
                player.sellItem(player.nearestMerchant, sellableItem);
                break;
            case "rob":
            	player.rob(player.nearestMerchant);
            	break;
            case "inventory":
            	printInventory(player);
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
            	boolean ownedHorses = stable.showOwnedHorses();
            	if (!ownedHorses){
            		break;
            	}
            	else {
            		System.out.println("Equip which horse?");
            		String horseToEquip = scanner.nextLine();
            		Horse chosenHorse = stable.getHorse(horseToEquip);
            		if (chosenHorse==null){
            			System.out.println("Invalid horse selection");
            			break;
            		}
                    player.equipHorse(chosenHorse,stable);
                    break;
            	}
            case "exit":
            	break;
            default:
                System.out.println("Invalid command, try again.");
        }
    }
	
	private static Item getSatchelItem(Player player, String itemName) {
        for (Item item : player.satchel.keySet()) {
            if (item.name.equalsIgnoreCase(itemName)) {
            	return item;
            }
        }
		return null;
	}

	private static void printInventory(Player player) {
        System.out.println("Satchel contents:");
        for (Item item : player.satchel.keySet()) {
        	System.out.println(item.name+ " - Quantity: "+player.satchel.get(item));
        }
	}

}
