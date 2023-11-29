
public class Buyable {
	int price;
	boolean owned;
	String name;
	//buyable class exists so that both horses and items can be bought and sold
	public Buyable(int price, boolean owned, String name) {
		super();
		this.price = price;
		this.owned = owned;
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public boolean isOwned() {
		return owned;
	}
	public void setOwned(boolean owned) {
		this.owned = owned;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
}
