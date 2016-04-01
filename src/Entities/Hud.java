package Entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//class for getting the HUD - health and ammo on screen
public class Hud {
	
	private BufferedImage image;
	private Font font;
	// gets player variable
	private Player player;
	
	
	//constructor passes through the player
	public Hud(Player p) {
		player = p;
		try {
			image = ImageIO.read(
					getClass().getResourceAsStream(
							"/HUD/player 1 hud.gif")
		);
			//set up the font displayed on the HUD
			font = new Font("Arial", Font.PLAIN, 10);
					
			
			// exception incase anything does wrong
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	// function to draw graphicsc onto the screen
	public void draw(Graphics2D g) {
		// position of image
		g.drawImage(image, 0, 5, null);
		g.setFont(font);
		//draw health
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 55, 18);
		//draw the ammo
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100 , 105, 32);
	}
	

}

