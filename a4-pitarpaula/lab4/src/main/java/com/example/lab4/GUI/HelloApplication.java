package com.example.lab4.GUI;

import Domain.Ingredient;
import Domain.Reteta;
import com.example.lab4.Repository.*;
import com.example.lab4.Service.IngredientService;
import com.example.lab4.Service.RetetaService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    TextField textFieldIdIngredient = new TextField();
    TextField textFieldType = new TextField();

    TextField textFieldIdReteta = new TextField();
    TextField textFieldDate = new TextField();

    //TextField textFieldCakes = new TextField();

    ObservableList<Ingredient> ingrReteta = FXCollections.observableArrayList();
    ListView<Ingredient> cakesOrderedListView = new ListView<>(ingrReteta);

    @Override
    public void start(Stage stage) throws IOException, RepositoryException {



        IRepository<Ingredient> ingredientRepository = new IngredientDbRepository();
        IRepository<Reteta> retetaRepository = new RetetaDbRepository();


        IngredientService ingredientService = new IngredientService(ingredientRepository);
        RetetaService retetaService = new RetetaService(retetaRepository);

        VBox mainVerticalBox = new VBox();
        mainVerticalBox.setPadding(new Insets(10));;
        Scene scene = new Scene(mainVerticalBox, 800, 600);


        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(ingredientService.getAll());
        ListView<Ingredient> ingredientListView = new ListView<>(ingredients);
        ingredientListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                           @Override
                                           public void handle(MouseEvent mouseEvent) {
                                               Ingredient ingredient = ingredientListView.getSelectionModel().getSelectedItem();
                                               textFieldIdIngredient.setText(Integer.toString(ingredient.getID()));
                                               textFieldType.setText(ingredient.getType());
                                           }
                                       }
        );
        ingredientListView.setPadding(new Insets(10));
        mainVerticalBox.getChildren().add(ingredientListView);



        ObservableList<Reteta> retete = FXCollections.observableArrayList(retetaService.getAll());
        ListView<Reteta> retetaListView = new ListView<>(retete);
        retetaListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Reteta order = retetaListView.getSelectionModel().getSelectedItem();
                textFieldIdReteta.setText(Integer.toString(order.getID()));
                textFieldIdReteta.setText(Integer.toString(order.getTime()));
                textFieldType.setText(order.getType().toString());
                ingrReteta.setAll(order.getIngrediente());
            }
        });
        retetaListView.setPadding(new Insets(10));
        mainVerticalBox.getChildren().add(retetaListView);



        GridPane gridPaneIngredient = new GridPane();
        Label labelIdIngredient = new Label("Id_Ingredient: ");
        labelIdIngredient.setPadding(new Insets(10,0,10,0));

        Label labelType = new Label("Type: ");
        labelType.setPadding(new Insets(10,0,10,0));




        gridPaneIngredient.add(labelIdIngredient, 0, 0);
        gridPaneIngredient.add(labelType, 0, 1);
        gridPaneIngredient.add(textFieldIdIngredient, 1, 0);
        gridPaneIngredient.add(textFieldType, 1, 1);

        HBox buttonsCakeHorizontal = new HBox();

        Button buttonAddCake = new Button("Add Ingredient");
        buttonAddCake.setPadding(new Insets(5));
        buttonsCakeHorizontal.getChildren().add(buttonAddCake);



        mainVerticalBox.getChildren().add(gridPaneIngredient);
        mainVerticalBox.getChildren().add(buttonsCakeHorizontal);


        buttonAddCake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdIngredient.getText());
                    String type = textFieldType.getText();
                    ingredientService.add(id, type);
                    ingredients.add(ingredientService.find(id));


                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });



        GridPane gridPaneReteta = new GridPane();
        Label labelIdOrder = new Label("Id_Order: ");
        labelIdOrder.setPadding(new Insets(10,0,10,0));

        Label labelDate = new Label("Date: ");
        labelDate.setPadding(new Insets(10,0,10,0));

        Label labelCakes = new Label("Cakes: ");
        labelCakes.setPadding(new Insets(10,0,10,0));

        gridPaneReteta.add(labelIdOrder, 0, 0);
        gridPaneReteta.add(labelDate, 0, 1);
        gridPaneReteta.add(labelCakes, 0, 2);
        gridPaneReteta.add(textFieldIdReteta, 1, 0);
        gridPaneReteta.add(textFieldDate, 1, 1);



        gridPaneReteta.add(cakesOrderedListView, 1, 2);




        HBox buttonsOrderHorizontal = new HBox();
        buttonsOrderHorizontal.setPadding(new Insets(10));

        Button buttonAddIngredientToReteta = new Button("Add Ingredient to reteta");
        buttonAddIngredientToReteta.setPadding(new Insets(5));

        Button buttonAddReteta = new Button("Add Reteta");
        buttonAddReteta.setPadding(new Insets(5));





        buttonsOrderHorizontal.getChildren().add(buttonAddIngredientToReteta);
        buttonsOrderHorizontal.getChildren().add(buttonAddReteta);

        mainVerticalBox.getChildren().add(gridPaneReteta);
        mainVerticalBox.getChildren().add(buttonsOrderHorizontal);

        buttonAddIngredientToReteta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdIngredient.getText());
                    Ingredient ingredient = ingredientService.find(id);
                    ingrReteta.add(ingredient);
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        buttonAddReteta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdReteta.getText());
                    int time = Integer.parseInt(textFieldIdReteta.getText());
                    int type = Integer.parseInt(textFieldIdReteta.getText());
                    ArrayList<Ingredient> ingredients1 = new ArrayList<>();
                    ingrReteta.stream().forEach(ingredient -> ingredients1.add(ingredient));
                    retetaService.add(id, ingredients1);
                    retete.add(retetaService.find(id));
                    ingrReteta.clear();

                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });




        Button toateRetetele = new Button("Toate retetele ordonate");
        toateRetetele.setPadding(new Insets(5));
        toateRetetele.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int resultArea;
                    mainVerticalBox.getChildren().remove(resultArea);
                    resultArea.clear();
                    Collection<Reteta> reteta = retetaService.getAll();
                    Map<Integer, Long> cakesPerMonth = reteta.stream()
                            .collect(Collectors.groupingBy(
                                    Collectors.summingLong(order -> order.getIngrediente().size())
                            ));
                    cakesPerMonth.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed());
                    mainVerticalBox.getChildren().add(resultArea);
                    //cakesPerMonth.forEach((month, nrCakes) -> resultArea.getItems().add(month.toString() + " " + nrCakes));
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });







        GridPane gridPaneReports = new GridPane();
        gridPaneReports.add(toateRetetele, 1, 0);

        //mainVerticalBox.getChildren().add(resultArea);
        mainVerticalBox.getChildren().add(gridPaneReports);















        stage.setTitle("Ingredient Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



