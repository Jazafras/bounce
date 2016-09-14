package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

/**
 * The Brick class is an Entity that is stationary. When
 * the Ball bounces off a brick, it destroys or weakens
 * the brick, depending on its level, and bounces the ball
 */
 class greenBrick extends Entity {

	public greenBrick(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.BRICK_GREENIMG_RSC));

	}

	/**
	 * Bounce the ball off a surface. This simple implementation, combined
	 * with the test used when calling this method can cause "issues" in
	 * some situations. Can you see where/when? If so, it should be easy to
	 * fix!
	 * 
	 * @param surfaceTangent
	 */
	public void bounce(float surfaceTangent) {
		removeImage(ResourceManager.getImage(BounceGame.BRICK_GREENIMG_RSC));
	}

	/**
	 * Update the Ball based on how much time has passed...
	 * 
	 * @param delta
	 *            the number of milliseconds since the last update
	 
	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;
			if (countdown <= 0) {
				addImageWithBoundingBox(ResourceManager
						.getImage(BounceGame.BALL_BALLIMG_RSC));
				removeImage(ResourceManager
						.getImage(BounceGame.BALL_BROKENIMG_RSC));
			}
		}
	}*/
}

class blueBrick extends Entity {
	public blueBrick(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.BRICK_BLUEIMG_RSC));

	}

	/**
	 * Bounce the ball off a surface. This simple implementation, combined
	 * with the test used when calling this method can cause "issues" in
	 * some situations. Can you see where/when? If so, it should be easy to
	 * fix!
	 * 
	 * @param surfaceTangent
	 */
	public void bounce(float surfaceTangent) {
		removeImage(ResourceManager.getImage(BounceGame.BRICK_BLUEIMG_RSC));
	}

	/**
	 * Update the Ball based on how much time has passed...
	 * 
	 * @param delta
	 *            the number of milliseconds since the last update
	 
	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;
			if (countdown <= 0) {
				addImageWithBoundingBox(ResourceManager
						.getImage(BounceGame.BALL_BALLIMG_RSC));
				removeImage(ResourceManager
						.getImage(BounceGame.BALL_BROKENIMG_RSC));
			}
		}
	}*/
}

class redBrick extends Entity {
	public redBrick(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.BRICK_REDIMG_RSC));

	}

	/**
	 * Bounce the ball off a surface. This simple implementation, combined
	 * with the test used when calling this method can cause "issues" in
	 * some situations. Can you see where/when? If so, it should be easy to
	 * fix!
	 * 
	 * @param surfaceTangent
	 */
	public void bounce(float surfaceTangent) {
		removeImage(ResourceManager.getImage(BounceGame.BRICK_REDIMG_RSC));
	}

	/**
	 * Update the Ball based on how much time has passed...
	 * 
	 * @param delta
	 *            the number of milliseconds since the last update
	 
	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;
			if (countdown <= 0) {
				addImageWithBoundingBox(ResourceManager
						.getImage(BounceGame.BALL_BALLIMG_RSC));
				removeImage(ResourceManager
						.getImage(BounceGame.BALL_BROKENIMG_RSC));
			}
		}
	}*/
}