package poolgame.helpers;

import javafx.scene.media.AudioClip;

/**
 * Class that handles all the audio
 */
public class AudioPlayer {

    private static String ballHittingWallLocation = "/soundfx/hitting_wall.wav";

    private static String ballsCollidingLocation = "/soundfx/balls_colliding.wav";

    private static String ballBeingPottedLocation = "/soundfx/potting_ball.wav";

    private static String cueHittingBallLocation = "/soundfx/cue_hitting.wav";

    /**
     * Plays a sound a pool ball hitting a wall
     */
    public static void playBallHittingWall() {
        getClip(ballHittingWallLocation).play();
    }

    /**
     * Plays a sound of two pool balls colliding
     */
    public static void playBallsColliding() {
        getClip(ballsCollidingLocation).play();
    }

    /**
     * Plays a sound of a pool ball falling in a pocket
     */
    public static void playBallPotted() {
        getClip(ballBeingPottedLocation).play();
    }

    /**
     * Plays a sound of a cue stick hitting a pool ball
     */
    public static void playCueHittingBall() {
        getClip(cueHittingBallLocation).play();
    }

    /**
     * Returns a playable audio clip of the clip at given location
     *
     * @return AudioClip to play
     */
    private static AudioClip getClip(String location) {
        return new AudioClip(AudioPlayer.class.getClass().getResource(location).toExternalForm());
    }
}
