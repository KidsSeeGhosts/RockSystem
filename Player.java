import java.util.HashMap;
import java.util.List;

public class Player {
	HashMap<Item,Integer> satchel;
	Horse equippedHorse;
	Location currentLocation;
	int money;
	int health;
	int deadEye;
	int stamina;
	int ammo;
	Merchant nearestMerchant;
	
	
	public Player(HashMap<Item, Integer> satchel, Horse equippedHorse, Location location, int money, int health,
			int deadEye, int stamina, int ammo) {
		super();
		this.satchel = satchel;
		this.equippedHorse = equippedHorse;
		this.currentLocation = location;
		this.money = money;
		this.health = health;
		this.deadEye = deadEye;
		this.stamina = stamina;
		this.ammo = ammo;
	}

	public HashMap<Item, Integer> getSatchel() {
		return satchel;
	}

	public void setSatchel(HashMap<Item, Integer> satchel) {
		this.satchel = satchel;
	}

	public Horse getEquippedHorse() {
		return equippedHorse;
	}

	public void setEquippedHorse(Horse equippedHorse) {
		this.equippedHorse = equippedHorse;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public void buyItem(Merchant merchant, Buyable buyable) {
		if (Location.calculateDistance(currentLocation, merchant.location)>5){
			System.out.println("Merchant "+merchant.name+" is out of range");
			return;
		}
		if (merchant.emotionalState==Merchant.EmotionalState.SCARED){
			System.out.println("Merchant has been frightened, won't deal with you.");
			return;
		}
		if (money>=buyable.getPrice() && merchant.inventory.get(buyable)>=1) {
			if (buyable instanceof Item){
				Item item = (Item) buyable;
				if (item.isOwned()){
					satchel.put(item, satchel.get(item)+1);
				}
				else {
					satchel.put(item, 1);
					item.owned=true;
				}
				int merchantInventory = merchant.inventory.get(buyable);
				if (merchantInventory-1==0) {
					merchant.inventory.remove(buyable);
				}
				else {
					merchant.inventory.put(buyable, merchantInventory-1);
				}
			}
			else if (buyable instanceof Horse){
				Horse horse = (Horse) buyable;
				if (horse.isOwned()){
					System.out.println("Can't buy already owned horse again");
					return;//purchase doesn't happen
				}
				else {
					horse.owned=true;//the horse can then be equipped as active horse by horse seller
				}
				
			}
			money = money-buyable.getPrice();
			merchant.money=merchant.money+buyable.getPrice();
		}
		System.out.println(buyable.name+ " purchase complete");
	}
	
	//Player sells item to merchant,
	public void sellItem(Merchant merchant, Buyable buyable) {
		if (merchant.emotionalState==Merchant.EmotionalState.SCARED){
			System.out.println("Merchant has been frightened, won't deal with you.");
			return;
		}
		if (merchant.money>=buyable.getPrice()) {
			if (buyable instanceof Item) {
				Item item = (Item) buyable;
				int satchelInventory = satchel.get(item);
				if (satchelInventory<=0){
					System.out.println("not enough of item to sell");
					return;
				}
				if (satchelInventory-1==0){
					satchel.remove(item);
					item.owned=false;
				}
				else {
					satchel.put(item, satchelInventory-1);
				}
				if (merchant.inventory.containsKey(item)){
					merchant.inventory.put(item, merchant.inventory.get(item)+1);
				}
				else {
					merchant.inventory.put(item, 1);
				}
				
			}
			else if (buyable instanceof Horse) {
				if (merchant instanceof Stable) {
					Horse horse = (Horse) buyable;
					if (horse==equippedHorse){
						horse.owned=false;
						equippedHorse=null;
						merchant.inventory.put(horse, 1);
					}
					else {//horse is already in stable, change status of ownership
						merchant.inventory.remove(horse);
						horse.owned=false;
						merchant.inventory.put(horse, 1);
					}
				}
				else {
					System.out.println("Can only sell horse to stable merchant");
					return;
				}
			}
			merchant.money=merchant.money-buyable.getPrice();
			money = money+buyable.getPrice();
			
		}
		else {
			System.out.println("Can't sell item, merchant can't afford to pay");
		}
	}
	//Boosts player stats and decreases item quantity in satchel
	public void useItem(Item item) {
		if (satchel.containsKey(item)) {
			health+=item.health;
			stamina+=item.stamina;
			deadEye+=item.deadEye;
			int currentQuantity = satchel.get(item)-1;
			System.out.println("Used "+item.name+", stats are now Health:"
			+health+" stamina: "+stamina+" Deadeye: "+deadEye);
			if (currentQuantity>0){
				satchel.put(item, currentQuantity);
			}
			else {
				satchel.remove(item);
				item.owned=false;
			}
		}
		else {
			System.out.println("Can't use "+item.name+", not in satchel");
		}
	}
	
	public void rob(Merchant merchant) {
		if (merchant.emotionalState!=Merchant.EmotionalState.SCARED) {
			merchant.emotionalState=Merchant.EmotionalState.SCARED;
		}
		if (merchant.money<=0){
			System.out.println("Robbery victim has no money to give");
			return;
		}
		int oldMoney = money;
		money+=merchant.money;
		merchant.money=0;
		System.out.println("Succesfully robbery, gained $"+(money-oldMoney));
	}
	
	
	public void shootGun() {
		ammo--;
		if (Location.calculateDistance(currentLocation, nearestMerchant.location)<5){
			nearestMerchant.emotionalState=Merchant.EmotionalState.SCARED;
			System.out.println("Shot gun near "+nearestMerchant.name+",they are now scared");
		}
		System.out.println("Shot fired, remaining: "+ammo);
		
	}
	
	public void equipHorse(Horse horse, Stable stable) {
		if (stable.inventory.containsKey(horse) && horse.isOwned()){
			stable.inventory.put(equippedHorse, 1);
			stable.inventory.remove(horse);
			Horse oldHorse = equippedHorse;
			equippedHorse = horse;
			System.out.println(horse.name+" is now current equipped horse, "+oldHorse.name+ " is back in stable");
		}
		else {
			System.out.println("Horse not available in stable");
			return;
		}
	}
	
	//Horse interaction
	public void patHorse() {
		if (equippedHorse != null){
			equippedHorse.bondLevel++;
			System.out.println("Patted current horse "+equippedHorse.name+", bond level is now "+equippedHorse.bondLevel);
		}
	}
	
	public void move(Location newLocation, List<Merchant> merchants) {
		currentLocation=newLocation;
		System.out.println("Location is now ("+currentLocation.x+", "+currentLocation.y+")");
		nearestMerchant = findNearestMerchant(merchants);
	}
	
	public Merchant findNearestMerchant(List<Merchant> merchants) {
		double shortestDistance = Double.MAX_VALUE;
		Merchant nearestMerchant = null;
		for (Merchant merchant:merchants){
			Location merchantLocation = merchant.location;
			double distance = Location.calculateDistance(currentLocation, merchantLocation);
			if (distance <=shortestDistance){
				shortestDistance = distance;
				nearestMerchant = merchant;
			}
		}
		return nearestMerchant;
	}
	
}
