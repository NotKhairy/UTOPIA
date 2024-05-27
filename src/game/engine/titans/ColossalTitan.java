package game.engine.titans;

public class ColossalTitan extends Titan
{
	public static final int TITAN_CODE = 4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	
	public ColossalTitan clone() {
		ColossalTitan clone = new ColossalTitan(this.getBaseHealth(), this.getDamage(), this.getHeightInMeters(), this.getDistance(), this.getSpeed(), this.getResourcesValue(), this.getDangerLevel());
		clone.setCurrentHealth(this.getCurrentHealth());
		return clone;
	}

	@Override
	public boolean move()
	{
		boolean moveResult = super.move();
		this.setSpeed(this.getSpeed() + 1);
		return moveResult;
	}
}
