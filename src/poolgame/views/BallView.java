package poolgame.views;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import poolgame.helpers.View;
import poolgame.models.Ball;

public class BallView extends Group implements View {
    private Ball ball;
    private Circle ballView;



    public BallView(Ball ball) {
        this.ball = ball;
        ballView = new Circle(0, 0, ball.getRadius());
        ballView.setFill(ball.getColor());

        getChildren().add(ballView);

        update();
    }

    @Override
    public void update() {
        ballView.setTranslateX((ball.getCenterX()));
        ballView.setTranslateY((ball.getCenterY()));
    }

}
