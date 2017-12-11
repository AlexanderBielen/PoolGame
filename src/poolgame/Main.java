package poolgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import poolgame.helpers.Navigation;
import poolgame.models.Cue;
import poolgame.models.Menu;
import poolgame.models.Table;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Menu mainMenu = new Menu();
        Table tableModel = new Table();
        Cue cue = new Cue();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPoolController.fxml"));
        Parent root = loader.load();

        FXMLPoolController controller = loader.getController();
        controller.setModel(tableModel);
        controller.setMenu(mainMenu);
        controller.setCue(cue);
        controller.navigate(Navigation.MAIN_MENU);

        primaryStage.setTitle("Pool game");
        primaryStage.setScene(new Scene(root, 550, 740));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
