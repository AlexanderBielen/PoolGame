package poolgame;

import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import poolgame.helpers.Navigation;
import poolgame.helpers.UnknownStateException;
import poolgame.models.*;
import poolgame.views.CueView;
import poolgame.views.MenuView;
import poolgame.views.TableView;

//TODO Settings page
//TODO Fix collisions
//TODO clean code


public class FXMLPoolController {
    // Properties
    private Navigation navigation;

    // Declare models
    private Table tableModel;
    private Menu menuModel;
    private Cue cueModel;

    // Declare views
    private MenuView menuView;
    private TableView tableView;
    private CueView cueView;

    private boolean gameEnded;

    @FXML
    private Button backButton;

    @FXML
    private Pane table;


    /**
     * Initialized the controller and initiates the mouse handlers
     *
     * @throws UnknownStateException when the current navigation state is uknown
     */
    private void initialize() throws UnknownStateException {
        backButton.setOnAction(this::reset);
        gameEnded = false;

        if(table.getOnMouseMoved() == null) {
            table.setOnMouseMoved(event -> {
                try {
                    if(!ballsMoving()) {
                        mouseMoveHandler(event);
                    }
                } catch (UnknownStateException ex) {

                }
            });
        }

        if(table.getOnMouseClicked() == null) {
            table.setOnMouseClicked(event -> {
                try {
                    if(!ballsMoving()) {
                        mouseClickHandler(event);
                    }
                } catch (UnknownStateException ex) {

                }
            });
        }

        try {
            updateViews();
        } catch (UnknownStateException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Sets the menuModel
     *
     * @param mainMenu the model to set
     */
    public void setMenu(Menu mainMenu) { this.menuModel = mainMenu; }

    /**
     * Sets the tableModel
     *
     * @param model the model to set
     */
    public void setModel(Table model) { this.tableModel = model; }

    /**
     * Sets the cueModel
     *
     * @param cue the model to set
     */
    public void setCue(Cue cue) { this.cueModel = cue; }

    /**
     * Updates all the views according to the current navigation
     *
     * @throws UnknownStateException when the current navigation state is unknown
     */
    public void updateViews() throws UnknownStateException {
        switch(navigation) {
            case MAIN_MENU:
                if(menuView == null) {
                    menuView = new MenuView(menuModel);
                    table.getChildren().add(menuView);
                }
                menuView.update();
                backButton.setVisible(false);
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
                backButton.setVisible(true);
                break;
            case SETTINGS:
                backButton.setVisible(true);
                break;
            case EXIT:
                Platform.exit();
                break;
            default:
                throw new UnknownStateException("Unknown navigation state");
        }
    }

    /**
     * Navigates to given navigation state
     * @param navigation state to navigate to
     * @throws UnknownStateException when the state is unknown
     */
    public void navigate(Navigation navigation) throws UnknownStateException {
        if(this.navigation != navigation) {
            clearTable();
            this.navigation = navigation;
            initialize();
        }
    }

    /**
     * Resets all views and models and clears the canvas
     */
    private void clearTable() {
        tableModel = new Table();
        cueModel = new Cue();
        menuModel = new Menu();

        tableView = null;
        cueView = null;
        menuView = null;

        gameEnded = false;

        table.getChildren().clear();
    }

    /**
     * Clears the table and navigates back to the main menu when the back button is clicked
     *
     * @param e ActionEvent of back button
     */
    public void reset(ActionEvent e){
        try {
            clearTable();
            navigate(Navigation.MAIN_MENU);
        } catch ( UnknownStateException ex) {

        }
    }
    /**
     * Handles a mouse move event
     *
     * @param event MouseEvent to use
     * @throws UnknownStateException when it enters an unknown navigation state
     */
    private void mouseMoveHandler(MouseEvent event) throws UnknownStateException {
        switch (navigation) {
            case IN_GAME:
                for(Ball ball : tableModel.getBalls()) {
                    if(ball.isCueBall()) {
                        double dy = ball.getY()-event.getY();
                        double dx = ball.getX()-event.getX();
                        double alpha = Math.atan(dy/dx);
                        if(ball.getX() < event.getX()) {
                            alpha -= Math.PI;
                        }
                        cueModel.setPullBack(Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2)));
                        cueModel.setXY(event.getX(), event.getY());
                        cueModel.setAlpha(alpha);
                        cueModel.setCueBallX(ball.getX());
                        cueModel.setCueBallY(ball.getY());
                        cueView.update();
                    }
                }
                break;
            case MAIN_MENU:
                for(MenuButton btn : menuModel.getMenuButtonList()) {
                    btn.setActive(false);
                    if(mouseIsOver(event, btn)) {
                        btn.setActive(true);
                    }
                    updateViews();
                }
                break;
            default:
                throw new UnknownStateException("Unknown navigation state");
        }
    }

