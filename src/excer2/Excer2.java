
package excer2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class Excer2 extends Application {

    private ListView<String> listViewSource, listViewDest;
    private TextField textFieldName;
    private CheckBox checkBoxAll;
    private RadioButton radioButtonGold, radioButtonCyan;
    private Button buttonAdd, buttonDel, buttonUpdate, buttonCopy, buttonClear;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String names[] = {"Ahmad", "Huda"};
        listViewSource = new ListView(
                FXCollections.observableArrayList(names)
        );
        listViewSource.getSelectionModel().selectedItemProperty()
                .addListener(e
                        -> {
                    textFieldName.setText(listViewSource.getSelectionModel().getSelectedItem());
                }
                );
        listViewSource.setPrefSize(200, 250);
        listViewDest = new ListView();
        listViewDest.setPrefSize(200, 250);
        listViewSource.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewDest.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        HBox hBox1 = new HBox(10, listViewSource, listViewDest);
        hBox1.setAlignment(Pos.CENTER);

        listViewDest.getSelectionModel().selectedItemProperty()
                .addListener(e
                        -> textFieldName.setText(listViewDest.getSelectionModel().getSelectedItem())
                );

        textFieldName = new TextField();

        checkBoxAll = new CheckBox("Select All");
        checkBoxAll.setIndeterminate(true);

        checkBoxAll.setOnAction(e -> {
            if (checkBoxAll.isSelected()) {
                listViewSource.getSelectionModel().selectAll();

            } else {
                listViewSource.getSelectionModel().clearSelection();
            }
        });

        radioButtonGold = new RadioButton("Gold");
        radioButtonCyan = new RadioButton("Cyan");
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtonGold.setToggleGroup(toggleGroup);
        radioButtonCyan.setToggleGroup(toggleGroup);
        HBox hBox2 = new HBox(20, radioButtonGold, radioButtonCyan);
        hBox2.setAlignment(Pos.CENTER);

        buttonAdd = new Button("Add");
        buttonDel = new Button("Delete");
        buttonUpdate = new Button("Update");
        buttonCopy = new Button("Copy");
        buttonClear = new Button("Clear");
        MyEventHandler myEventHandler = new MyEventHandler();
        buttonAdd.setOnAction(myEventHandler);
        buttonDel.setOnAction(myEventHandler);
        buttonUpdate.setOnAction(myEventHandler);
        buttonCopy.setOnAction(myEventHandler);
        buttonClear.setOnAction(myEventHandler);
        HBox hBox3 = new HBox(15, buttonAdd, buttonDel, buttonUpdate, buttonCopy, buttonClear);
        hBox3.setAlignment(Pos.CENTER);

        VBox vBox1 = new VBox(15, hBox1, textFieldName, checkBoxAll, hBox2, hBox3);
        vBox1.setPadding(new Insets(20));
        vBox1.setAlignment(Pos.CENTER);
        radioButtonGold.setOnAction(e
                -> vBox1.setStyle("-fx-background-color:gold")
        );
        radioButtonCyan.setOnAction(e
                -> vBox1.setStyle("-fx-background-color:cyan")
        );

        FlowPane flowPane = new FlowPane(vBox1);
        flowPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(flowPane, 500, 650);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Exe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class MyEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String buttonName = ((Button) event.getSource()).getText();
            switch (buttonName) {
                case "Add":
                    if (textFieldName.getText().trim() != null
                            && !textFieldName.getText().trim().equalsIgnoreCase("")) {
                        listViewSource.getItems().add(textFieldName.getText().trim());
                    }
                    textFieldName.clear();
                    break;
                case "Delete":
                    if (listViewSource.getSelectionModel().
                            isSelected(listViewSource.getSelectionModel().getSelectedIndex())) {
                        listViewSource.getItems().remove(listViewSource.getSelectionModel().getSelectedIndex());
                        checkBoxAll.selectedProperty().set(false);
                    }
                    textFieldName.clear();

                    break;
                case "Update":
                    if (listViewSource.getSelectionModel()
                            .isSelected(listViewSource.getSelectionModel().getSelectedIndex())) {
                        listViewSource.getItems().set(
                                listViewSource.getSelectionModel().getSelectedIndex(),
                                textFieldName.getText().trim()
                        );
                        textFieldName.clear();

                    }

                    break;
                case "Copy":
                    if (listViewSource.getSelectionModel()
                            .isSelected(listViewSource.getSelectionModel().getSelectedIndex())) {
                        listViewDest.getItems().addAll(
                                listViewSource.getSelectionModel().getSelectedItems());
                        textFieldName.clear();

                    }
                    break;
                case "Clear":
                    listViewSource.getItems().clear();
                    listViewDest.getItems().clear();
                    checkBoxAll.selectedProperty().set(false);
                    break;

            }

        }
    }

}
