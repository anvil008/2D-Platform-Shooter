package JUnitTest;
import Entities.*;
import gameState.*;
import tileMap.*;
import main.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestingMethods {
	
	//method to test getFrame in Animation
	@Test
	public void getFrameTest(){
	// arrange
	// act 
		Animation a = new Animation();
	int intexpected = 0;
	//assert
	assertEquals(intexpected, a.getFrame());
	}
	
	//method to test hasPlayedOnce in Animation
	@Test
	public void hasPlayedOnceTest(){
	// arrange
	// act 
		Animation a = new Animation();
	boolean boolexpected = false;
	//assert
	assertEquals(boolexpected, a.hasPlayedOnce());
	}
	
	//method to test isDead in Enemy
	@Test
	public void enemyTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		Foe e = new Foe(tm);
	boolean expected = false;
	//assert
	assertEquals(expected, e.isEnemyDead());
	}
	
	//method to test getDamage in Enemy
	@Test
	public void getDamageTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		Foe e = new Foe(tm);
	int intexpected = 0;
	//assert
	assertEquals(intexpected, e.getDamage());
	}
	
	//method to test intersects in MapObject
	@Test
	public void Test(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		MapObject mo = new MapObject(tm) {
		};
	boolean boolexpected = false;
	//assert
	assertEquals(boolexpected, mo.intersects(mo));
	}
	
	//method to test getX in MapObject
	@Test
	public void getXTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		MapObject mo = new MapObject(tm) {
		};
	int intexpected = 0;
	//assert
	assertEquals(intexpected, mo.getx());
	}
	
	//method to test getY in MapObject
	@Test
	public void getYTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		MapObject mo = new MapObject(tm) {
		};
	int intexpected = 0;
	//assert
	assertEquals(intexpected, mo.gety());
	}
	
	//method to test getWidth in MapObject
	@Test
	public void getWidthTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		MapObject mo = new MapObject(tm) {
		};
	int intexpected = 0;
	//assert
	assertEquals(intexpected, mo.getWidth());
	}
	
	//method to test getHeight in MapObject
	@Test
	public void getHeightTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		MapObject mo = new MapObject(tm) {
		};
	int intexpected = 0;
	//assert
	assertEquals(intexpected, mo.getHeight());
	}
	
	//method to test shouldRemove in PlayerFire
	@Test
	public void PlayerFireTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
		PlayerFire pft = new PlayerFire(tm, true) {
		};
	boolean boolexpected = false;
	//assert
	assertEquals(boolexpected, pft.shouldRemove());
	}
	
	//method to test getTileSize in TileMap
	@Test
	public void getTileSizeTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
	int intexpected = 30;
	//assert
	assertEquals(intexpected, tm.getTileSize());
	}

	//method to test getX in TileMap
	@Test
	public void getxTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
	int intexpected = 0;
	//assert
	assertEquals(intexpected, tm.getx());
	}

	//method to test getY in TileMap
	@Test
	public void getyTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
	int intexpected = 0;
	//assert
	assertEquals(intexpected, tm.gety());
	}

	//method to test getWidth in TileMap
	@Test
	public void getwidthTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
	int intexpected = 0;
	//assert
	assertEquals(intexpected, tm.getWidth());
	}
	
	//method to test getHeight in TileMap
	@Test
	public void getheightTest(){
	// arrange
	// act 
		TileMap tm = new TileMap(30);
	int intexpected = 0;
	//assert
	assertEquals(intexpected, tm.getHeight());
	}
	

}
