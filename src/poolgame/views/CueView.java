package poolgame.views;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import poolgame.helpers.View;
import poolgame.models.Cue;

public class CueView extends Group implements View {
    private Cue cue;
    private Rectangle cueView;
    private boolean isVisible;
    private Rotate rotation;

    public CueView(Cue cue) {
        this.cue = cue;
        this.isVisible = true;

        cueView = new Rectangle(0,0,cue.getLength(),cue.getWidth());
        cueView.setFill(Color.BROWN);

        rotation = new Rotate(0);
        cueView.getTransforms().add(rotation);

        getChildren().add(cueView);
        update();
    }

    @Override
    public void update() {
        cueView.setTranslateX(cue.getCueBallX()+20);
        cueView.setTranslateY(cue.getCueBallY()-cue.getWidth()/2);
        rotation.setAngle((cue.getAlpha()*(180/Math.PI))+180);
        rotation.setPivotX(-20);
    }
}
