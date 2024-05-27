package game.engine.base;

import game.engine.interfaces.Attackee;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Wall implements Attackee, Cloneable
{
	private final int baseHealth;
	private int currentHealth;
	private IntegerProperty wallCurrentHealthIntegerProperty;

	public Wall(int baseHealth)
	{
		super();
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
		this.wallCurrentHealthIntegerProperty =new SimpleIntegerProperty(this.currentHealth);
	}
	
	public Wall clone() {
		Wall clone = new Wall(baseHealth);
		clone.currentHealth = this.currentHealth;
		return clone;
	}

	public int getBaseHealth()
	{
		return this.baseHealth;
	}

	@Override
	public int getCurrentHealth()
	{
		return this.currentHealth;
	}

	@Override
	public void setCurrentHealth(int health)
	{
		this.currentHealth = health < 0 ? 0 : health;
		this.setWallCurrentHealthIntegerProperty(this.currentHealth);
	}

	@Override
	public int getResourcesValue() // implies that a wall was damaged and not the enemy (titan)
	{
		return -1;
	}
	
	public IntegerProperty getCurrentHealthIntegerProperty() {
		return this.wallCurrentHealthIntegerProperty;
	}
	
	public void setWallCurrentHealthIntegerProperty(int currentHealth) {
		this.wallCurrentHealthIntegerProperty.set(currentHealth);
	}

}
