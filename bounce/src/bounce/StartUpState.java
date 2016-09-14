package bounce;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This state is active prior to the Game starting. In this state, sound is
 * turned off, and the bounce counter shows '?'. The user can only interact with
 * the game by pressing the SPACE key which transitions to the Playing State.
 * Otherwise, all game objects are rendered and updated normally.
 * 
 * Transitions From (Initialization), GameOverState
 * 
 * Transitions To PlayingState
 */
class StartUpState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		BounceGame bg = (BounceGame)game;
		
		bg.ball.render(g);
		bg.paddle.render(g);
		g.drawString("Bounces: ?", 10, 30);
		g.drawString("Lives: " + 5, 10, 50);
		for (Bang b : bg.explosions)
			b.render(g);
		g.drawImage(ResourceManager.getImage(BounceGame.STARTUP_BANNER_RSC),
				225, 120);		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		BounceGame bg = (BounceGame)game;

		if (input.isKeyDown(Input.KEY_SPACE))
			bg.enterState(BounceGame.PLAYINGSTATE);	
		
		// bounce the ball...
		boolean explode = false;
		
		//bounce off paddle
		if (bg.ball.collides(bg.paddle) != null) {
			bg.ball.bounce(0);
		} 
		
		//right wall
		if (bg.ball.getCoarseGrainedMaxX() > bg.ScreenWidth){
			//bg.ball.setCoarseGrainedMaxX(bg.ScreenWidth);
			bg.ball.bounce(90);
		}
		
		//left wall
		else if (bg.ball.getCoarseGrainedMinX() < 0) {
			//bg.ball.setCoarseGrainedMinX(0);
			bg.ball.bounce(90);
		} 
		
		//bottom wall
		else if (bg.ball.getCoarseGrainedMaxY() > bg.ScreenHeight){
			bg.ball.bounce(0);
			explode = true;
		}
		
		//top wall
		else if (bg.ball.getCoarseGrainedMinY() < 0) {
			bg.ball.bounce(0);
		}
		
		if (explode){
			bg.explosions.add(new Bang(bg.ball.getX(), bg.ball.getY()));
		}
		
		bg.ball.update(delta);
		bg.paddle.update(delta);

		// check if there are any finished explosions, if so remove them
		for (Iterator<Bang> i = bg.explosions.iterator(); i.hasNext();) {
			if (!i.next().isActive()) {
				i.remove();
			}
		}
		

	}

	@Override
	public int getID() {
		return BounceGame.STARTUPSTATE;
	}
	
}