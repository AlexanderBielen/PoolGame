package poolgame.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import poolgame.helpers.AudioPlayer;
import poolgame.helpers.Navigation;
import poolgame.helpers.Physics;
import poolgame.models.*;
import poolgame.views.CueView;
import poolgame.views.MenuView;
import poolgame.views.TableView;

import java.util.TimerTask;

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
     */
    private void initialize() {
        backButton.setOnAction(this::reset);
        gameEnded = false;

        if(table.getOnMouseMoved() == null) {
            table.setOnMouseMoved(event -> {
                if(!Physics.ballsMovingInList(tableModel.getBalls())) {
                    mouseMoveHandler(event);
                }
            });
        }

        if(table.getOnMouseClicked() == null) {
            table.setOnMouseClicked(event -> {
                if(!Physics.ballsMovingInList(tableModel.getBalls())) {
                    mouseClickHandler(event);
                }

            });
        }

        updateViews();
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
     */
    public void updateViews() {
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
            case EXIT:
                Platform.exit();
                break;
        }
    }

    /**
     * Navigates to given navigation state
     * @param navigation state to navigate to
     */
    public void navigate(Navigation navigation) {
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
        clearTable();
        navigate(Navigation.MAIN_MENU);
    }
    /**
     * Handles a mouse move event
     *
     * @param event MouseEvent to use
     */
    private void mouseMoveHandler(MouseEvent event) {
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
        }
    }

    /**
     * Handles a mouse click event
     *
     * @param event MouseEvent to use
     */
    private void mouseClickHandler(MouseEvent event) {
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

                                AudioPlayer.playCueHittingBall();

                                cueModel.setVisible(false);
                                cueView.update();
                            }
                        }
                        while(Physics.ballsMovingInList(tableModel.getBalls()) && !gameEnded) {
                            for(Ball ball : tableModel.getBalls()) {
                                if(ball.getVelocity() != 0 && !ball.isPocketed()) {
                                    ball.calculateTrajectory();
                                    Physics.calculateBallCollisions(tableModel.getBalls(), ball);

                                    if(ballHitPocket(ball)) {
                                        AudioPlayer.playBallPotted();
                                        if (ball.isCueBall()) {
                                            Platform.runLater(FXMLPoolController.this::showGameLostMessage);
                                            gameEnded = true;
                                        } else {
                                            ball.pocket(tableModel.getPocketedBalls());
                                            tableModel.setPocketedBalls(tableModel.getPocketedBalls() + 1);
                                        }
                                    } else {
                                        Physics.calculateWallCollisions(ball);
                                    }

                                    ball.tick();
                                }
                            }
                            updateViews();
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
        }
    }

    /**
     * Loops through all pockets and checks if given ball is in one of them
     *
     * @param ball to check
     * @return true if the ball is in a pocket
     */
    private boolean ballHitPocket(Ball ball) {
        for(Pocket p : tableModel.getPockets()) {
            if(p.isInPocket(ball)) {
                return true;
            }
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
     *
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
