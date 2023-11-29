
public class Location {
	double x;
	double y;
	
	public Location(double x, double y) {
		this.x=x;
		this.y=y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	//Euclidian distance between locations
	public static double calculateDistance(Location location1, Location location2) {
		return Math.sqrt((location2.y - location1.y) * (location2.y - location1.y) 
				+ (location2.x - location1.x) * (location2.x - location1.x));
	}
	
}