    /**
     * Handles a mouse click event
     *
     * @param event MouseEvent to use
     * @throws UnknownStateException when it enters an unknown navigation state
     */
    private void mouseClickHandler(MouseEvent event) throws UnknownStateException {
        switch (navigation) {
            case IN_GAME:
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        for(Ball ball : tableModel.getBalls()) {
                            if(ball.isCueBall()) {
                                ball.setVelocity((cueModel.getPullBack() - 55) / 10);
                                double dy = ball.getY()-(event.getY());
                                double dx = ball.getX()-(event.getX());
                                double alpha = Math.atan(dy/dx);
                                if(ball.getX() < event.getX()) {
                                    alpha -= Math.PI;
                                }
                                ball.setAlpha(alpha);
                                cueModel.setVisible(false);
                                cueView.update();
                            }
                        }
                        while(ballsMoving() && !gameEnded) {
                            for(Ball ball : tableModel.getBalls()) {
                                if(ball.getVelocity() != 0 && !ball.isPocketed()) {
                                    ball.calculateTrajectory();
                                    calculateBallCollisions(ball);
                                    calculateWallCollisions(ball);
                                    ball.tick();
                                }
                            }
                            tableView.update();
                            if(getAmountOfBallsLeft() == 0) {
                                Platform.runLater(FXMLPoolController.this::showGameWonMessage);
                                gameEnded = true;
                            }
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {

                            }
                        }
                        cueModel.setVisible(true);
                    }
                };
                Thread t = new Thread(timerTask);
                t.setDaemon(true);
                t.start();
                break;
            case MAIN_MENU:
                for(MenuButton btn : menuModel.getMenuButtonList()) {
                    btn.setActive(false);
                    if(mouseIsOver(event, btn)) {
                        navigate(btn.getClickLocation());
                    }
                    updateViews();
                }
                break;
            default:
                throw new UnknownStateException("Unknown navigation state");
        }
    }

    /**
     * Checks all balls on the table if they are moving
     *
     * @return true if there are moving balls
     */
    private boolean ballsMoving() {
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

    /**
     * Calculates if given ball is colliding with any other ball
     *
     * @param ball ball to check
     */
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
        if(distance <= (ball.getRadius() * 2 + (ball.getRadius() * 0.05))) {
            collide(ball, closestBall);
        } else if(futureDistance <= ball.getRadius() * 2) {
            double travel = distance - (2 * ball.getRadius());
            double travel2 = Math.sqrt(Math.pow(ball.getX() + ball.getDx() - ball.getX(), 2) + Math.pow(ball.getY() + ball.getDy() - ball.getY(), 2));

            double factor = travel / travel2;
            ball.setDx(ball.getDx() * factor);
            ball.setDy(ball.getDy() * factor);
        }
    }

    /**
     * Checks if given ball is colliding with a wall or pocket
     *
     * @param ball ball to check
     */
    private void calculateWallCollisions(Ball ball) {
        double dx = ball.getDx();
        double dy = ball.getDy();
        double x = ball.getX();
        double y = ball.getY();
        double alpha = ball.getAlpha();
        double wall = 20 + ball.getRadius();

        if(Math.signum(dx) < 0 && (x+dx) < wall) {
            if(x <= wall) {
                //first Check if it is not a pocket
                if(checkPocket(ball)) return;
                ball.setAlpha(Math.PI - alpha);
                ball.calculateTrajectory();
            } else {
                ball.setDx(wall-x);
                ball.setDy(dy*(ball.getDx()/dx));
            }
        } else if(Math.signum(dx) > 0 && (x+dx) > Table.WIDTH-wall) {
            if(x >= Table.WIDTH-wall) {
                if(checkPocket(ball)) return;
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

    /**
     * Calculates the current distance between two balls
     *
     * @param ball1 first ball
     * @param ball2 second ball
     *
     * @return distance between ball1 and ball2
     */
    private double calculateDistance(Ball ball1, Ball ball2) {
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
    private double calculateFutureDistance(Ball ball1, Ball ball2) {
        return Math.sqrt(Math.pow(ball1.getX() + ball1.getDx() - ball2.getX(), 2) + Math.pow(ball1.getY() + ball1.getDy() - ball2.getY(), 2));
    }

    /**
     * Calculates new trajectories after the 2 given balls collide
     *
     * @param ball1 first ball
     * @param ball2 second ball
     */
    private void collide(Ball ball1, Ball ball2) {
        for(Ball b : tableModel.getBalls()) {
            if(b.equals(ball1)) {
                b.setVelocity(ball1.getVelocity()*0.8);
                b.setAlpha(Math.PI - ball1.getAlpha());
            }
            if(b.equals(ball2)) {
                double dy = ball2.getY()-ball1.getY();
                double dx = ball2.getX()-ball1.getX();
                double alpha = Math.atan(dy/dx);

                if(ball2.getX() < ball1.getX()) {
                    alpha -= Math.PI;
                }

                b.setVelocity(ball1.getVelocity()*0.9);
                b.setAlpha(alpha);
            }
        }
    }

    /**
     * Checks if given ball is in a pocket
     *
     * @param ball ball to check
     *
     * @return true if the ball is in a pocket
     */
    private boolean checkPocket(Ball ball) {
        for(Pocket p: tableModel.getPockets())
            if (p.isInPocket(ball)) {
                if (ball.isCueBall()) {
                    Platform.runLater(this::showGameLostMessage);
                    gameEnded = true;
                } else {
                    ball.setVelocity(0);
                    ball.setDx(0);
                    ball.setDy(0);
                    ball.setX(-100 + ball.getRadius() * tableModel.getPocketedBalls() * 2.5);
                    ball.setY(-100);
                    ball.setPocketed(true);
                    tableModel.setPocketedBalls(tableModel.getPocketedBalls() + 1);
                }
                return true;
            }
        return false;
    }

    /**
     * Returns the amount of balls left on the table
     *
     * @return amount of balls left en the table
     */
    private int getAmountOfBallsLeft() {
        int amount = 0;
        for(Ball ball : tableModel.getBalls()) {
            if(!ball.isPocketed() && !ball.isCueBall()) { amount++; }
        }
        return amount;
    }

    /**
     * Checks if the given mouse coordinates are within the buttons boundary's
     *
     * @param event MouseEvent that contain cursor coordinates
     * @param btn MenuButton to check
     *
     * @return true if mouse is hovering over given button
     */
    private boolean mouseIsOver(MouseEvent event, MenuButton btn) {
        return event.getX() > btn.getXPosition()
                && event.getX() < btn.getXPosition() + btn.getWidth()
                && event.getY() > btn.getYPosition()
                && event.getY() < btn.getYPosition()  + btn.getHeight();
    }

    /**
     * Shows an alert with a lose message
     */
    private void showGameLostMessage() {
        showAlert("You lost!", "You potted the cue ball..");
    }

    /**
     * Shows an alert with a win message
     */
    private void showGameWonMessage() {
        showAlert("You won!", "You potted all balls!");
    }

    /**
     * Shows an alert
     * @param title title of the alert
     * @param message content of the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
