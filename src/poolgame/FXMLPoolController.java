package poolgame;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import poolgame.helpers.Navigation;
import poolgame.helpers.UnknownStateException;
import poolgame.models.*;
import poolgame.views.CueView;
import poolgame.views.MenuView;
import poolgame.views.TableView;

public class FXMLPoolController {

    private Navigation navigation;

    // Declare models
    private Table tableModel;
    private Menu menuModel;
    private Cue cueModel;

    // Declare views
    private MenuView menuView;
    private TableView tableView;
    private CueView cueView;

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

    public void setMenu(Menu mainMenu) { this.menuModel = mainMenu; }
    public void setModel(Table model) { this.tableModel = model; }
    public void setCue(Cue cue) { this.cueModel = cue; }

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
                if(cueView == null) {
                    cueView = new CueView(cueModel);
                    table.getChildren().add(cueView);
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
                for(Ball ball : tableModel.getBalls()) {
                    if(ball.isCueBall()) {
                        double dy = ball.getCenterY()-event.getY();
                        double dx = ball.getCenterX()-event.getX();
                        double alpha = Math.atan(dy/dx);
                        if(ball.getCenterX() < event.getX()) {
                            alpha -= Math.PI;
                        }
                        cueModel.setPullBack(Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2)));
                        cueModel.setXY(event.getX(), event.getY());
                        cueModel.setAlpha(alpha);
                        cueModel.setCueBallX(ball.getCenterX());
                        cueModel.setCueBallY(ball.getCenterY());
                        cueView.update();
                    }
                }
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
    }

    private void mouseClickHandler(MouseEvent event) throws UnknownStateException {
        switch (navigation) {
            case IN_GAME:
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        for(Ball ball : tableModel.getBalls()) {
                            if(ball.isCueBall()) {
                                ball.setVelocity((cueModel.getPullBack() - 155) / 10);
                                double dy = ball.getCenterY()-(event.getY());
                                double dx = ball.getCenterX()-(event.getX());
                                double alpha = Math.atan(dy/dx);
                                if(ball.getCenterX() < event.getX()) {
                                    alpha -= Math.PI;
                                }
                                ball.setAlpha(alpha);
                                cueModel.isVisible(false);
                                cueView.update();
                            }
                        }
                        while(ballsMoving()) {
                            for(Ball ball : tableModel.getBalls()) {
                                if(ball.getVelocity() != 0) {
                                    ball.calculateTrajectory();
                                    calculateBallCollisions(ball);
                                    calculateWallCollisions(ball);
                                    ball.tick();
                                }
                            }
                            tableView.update();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {

                            }
                        }
                        cueModel.isVisible(true);
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
    }

    public boolean ballsMoving() {
        if(navigation != Navigation.IN_GAME) { return false; }

        boolean movement = false;
        for(Ball ball : tableModel.getBalls()) {
            if(ball.getVelocity() > 0) {
                movement = true;
                break;
            }
        }
        return movement;
    }

    private void calculateBallCollisions(Ball ball) {
        Ball closestBall = null;
        for(Ball ball2 : tableModel.getBalls()) {
            if (!ball2.equals(ball)) {
                if (closestBall == null) {
                    closestBall = ball2;
                }
                if (calculateDistance(ball, ball2) < calculateDistance(ball, closestBall)) {
                    closestBall = ball2;
                }
            }
        }

        double futureDistance = calculateFutureDistance(ball, closestBall);
        double distance = calculateDistance(ball, closestBall);
        System.out.println("current distance " + distance + " future distance " + futureDistance);
        if(distance <= (ball.getRadius() * 2 + (ball.getRadius() * 0.05))) {
            collide(ball, closestBall);
        } else if(futureDistance <= ball.getRadius() * 2) {
            double travel = distance - (2 * ball.getRadius());
            double travel2 = Math.sqrt(Math.pow(ball.getCenterX() + ball.getDx() - ball.getCenterX(), 2) + Math.pow(ball.getCenterY() + ball.getDy() - ball.getCenterY(), 2));

            double factor = travel / travel2;
            ball.setDx(ball.getDx() * factor);
            ball.setDy(ball.getDy() * factor);
            System.out.println("new future distance " + calculateFutureDistance(ball, closestBall));
        }
    }

    private void calculateWallCollisions(Ball ball) {
        double dx = ball.getDx();
        double dy = ball.getDy();
        double x = ball.getCenterX();
        double y = ball.getCenterY();
        double alpha = ball.getAlpha();
        double wall = 20 + ball.getRadius();
        if(Math.signum(dx) < 0 && (x+dx) < wall) {
            // Making sure that the ball hits the edge
            if(x <= wall) {
                ball.setAlpha(Math.PI - alpha);
                ball.calculateTrajectory();
            } else {
                ball.setDx(wall-x);
                ball.setDy(dy*(ball.getDx()/dx));
            }
        } else if(Math.signum(dx) > 0 && (x+dx) > Table.WIDTH-wall) {
            if(x >= Table.WIDTH-wall) {
                ball.setAlpha(Math.PI - alpha);
                ball.calculateTrajectory();
            } else {
                ball.setDx(Table.WIDTH-wall - x);
                ball.setDy(dy*(ball.getDx()/dx));
            }
        }

        if(Math.signum(dy) < 0 && (y+dy) < wall) {
            if(y <= wall) {
                ball.setAlpha(Math.PI + (Math.PI - alpha));
                ball.calculateTrajectory();
            } else {
                ball.setDy(wall-y);
                ball.setDx(dx*(ball.getDy()/dy));
            }
        }
        else if(Math.signum(dy) > 0 && (y+dy) > Table.HEIGHT - wall) {
            if(y >= Table.HEIGHT-wall) {
                ball.setAlpha(Math.PI + (Math.PI - alpha));
                ball.calculateTrajectory();
            } else {
                ball.setDy(Table.HEIGHT-wall - y);
                ball.setDx(dx*(ball.getDy()/dy));
            }
        }
    }

    private double calculateDistance(Ball ball1, Ball ball2) {
        return Math.sqrt(Math.pow(ball1.getCenterX() - ball2.getCenterX(), 2) + Math.pow(ball1.getCenterY() - ball2.getCenterY(), 2));
    }

    private double calculateFutureDistance(Ball ball1, Ball ball2) {
        return Math.sqrt(Math.pow(ball1.getCenterX() + ball1.getDx() - ball2.getCenterX(), 2) + Math.pow(ball1.getCenterY() + ball1.getDy() - ball2.getCenterY(), 2));
    }

    private void collide(Ball ball1, Ball ball2) {
        for(Ball b : tableModel.getBalls()) {
            if(b.equals(ball1)) {
                b.setVelocity(ball1.getVelocity()*0.8);
                b.setAlpha(Math.PI - ball1.getAlpha());
            }
            if(b.equals(ball2)) {
                double dy = ball2.getCenterY()-ball1.getCenterY();
                double dx = ball2.getCenterX()-ball1.getCenterX();
                double alpha = Math.atan(dy/dx);

                if(ball2.getCenterX() < ball1.getCenterX()) {
                    alpha += Math.PI;
                }

                b.setVelocity(ball1.getVelocity()*0.9);
                b.setAlpha(alpha);
            }
        }
    }
}
