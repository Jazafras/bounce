package bounce;

import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A Simple Game of Bounce.
 * 
 * The game has three states: StartUp, Playing, and GameOver, the game
 * progresses through these states based on the user's input and the events that
 * occur. Each state is modestly different in terms of what is displayed and
 * what input is accepted.
 * 
 * In the playing state, our game displays a moving rectangular "ball" that
 * bounces off the sides of the game container. The ball can be controlled by
 * input from the user.
 * 
 * When the ball bounces, it appears broken for a short time afterwards and an
 * explosion animation is played at the impact site to add a bit of eye-candy
 * additionally, we play a short explosion sound effect when the game is
 * actively being played.
 * 
 * Our game also tracks the number of bounces and syncs the game update loop
 * with the monitor's refresh rate.
 * 
 * Graphics resources courtesy of qubodup:
 * http://opengameart.org/content/bomb-explosion-animation
 * 
 * Sound resources courtesy of Star Wars
 * 
 * 
 * @author wallaces
 * 
 */
public class BounceGame extends StateBasedGame {
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	
	public static final String BALL_BALLIMG_RSC = "bounce/resource/ball.png";
	public static final String BALL_BROKENIMG_RSC = "bounce/resource/brokenball.png";
	public static final String PADDLEIMG_RSC = "bounce/resource/paddle.png";
	public static final String BRICK_GREENIMG_RSC = "bounce/resource/greenBrick.png";
	public static final String BRICK_BLUEIMG_RSC = "bounce/resource/blueBrick.png";
	public static final String BRICK_REDIMG_RSC = "bounce/resource/redBrick.png";
	public static final String GAMEOVER_BANNER_RSC = "bounce/resource/gameover.png";
	public static final String STARTUP_BANNER_RSC = "bounce/resource/PressSpace.png";
	public static final String BANG_EXPLOSIONIMG_RSC = "bounce/resource/explosion.png";
	public static final String BANG_EXPLOSIONSND_RSC = "bounce/resource/explosion.wav";

	public final int ScreenWidth;
	public final int ScreenHeight;

	int level;
	Ball ball;
	Paddle paddle;
	ArrayList<Bang> explosions;
	ArrayList<greenBrick> greenBrick;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public BounceGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		explosions = new ArrayList<Bang>(10);
				
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		
		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.
		ResourceManager.loadSound(BANG_EXPLOSIONSND_RSC);	

		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(BALL_BALLIMG_RSC);
		ResourceManager.loadImage(BALL_BROKENIMG_RSC);
		ResourceManager.loadImage(PADDLEIMG_RSC);
		ResourceManager.loadImage(BRICK_GREENIMG_RSC);
		ResourceManager.loadImage(BRICK_BLUEIMG_RSC);
		ResourceManager.loadImage(BRICK_REDIMG_RSC);
		ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		ResourceManager.loadImage(STARTUP_BANNER_RSC);
		ResourceManager.loadImage(BANG_EXPLOSIONIMG_RSC);
		
		level = 1;
		ball = new Ball(ScreenWidth / 2, ScreenHeight / 2, .1f, .2f);
		paddle = new Paddle(ScreenWidth / 2, ScreenHeight - 10, 0.0f, 0.0f);
		greenBrick = new ArrayList<greenBrick>();
		if (level == 1){
			for (int i = 1; i <= 4; i++) {
				greenBrick.add(new greenBrick(i * (ScreenWidth / 5), 200));
			}
		}
		else if (level == 2){
			for (int i = 1; i <= 4; i++) {
				greenBrick.add(new greenBrick(i * (ScreenWidth / 5), 100));
				greenBrick.add(new greenBrick(i * (ScreenWidth / 5), 200));
			}
		}
		else if (level == 3){
			for (int i = 1; i <= 4; i++) {
				greenBrick.add(new greenBrick(i * (ScreenWidth / 5), 50));
				greenBrick.add(new greenBrick(i * (ScreenWidth / 5), 200));
				greenBrick.add(new greenBrick(i * (ScreenWidth / 5), 350));
			}
		}
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new BounceGame("Bounce!", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	
}
