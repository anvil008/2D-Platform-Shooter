package Entities;

import tileMap.TileMap;

public class Foe extends MapObject {

	protected boolean flinching;
	protected long flinchTimer;

	// constructor - passes through the tilemap
	public Foe(TileMap tm) {

		// call map object constructor - sets tilemap and tilesize
		super(tm);

	}

	// the enemy gets hit with this much damage
	public void hit(int damage) {

		if (isDead || flinching)
			return;
		foeHealth -= damage;
		if (foeHealth < 0)
			foeHealth = 0;
		if (foeHealth == 0)
			isDead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	// returns if foe is isDead
	public boolean isEnemyDead() {
		return isDead;
	}

	// returns the damage of the foe
	public int getDamage() {
		return foeDamage;
	}

	public void update() {
	}

}