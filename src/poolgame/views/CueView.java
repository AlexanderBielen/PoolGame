package poolgame.views;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import poolgame.helpers.View;
import poolgame.models.Cue;

import java.util.TimerTask;

public class CueView extends Group implements View {
    private Cue cue;
    private Rectangle cueView;
    private boolean isVisible;
    private Rotate rotation;

    public CueView(Cue cue) {
        this.cue = cue;
        this.isVisible = true;

        Image img = new Image(getClass().getResourceAsStream(cue.getImageLocation()));

        cueView = new Rectangle(0,0,cue.getLength(),cue.getWidth());
        cueView.setFill(new ImagePattern(img));

        rotation = new Rotate(0, 0, cue.getWidth()/2);
        cueView.getTransforms().add(rotation);

        getChildren().add(cueView);

        update();
    }

    @Override
    public void update() {
        cueView.setTranslateX(cue.getCueBallX() - (Math.cos(cue.getAlpha()) * (cue.getPullBack() - 150)));
        cueView.setTranslateY(cue.getCueBallY() - cue.getWidth()/2 - (Math.sin(cue.getAlpha()) * (cue.getPullBack() - 150)));
        rotation.setAngle((cue.getAlpha()*(180/Math.PI))+180);
    }
}
