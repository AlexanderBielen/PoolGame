package poolgame.views;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import poolgame.helpers.View;
import poolgame.models.Ball;

public class BallView extends Group implements View {
    // Properties
    private Ball ball;
    private Circle ballView;

    /**
     * New instance of BallView
     *
     * @param ball the model to take data from
     */
    public BallView(Ball ball) {
        this.ball = ball;
        ballView = new Circle(0, 0, ball.getRadius());
        ballView.setFill(ball.getColor());

        getChildren().add(ballView);

        update();
    }

    /**
     * updates the ball position and moves it aside when it is pocketed
     */
    @Override
    public void update() {
        if(ball.isPocketed()) {
            ballView.setTranslateX(-100);
            ballView.setTranslateY(-100);
        } else if(!ball.isPocketed()) {
            ballView.setTranslateX((ball.getX()));
            ballView.setTranslateY((ball.getY()));
        }
    }

}
