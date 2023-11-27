
public class Item extends Buyable{
	int stamina;
	int health;
	int deadEye;

	public Item(int price, boolean owned, String name, int stamina, int health, int deadEye) {
		super(price, owned, name);
		this.stamina = stamina;
		this.health = health;
		this.deadEye = deadEye;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDeadEye() {
		return deadEye;
	}

	public void setDeadEye(int deadEye) {
		this.deadEye = deadEye;
	}
	
	
	
	
	
}
