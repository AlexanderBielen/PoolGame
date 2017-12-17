package poolgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import poolgame.helpers.Navigation;
import poolgame.models.Cue;
import poolgame.models.Menu;
import poolgame.models.Table;

public class Main extends Application {
    Navigation navigation = Navigation.MAIN_MENU;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Menu mainMenu = new Menu();
        Table tableModel = new Table();
        Cue cue = new Cue();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPoolController.fxml"));
        Parent root = loader.load();
        root.setStyle("-fx-background-color: transparent;"); // solution: https://community.oracle.com/thread/3570580


        FXMLPoolController controller = loader.getController();
        controller.setModel(tableModel);
        controller.setMenu(mainMenu);
        controller.setCue(cue);
        controller.navigate(Navigation.MAIN_MENU);

        primaryStage.setTitle("Pool game");

        //Source of the background image: https://www.freepik.com/free-vector/wood-planks-texture-background-parquet-flooring_886477.htm#term=parquet&page=1&position=4
        Image img = new Image(getClass().getResourceAsStream("/poolgame/img/background.jpg"));

        primaryStage.setScene(new Scene(root, 692, 800, new ImagePattern(img)));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
