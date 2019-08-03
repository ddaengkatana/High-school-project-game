package spelet.game.entitites;
import spelet.game.InputHandler;
import spelet.game.gfx.Colours;
import spelet.game.gfx.Font;
import spelet.game.gfx.Screen;
import spelet.game.gfx.level.Level;
import spelet.game.gfx.level.tiles.Tile;

public class Ghoul extends Mob {
	
	private InputHandler input;
	private int colour = Colours.get(-1, 111, 275, 270);
	private int scale = 1;
	protected boolean isSwimming = false;
	private int tickCount = 0;
	private String username;
	private int score = 0;
	private String scoreCount;
	private static Player player;
	
	
	public Ghoul(Level level, int x, int y, Player player) {
	 	 super(level, "Ghoul", x, y, 0.8);
	 	 this.player = player;
	}

	


	
	public void tick() {
		int xa = 0;
		int ya = 0;
		
		xa = (int) Math.signum(Math.round(player.x/8f) - Math.round(x/8f));
		ya = (int) Math.signum(Math.round(player.y/8f) - Math.round(y/8f));
		
	 	 if (Math.abs(player.x - x) < 8 && Math.abs(player.y - y) < 8) {
	 		if (player.score > 0) {
	 			player.score -= 0.1;
	 			}
	 	 }
	 	 
	 	if (Math.abs(player.x - x) < 8 && Math.abs(player.y - y) < 8) {
	 		if (player.score > 0) {
	 		player.score -= 0.1;
	 		 	 } else {
	 		player.died();
	 		}
	 		}
	 	 
		
		if (xa != 0||ya!= 0){
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}
		
		if (level.getTile(this.x/8, this.y/8).getId() == 3){
			isSwimming = true;
		}
		if (isSwimming && level.getTile(this.x/8, this.y/8).getId() != 3){ 
			isSwimming = false;
		}
		tickCount++;
	}

	
	public void render(Screen screen) {
		int xTile = 8;
		int yTile = 28;
		int walkingSpeed = 4;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if (movingDir == 1){
			xTile += 2;
		} else if (movingDir > 1){
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}
		
		
		int modifier = 8 * scale;
		int xOffset = (int)Math.round(x - modifier/2);
		int yOffset = (int)Math.round(y - modifier/2 - 4);
		if (isSwimming){
			int waterColour = 0;
			yOffset += 4;
			if (tickCount % 60 < 15){		
				waterColour = Colours.get(-1, -1, 225, -1);
			} else if (15 <= tickCount%60 && tickCount%60 < 30){
				yOffset -=1;
				waterColour = Colours.get(-1, 225, 115, -1);
			} else if (30 <= tickCount %60 && tickCount%60 < 45){
				waterColour = Colours.get(-1, 115, -1, 225);
			} else {
				yOffset -=1;
				waterColour = Colours.get(-1, 225, 115, -1);
			}
			screen.render(xOffset, yOffset +3, 0 + 27 * 32, waterColour, 0x00, 1);
			screen.render(xOffset + 8, yOffset +3, 0 + 27 * 32, waterColour, 0x01, 1);
		}
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale);
		
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop, scale);
		
		if (!isSwimming){
			
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom, scale);
		
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier , (xTile + 1) + (yTile + 1) * 32, colour, flipBottom, scale);

		}
		if (username != null){
			Font.render(username, screen, xOffset - ((username.length() -1 )/2 * 8), yOffset - 10, Colours.get(-1, -1, -1, 555), 1);
		}
		/*if (Tile.FLOWER != null){
			Font.render(Integer.toString(score), screen, xOffset + 1, yOffset + 17, Colours.get(-1, -1, -1, 555), 1);
		}*/
	}
	
		public boolean hasCollided(int xa, int ya) {
			int xMin = 0;
			int xMax = 7;
			int yMin = 3;
			int yMax = 7;
			for (int x = xMin; x < xMax; x++){
				if(isSolidTile(xa, ya, x, yMin)){
					return true;}
				}
			for (int x = xMin; x < xMax; x++){
				if(isSolidTile(xa, ya, x, yMax)){
					return true;
				}
			}
			for (int y = yMin; y < yMax; y++){
					if(isSolidTile(xa, ya, xMin, y)){
						return true;
					}
			}
			for (int y = yMin; y < yMax; y++){
						if(isSolidTile(xa, ya, xMax, y)){
							return true;
						}
			}
			return false;
		
	}
}