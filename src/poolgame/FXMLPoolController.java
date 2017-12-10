package poolgame;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import poolgame.helpers.Navigation;
import poolgame.helpers.UnknownStateException;
import poolgame.models.Ball;
import poolgame.models.Menu;
import poolgame.models.MenuButton;
import poolgame.models.Table;
import poolgame.views.MenuView;
import poolgame.views.TableView;

public class FXMLPoolController {

    private Navigation navigation;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button newGame;

    @FXML
    private Label debug1;

    @FXML
    private Label debug2;

    @FXML
    private Label debug3;

    @FXML
    private Pane table;

    private void initialize() throws UnknownStateException {
        if(table.getOnMouseMoved() == null) {
            // Volgend event gevonden op https://stackoverflow.com/questions/16635514/how-to-get-location-of-mouse-in-javafx
            table.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if(!ballsMoving()) {
                            mouseMoveHandler(event);
                        }
                    } catch (UnknownStateException ex) {

                    }
                }
            });
        }

        if(table.getOnMouseClicked() == null) {
            table.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if(!ballsMoving()) {
                            mouseClickHandler(event);
                        }
                    } catch (UnknownStateException ex) {

                    }
                }
            });
        }

        try {
            updateViews();
        } catch (UnknownStateException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Declare models
    private Table tableModel;
    private Menu menuModel;

    // Declare views
    private MenuView menuView;
    private TableView tableView;

    public void setMenu(Menu mainMenu) { this.menuModel = mainMenu; }
    public void setModel(Table model) { this.tableModel = model; }

    public void updateViews() throws UnknownStateException {
        switch(navigation) {
            case MAIN_MENU:
                if(menuView == null) {
                    menuView = new MenuView(menuModel);
                    table.getChildren().add(menuView);
                }
                menuView.update();
                break;
            case IN_GAME:
                if(tableView == null) {
                    tableView = new TableView(tableModel);
                    table.getChildren().add(tableView);
                }
                tableView.update();
                break;
            case SETTINGS:
                break;
            case EXIT:
                System.exit(0);
                break;
            default:
                throw new UnknownStateException("Unknown navigation state");
        }
    }

    public void navigate(Navigation navigation) throws UnknownStateException {
        if(this.navigation != navigation) {
            table.getChildren().clear();
            this.navigation = navigation;
            initialize();
        }
    }

    private void mouseMoveHandler(MouseEvent event) throws UnknownStateException {
        switch (navigation) {
            case IN_GAME:

                break;
            case MAIN_MENU:
                for(MenuButton btn : menuModel.getButtons()) {
                    btn.setActive(false);
                    if(event.getX() > (btn.getBeginX() * MenuView.WIDTH) / 3
                            && event.getX() < ((btn.getBeginX() * MenuView.WIDTH) / 3) + btn.getWidth()
                            && event.getY() > ((btn.getBeginY() * MenuView.HEIGHT) / 10)
                            && event.getY() < ((btn.getBeginY() * MenuView.HEIGHT) / 10) + btn.getHeight()) {
                        btn.setActive(true);
                    }
                    updateViews();
                }
                break;
            default:
                throw new UnknownStateException("Unknown navigation state");
        }
        String msg =
                "(x: "       + event.getX()      + ", y: "       + event.getY()       + ") -- " +
                        "(sceneX: "  + event.getSceneX() + ", sceneY: "  + event.getSceneY()  + ") -- " +
                        "(screenX: " + event.getScreenX()+ ", screenY: " + event.getScreenY() + ")";

        //System.out.println(msg);
    }

    private void mouseClickHandler(MouseEvent event) throws UnknownStateException {
        switch (navigation) {
            case IN_GAME:
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        System.out.print("Ball shot");
                        //Set cueball velocity to something
                        for(Ball ball : tableModel.getBalls()) {
                            if(ball.isCueBall()) {
                                ball.setVelocity(10);
                                double dy = ball.getCenterY()-event.getY();
                                double dx = ball.getCenterX()-event.getX();
                                double alpha = Math.atan(dy/dx);
                                if(ball.getCenterX() < event.getX()) {
                                    alpha -= Math.PI;
                                }
                                ball.setAlpha(alpha);
                            } else {
                                ball.setAlpha(90 + Math.random());
                                ball.setVelocity(10);
                            }
                        }
                        while(ballsMoving()) {
                            for(Ball ball : tableModel.getBalls()) {
                                ball.tick();
                            }
                            tableView.update();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {

                            }
                        }

                    }
                };
                Thread t = new Thread(timerTask);
                t.setDaemon(true);
                t.start();
                break;
            case MAIN_MENU:
                for(MenuButton btn : menuModel.getButtons()) {
                    btn.setActive(false);
                    if(event.getX() > (btn.getBeginX() * MenuView.WIDTH) / 3
                            && event.getX() < ((btn.getBeginX() * MenuView.WIDTH) / 3) + btn.getWidth()
                            && event.getY() > ((btn.getBeginY() * MenuView.HEIGHT) / 10)
                            && event.getY() < ((btn.getBeginY() * MenuView.HEIGHT) / 10) + btn.getHeight()) {
                        navigate(btn.getClickLocation());
                    }
                    updateViews();
                }
                break;
            default:
                throw new UnknownStateException("Unknown navigation state");
        }
        String msg =
                "(x: "       + event.getX()      + ", y: "       + event.getY()       + ") -- " +
                        "(sceneX: "  + event.getSceneX() + ", sceneY: "  + event.getSceneY()  + ") -- " +
                        "(screenX: " + event.getScreenX()+ ", screenY: " + event.getScreenY() + ")";

        //System.out.println(msg);
    }

    public boolean ballsMoving() {
        if(navigation != Navigation.IN_GAME) { return false; }

        boolean movement = false;
        for(Ball ball : tableModel.getBalls()) {
            if(ball.getVelocity() != 0) {
                movement = true;
                break;
            }
        }
        return movement;
    }
}
