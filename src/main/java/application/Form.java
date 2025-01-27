package application;

import utils.ConfigHandler;

import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

import java.util.ArrayList;
import java.util.List;

public class Form {
    private final StackPane formView;
    private final List<VBox> formItemList;
    private Button formButton;

    public Form() {
        formItemList = new ArrayList<>();
        this.formView = this.createView();
    }

    public StackPane getView() {
        return this.formView;
    }

    public Button getFormButton() {
        return this.formButton;
    }

    public List<VBox> getFormItemList() {
        return this.formItemList;
    }

    public void validateForm() {
        // Check if values in every TextField are valid
        for (VBox formItem : formItemList) {
            String fieldName = ((Label) formItem.getChildren().getFirst()).getText();
            TextField inputField = (TextField) formItem.getChildren().get(1);

            if (inputField.getText().isEmpty()) {
                throw new IllegalArgumentException("Empty field for \"" + fieldName + "\"");
            }
            try {
                Integer.parseInt(inputField.getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid input for \"" + fieldName + "\": must be a number.");
            }
        }
    }

    public void changeConfig() {
        // Iterate through inputed values and change the config
        for (VBox vbox : this.formItemList) {
            // Get variables from form items in vbox
            String key = ((Label) vbox.getChildren().getFirst()).getText();
            int value = Integer.parseInt(((TextField) vbox.getChildren().get(1)).getText());
            // Format key to be the same as config
            key = key
                    .toUpperCase()
                    .replace(" ", "_");
            if (value != ConfigHandler.getInstance().getConfigValue(key)) {
                ConfigHandler.getInstance().changeConfig(key, value);
            }
        }
    }

    private String formatString(String text) {
        String formattedKey = text
                .toLowerCase()
                .replace("_", " ");
        String[] words = formattedKey.split("\\s");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            // Capitalize the first letter for each word
            result.append(Character.toTitleCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return result.toString().trim();
    }

    private StackPane createView() {
        // Create config input field container
        FlowPane inputContainer = new FlowPane(Orientation.VERTICAL);
        inputContainer.setHgap(20);
        inputContainer.setVgap(10);

        // Add all values in config to form
        ConfigHandler.getInstance().getConfig(true).forEach((key, value) -> {
            VBox formItem = new VBox();

            // Create form item elements
            Label label = new Label(formatString(key));
            label.setStyle("-fx-text-fill:white;");
            formItem.getChildren().add(label);

            TextField input = new TextField(value.toString());
            input.setStyle("-fx-background-color:#4A64A4; -fx-text-fill:white;");
            formItem.getChildren().add(input);

            formItemList.add(formItem);
            inputContainer.getChildren().add(formItem);
        });

        // Create startButton
        formButton = new Button();
        formButton.setStyle("-fx-background-color:#4A64A4; -fx-text-fill:white;");
        formButton.setPadding(new Insets(10, 10, 10, 10));
        formButton.setText("Show Gameboard");
        // Put the button in a container
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().add(formButton);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);

        // Create whole form container
        VBox formLayout = new VBox();
        formLayout.setMaxWidth(formLayout.getWidth());
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(10, 10, 10, 10));
        formLayout.getChildren().addAll(inputContainer, buttonContainer);

        // Show scene
        StackPane root = new StackPane(formLayout);
        root.setStyle("-fx-background-color:#282A3A");
        return root;
    }
}
