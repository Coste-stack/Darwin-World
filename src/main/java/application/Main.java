package application;

import utils.ConfigHandler;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class Main extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set stage properties
        primaryStage.setWidth(ConfigHandler.getInstance().getConfigValue("SCREEN_WIDTH"));
        primaryStage.setHeight(ConfigHandler.getInstance().getConfigValue("SCREEN_HEIGHT"));

        // Create starting menu - formView
        Form form = new Form();
        StackPane root = form.getView();
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Run simulation on button click - create boardView
        form.getFormButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    form.validateForm();
                    form.changeConfig();
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.show();
                    return;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.show();
                    return;
                }

                Simulation simulation = new Simulation();
                StackPane root = simulation.getView();
                simulation.run();

                Scene mainScene = new Scene(root);
                primaryStage.setScene(mainScene);
            }
        });
    }
}