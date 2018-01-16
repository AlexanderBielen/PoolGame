package poolgame.helpers;

import poolgame.models.Ball;
import poolgame.models.Table;

import java.util.ArrayList;

/**
 * Handles all the physics calculations
 */
public class Physics {
    /**
     * Checks all balls on the table if they are moving
     *
     * @param balls List off balls to check for moving balls
     * @return true if there are moving balls
     */
    public static boolean ballsMovingInList(ArrayList<Ball> balls) {
        boolean movement = false;
        for(Ball ball : balls) {
            if(ball.getVelocity() > 0) {
                movement = true;
                break;
            }
        }
        return movement;
    }

    /**
     * Calculates if given ball is colliding with any other ball
     *
     * @param balls list that contains all balls
     * @param ball to check
     */
    public static void calculateBallCollisions(ArrayList<Ball> balls,  Ball ball) {
        Ball closestBall = null;
        for(Ball ball2 : balls) {
            if (!ball2.equals(ball)) {
                if (closestBall == null) {
                    closestBall = ball2;
                }
                if (calculateDistance(ball, ball2) < calculateDistance(ball, closestBall)) {
                    closestBall = ball2;
                }
            }
        }

        if(closestBall == null) return;

        ball.calculateTrajectory();
        closestBall.calculateTrajectory();

        double futureDistance = calculateFutureDistance(ball, closestBall);
        double distance = calculateDistance(ball, closestBall);
        double t = simulateSmallMovement(ball, closestBall, 0.01);
        if(distance <= (ball.getRadius() * 2 + (ball.getRadius() * 0.05)) && t < distance) {
            AudioPlayer.playBallsColliding();

            ball.setVelocity(ball.getVelocity()*0.8);
            ball.setAlpha(Math.PI - ball.getAlpha());

            double dy = closestBall.getY()-ball.getY();
            double dx = closestBall.getX()-ball.getX();
            double alpha = Math.atan(dy/dx);

            if(closestBall.getX() < ball.getX()) {
                alpha -= Math.PI;
            }

            closestBall.setVelocity(ball.getVelocity()*1.1);
            closestBall.setAlpha(alpha);
        } else if(futureDistance <= ball.getRadius() * 2 && distance > t) {
            double travel = distance - (2 * ball.getRadius());
            double travel2 = Math.sqrt(Math.pow(ball.getX() + ball.getDx() - ball.getX(), 2) + Math.pow(ball.getY() + ball.getDy() - ball.getY(), 2));

            double factor = travel / travel2;
            ball.setDx(ball.getDx() * factor);
            ball.setDy(ball.getDy() * factor);
        }
    }

    /**
     * Calculates the current distance between two balls
     *
     * @param ball1 first ball
     * @param ball2 second ball
     *
     * @return distance between ball1 and ball2
     */
    private static double calculateDistance(Ball ball1, Ball ball2) {
        return Math.sqrt(Math.pow(ball1.getX() - ball2.getX(), 2) + Math.pow(ball1.getY() - ball2.getY(), 2));
    }

    /**
     * Calculates what the distance between ball1 and 2 will be in the next game tick
     *
     * @param ball1 first ball
     * @param ball2 second ball
     *
     * @return distance between ball1 and ball2
     */
    private static double calculateFutureDistance(Ball ball1, Ball ball2) {
        return Math.sqrt(Math.pow(ball1.getX() + ball1.getDx() - ball2.getX(), 2) + Math.pow(ball1.getY() + ball1.getDy() - ball2.getY(), 2));
    }

    /**
     * Simulates a small movement of ball 1 to ball 2 to test if ball 1 is moving towards ball 2
     *
     * @param ball1 ball that is moving
     * @param ball2 ball that is not moving
     * @param percentage of real move to simulate
     * @return distance between ball1 and 2 after simulation
     */
    private static double simulateSmallMovement(Ball ball1, Ball ball2, double percentage) {
        return  Math.sqrt(Math.pow(ball1.getX() + (ball1.getDx() * percentage) - (ball2.getX() + (ball2.getDx() * percentage)), 2)
                + Math.pow(ball1.getY() + (ball1.getDy()* percentage) - (ball2.getY() + (ball2.getDy() * percentage)), 2));
    }

    /**
     * Checks if given ball is colliding with a wall or pocket
     *
     * @param ball ball to check
     */
    public static void calculateWallCollisions(Ball ball) {
        double dx = ball.getDx();
        double dy = ball.getDy();
        double x = ball.getX();
        double y = ball.getY();
        double alpha = ball.getAlpha();
        double wall = 20 + ball.getRadius();

        if(Math.signum(dx) < 0 && (x+dx) < wall) {
            if(x <= wall) {
                ball.setAlpha(Math.PI - alpha);
                ball.calculateTrajectory();
                AudioPlayer.playBallHittingWall();
            } else {
                ball.setDx(wall-x);
                ball.setDy(dy*(ball.getDx()/dx));
            }
        } else if(Math.signum(dx) > 0 && (x+dx) > Table.WIDTH-wall) {
            if(x >= Table.WIDTH-wall) {
                ball.setAlpha(Math.PI - alpha);
                ball.calculateTrajectory();
                AudioPlayer.playBallHittingWall();
            } else {
                ball.setDx(Table.WIDTH-wall - x);
                ball.setDy(dy*(ball.getDx()/dx));
            }
        }

        if(Math.signum(dy) < 0 && (y+dy) < wall) {
            if(y <= wall) {
                ball.setAlpha(Math.PI + (Math.PI - alpha));
                ball.calculateTrajectory();
                AudioPlayer.playBallHittingWall();
            } else {
                ball.setDy(wall-y);
                ball.setDx(dx*(ball.getDy()/dy));
            }
        }
        else if(Math.signum(dy) > 0 && (y+dy) > Table.HEIGHT - wall) {
            if(y >= Table.HEIGHT-wall) {
                ball.setAlpha(Math.PI + (Math.PI - alpha));
                ball.calculateTrajectory();
                AudioPlayer.playBallHittingWall();
            } else {
                ball.setDy(Table.HEIGHT-wall - y);
                ball.setDx(dx*(ball.getDy()/dy));
            }
        }
    }
}
