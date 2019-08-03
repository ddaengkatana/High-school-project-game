package spelet.game.entitites;

import spelet.game.gfx.Screen;
import spelet.game.gfx.level.Level;

public abstract class Entity {
	
	public double x, y;
	protected int renderLayer = 1;
	protected Level level;
	
	public Entity(Level level) {
		init(level);
		
	}

	public final void init(Level level){
		this.level = level;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
	
	public void action(Player player) {
		
	}
	
}
