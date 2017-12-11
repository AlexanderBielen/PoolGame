package poolgame.views;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import poolgame.helpers.View;
import poolgame.models.Cue;

public class CueView extends Group implements View {
    private Cue cue;
    private Rectangle cueView;
    private boolean isVisible;

    public CueView(Cue cue) {
        this.cue = cue;
        this.isVisible = true;

        cueView = new Rectangle(0,0,100,10);
        cueView.setFill(Color.BROWN);

        getChildren().add(cueView);

        update();
    }

    @Override
    public void update() {
        cueView.setTranslateX(cue.getX());
        cueView.setTranslateY(cue.getY());
    }

    public void isVisible(boolean isVis) {
        this.isVisible = isVis;
    }
}
