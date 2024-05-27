package game.gui;


public class Move {
	private double ratio;
	private Boolean pass;
	private int laneNumber;
	private int weaponCode;
	private String description;
	
	public Move() {}
	public Move(Boolean pass, int lane, int weaponCode, String description) {
		this.setPass(pass);
		this.setLaneNumber(lane);
		this.setWeaponCode(weaponCode);
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}


	public int getWeaponCode() {
		return weaponCode;
	}

	public void setWeaponCode(int weaponCode) {
		this.weaponCode = weaponCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public int getLaneNumber() {
		return laneNumber;
	}
	public void setLaneNumber(int laneNumber) {
		this.laneNumber = laneNumber;
	}
}
