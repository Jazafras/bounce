package bounce;

import java.util.ArrayList;
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
 * turned on, the bounce counter begins at 0 and increases with each brick destroyed.
 * 
 * The Lives counters begins at 3, which can be increased by pressing '4'.
 * 
 * Game Over is initiated when the lives counter reaches 0, or when level 3 is completed.
 * 
 * The player can skip around levels by pressing '1', '2', or '3'
 *  
 * The user can also control the paddle using the A & D keys.
 * 
 * Transitions From StartUpState
 * 
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	int lives;
	int bounces;
	int explosions;
	int level;
	int paddleCol;
	int xTime;
	int yTime;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//bg.ball.setVelocity(new Vector(.1f, .2f));
		paddleCol=0;
		xTime=0;
		yTime=0;
		lives = 3;
		explosions = 0;
		bounces = 0;
		container.setSoundOn(true);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
		
		Graphics g) throws SlickException {
		BounceGame bg = (BounceGame)game;
		//bg.greenBrick.clear();
		bg.ball.render(g);
		bg.paddle.render(g);
		g.drawString("Bounces: " + bounces, 10, 30);
		g.drawString("Lives: " + lives, 10, 50);
		g.drawString("Level: " + level, 10, 70);
		for (Bang b : bg.explosions)
			b.render(g);
		for (greenBrick b : bg.greenBrick){
			b.render(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		BounceGame bg = (BounceGame)game;
		ArrayList<greenBrick> destroy = new ArrayList<greenBrick>();
		
		/***Cheat Codes***/
		if (input.isKeyDown(Input.KEY_1)) {
			level = 1;
			//destroy current bricks
			for (greenBrick b : bg.greenBrick){
					destroy.add(b);
			}
			for (greenBrick b : destroy){
				bg.greenBrick.remove(b);
			}
			//reset ball position
			bg.ball.setPosition(bg.ScreenWidth / 2, bg.ScreenHeight / 2);
			//generate level 1 bricks
			for (int i = 1; i <= 4; i++) {
				bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 200));
			}
			bg.ball.setVelocity(new Vector(.1f, .2f));
		}
		
		if (input.isKeyDown(Input.KEY_2)) {
			level = 2;
			//destroy current bricks
			for (greenBrick b : bg.greenBrick){
					destroy.add(b);
			}
			for (greenBrick b : destroy){
				bg.greenBrick.remove(b);
			}
			//reset ball position
			bg.ball.setPosition(bg.ScreenWidth / 2, bg.ScreenHeight / 2);
			//generate level 2 bricks
			for (int i = 1; i <= 4; i++) {
				bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 100));
				bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 200));
			}
			bg.ball.setVelocity(new Vector(.2f, .3f));
		}
		
		if (input.isKeyDown(Input.KEY_3)) {
			level = 3;
			//destroy current bricks
			for (greenBrick b : bg.greenBrick){
					destroy.add(b);
			}
			for (greenBrick b : destroy){
				bg.greenBrick.remove(b);
			}
			//reset ball position
			bg.ball.setPosition(bg.ScreenWidth / 2, bg.ScreenHeight / 2);
			//generate level 3 bricks
			for (int i = 1; i <= 4; i++) {
				bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 50));
				bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 200));
				bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 350));
			}
			bg.ball.setVelocity(new Vector(.3f, .4f));
			
		}
		
		if (input.isKeyDown(Input.KEY_4)) {
			lives += 400;
		}
		
		/***control the paddle****/
		//move paddle right
		if (input.isKeyDown(Input.KEY_A)) {
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(-.01f, 0)));
		}
		
		//move paddle left
		if (input.isKeyDown(Input.KEY_D)) {
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(+.01f, 0f)));
		}
		
		//paddle right wall
		if (bg.paddle.getCoarseGrainedMaxX() > bg.ScreenWidth){
			//bg.paddle.setCoarseGrainedMaxX(bg.ScreenWidth);
			bg.paddle.bounce(0);
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(-.01f, 0f)));
		}
		
		//paddle left wall
		else if (bg.paddle.getCoarseGrainedMinX() < 0) {
			//bg.paddle.setCoarseGrainedMinX(0);
			bg.paddle.bounce(0);
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(+.01f, 0)));
		} 
		
		/***bounce the ball***/
		boolean bounced = false;
		boolean explode = false;
		
		//bounce off paddle
		if (bg.ball.collides(bg.paddle) != null && paddleCol <= 0) {
			bg.ball.bounce(0);
			paddleCol = 10;
		}
		paddleCol--;
		
		//brick collisions
		
		//bounce off and destroy a green brick
		for (greenBrick b : bg.greenBrick){
			if (bg.ball.collides(b) != null) {
				bg.ball.bounce(0);
				bounced = true;
				destroy.add(b);
			}
		} 
		
		for (greenBrick b : destroy){
			bg.greenBrick.remove(b);
		}
		
		//right wall
		if (bg.ball.getCoarseGrainedMaxX() > bg.ScreenWidth && xTime <= 0){
			xTime = 10;
			bg.ball.setCoarseGrainedMaxX(bg.ScreenWidth);
			bg.ball.bounce(90);
		}
		
		//left wall
		else if (bg.ball.getCoarseGrainedMinX() < 0 && xTime <= 0) {
			xTime = 10;
			bg.ball.setCoarseGrainedMinX(0);
			bg.ball.bounce(90);
		} 
		
		//bottom wall
		else if (bg.ball.getCoarseGrainedMaxY() > bg.ScreenHeight && yTime <= 0){
			//bg.ball.setCoarseGrainedMaxY(bg.ScreenHeight);
			yTime = 10;
			bg.ball.bounce(0);
			bounced = true;
			explode = true;
		}
		
		//top wall
		else if (bg.ball.getCoarseGrainedMinY() < 0 && yTime <= 0) {
			yTime = 10;
			bg.ball.setCoarseGrainedMinY(0);
			bg.ball.bounce(0);
		}
		xTime--;
		yTime--;
		
		if (bounced) {
			bg.explosions.add(new Bang(bg.ball.getX(), bg.ball.getY()));
			bounces++;
		}
		if (explode){
			bg.explosions.add(new Bang(bg.ball.getX(), bg.ball.getY()));
			explosions++;
			lives--;
		}
		bg.ball.update(delta);
		bg.paddle.update(delta);

		// check if there are any finished explosions, if so remove them
		for (Iterator<Bang> i = bg.explosions.iterator(); i.hasNext();) {
			if (!i.next().isActive()) {
				i.remove();
			}
		}

		
		//increment levels
		if (bg.greenBrick.size() == 0){
			if (level < 4){
				level++;
			}
			if (level == 2){
				for (int i = 1; i <= 4; i++) {
					bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 100));
					bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 200));
				}
				bg.ball.setVelocity(bg.ball.getVelocity().scale(1.5f));
			}
			else if (level == 3) {
				for (int i = 1; i <= 4; i++) {
					bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 100));
					bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 200));
					bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 350));
				}
				bg.ball.setVelocity(bg.ball.getVelocity().scale(1.2f));
			}
		}
		
		if (lives == 0 || level == 4) {
			((GameOverState)game.getState(BounceGame.GAMEOVERSTATE)).setUserScore(bounces);
			((GameOverState)game.getState(BounceGame.GAMEOVERSTATE)).setUserLevel(level);
			//reset ball position
			bg.ball.setPosition(bg.ScreenWidth / 2, bg.ScreenHeight / 2);
			//reset paddle position
			bg.paddle.setPosition(bg.ScreenWidth / 2, bg.ScreenHeight - 10);
			//reset paddle velocity
			bg.paddle.setVelocity(new Vector(0f, 0f));
			if (level == 4){
				level = 1;
				//generate level 1 bricks
				for (int i = 1; i <= 4; i++) {
					bg.greenBrick.add(new greenBrick(i * (bg.ScreenWidth / 5), 200));
				}
				bg.ball.setVelocity(new Vector(.1f, .2f));
			}
			game.enterState(BounceGame.GAMEOVERSTATE);
		}
	}

	@Override
	public int getID() {
		return BounceGame.PLAYINGSTATE;
	}
	
}