package bounce;

import java.util.Iterator;

import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * This state is active when the Game is being played. In this state, sound is
 * turned on, the bounce counter begins at 0 and increases until 10 at which
 * point a transition to the Game Over state is initiated. The user can also
 * control the paddle using the WAS & D keys.
 * 
 * Transitions From StartUpState
 * 
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	int bounces;
	int explosions;
	int level;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		explosions = 0;
		bounces = 0;
		level = 1;
		container.setSoundOn(true);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		BounceGame bg = (BounceGame)game;
		
		bg.ball.render(g);
		bg.paddle.render(g);
		g.drawString("Bounces: " + bounces, 10, 30);
		g.drawString("Lives: " + (5 - explosions), 10, 50);
		g.drawString("Level: " + level, 10, 70);
		for (Bang b : bg.explosions)
			b.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		BounceGame bg = (BounceGame)game;
		
		/***Cheat Codes***/
		if (input.isKeyDown(Input.KEY_1)) {
			level = 1;
		}
		
		if (input.isKeyDown(Input.KEY_2)) {
			level = 2;
		}
		
		if (input.isKeyDown(Input.KEY_3)) {
			level = 3;
		}
		
		//move paddle right
		if (input.isKeyDown(Input.KEY_A)) {
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(-.01f, 0)));
		}
		
		//move paddle left
		if (input.isKeyDown(Input.KEY_D)) {
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(+.01f, 0f)));
		}
		
		//right wall
		if (bg.paddle.getCoarseGrainedMaxX() > bg.ScreenWidth){
			//bg.ball.setCoarseGrainedMaxX(bg.ScreenWidth);
			bg.paddle.bounce(180);
		}
		
		//left wall
		else if (bg.paddle.getCoarseGrainedMinX() < 0) {
			//bg.ball.setCoarseGrainedMinX(0);
			bg.paddle.bounce(180);
		} 
		
		// bounce the ball...
		boolean bounced = false;
		boolean explode = false;
		
		//right wall
		if (bg.ball.getCoarseGrainedMaxX() > bg.ScreenWidth){
			//bg.ball.setCoarseGrainedMaxX(bg.ScreenWidth);
			bg.ball.bounce(90);
			bounced = true;
		}
		
		//left wall
		else if (bg.ball.getCoarseGrainedMinX() < 0) {
			//bg.ball.setCoarseGrainedMinX(0);
			bg.ball.bounce(90);
			bounced = true;
		} 
		
		//bottom wall
		else if (bg.ball.getCoarseGrainedMaxY() > bg.ScreenHeight){
			bg.ball.bounce(0);
			bounced = true;
			explode = true;
		}
		
		//top wall
		else if (bg.ball.getCoarseGrainedMinY() < 0) {
			bg.ball.bounce(0);
			bounced = true;
		}
		if (bounced) {
			bounces++;
		}
		if (explode){
			bg.explosions.add(new Bang(bg.ball.getX(), bg.ball.getY()));
			explosions++;
		}
		bg.ball.update(delta);
		bg.paddle.update(delta);

		// check if there are any finished explosions, if so remove them
		for (Iterator<Bang> i = bg.explosions.iterator(); i.hasNext();) {
			if (!i.next().isActive()) {
				i.remove();
			}
		}

		if (explosions >= 5) {
			((GameOverState)game.getState(BounceGame.GAMEOVERSTATE)).setUserScore(bounces);
			game.enterState(BounceGame.GAMEOVERSTATE);
		}
	}

	@Override
	public int getID() {
		return BounceGame.PLAYINGSTATE;
	}
	
}