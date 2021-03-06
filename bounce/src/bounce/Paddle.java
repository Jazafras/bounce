package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

/**
 * The Paddle class is an Entity that has a velocity (since it's moving). When
 * the Ball bounces off its surface, it temporarily displays a image with
 * cracks for a nice visual effect.
 * 
 */
 class Paddle extends Entity {

	private Vector velocity;
	private int countdown;

	public Paddle(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.PADDLEIMG_RSC));
		velocity = new Vector(vx, vy);
		countdown = 0;
	}

	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}


	public void bounce(float surfaceTangent) {
		countdown = 500;
		velocity = velocity.bounce(surfaceTangent);
	}
	
	
	/**
	 * Update the Ball based on how much time has passed...
	 * 
	 * @param delta
	 *            the number of milliseconds since the last update
	 */
	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;
			if (countdown <= 0) {
				addImageWithBoundingBox(ResourceManager
						.getImage(BounceGame.PADDLEIMG_RSC));
				removeImage(ResourceManager
						.getImage(BounceGame.PADDLEIMG_RSC));
			}
		}
	}
}
