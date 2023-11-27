
public class Horse extends Buyable {
	
	enum Handling {
		Standard,
		Heavy,
		Race
	}
	
	String breed;
	int stamina;
	int health;
	int bondLevel;
	int speed;
	int acceleration;
	Handling handlingType;
	
	public Horse(int price, boolean owned, String breed, int stamina, int health, int bondLevel, int speed,
			int acceleration, Horse.Handling handlingType) {
		super(price, owned, breed);
		this.breed = breed;
		this.stamina = stamina;
		this.health = health;
		this.bondLevel = bondLevel;
		this.speed = speed;
		this.acceleration = acceleration;
		this.handlingType = handlingType;
	}
	

	
	
}
