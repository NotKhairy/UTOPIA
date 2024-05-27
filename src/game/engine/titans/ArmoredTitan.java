package game.engine.titans;

public class ArmoredTitan extends Titan
{
	public static final int TITAN_CODE = 3;

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	
	public ArmoredTitan clone() {
		ArmoredTitan clone = new ArmoredTitan(this.getBaseHealth(), this.getDamage(), this.getHeightInMeters(), this.getDistance(), this.getSpeed(), this.getResourcesValue(), this.getDangerLevel());
		clone.setCurrentHealth(this.getCurrentHealth());
		return clone;
	}
	
	@Override
	public int takeDamage(int damage)
	{
		return super.takeDamage(damage / 4);
	}

}
