package com.dickens;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.*;


public class PrimaryController implements Initializable {




    enum DialogMode {
        PLAYER_ONE,
        PLAYER_TWO,
        GAME_NAME
    }


    public class Match implements java.io.Serializable {

        private int scoreLeft = 0;
        private int scoreRight = 0;
        private String playerOne = "Tawanda 1999";
        private String playerTwo = "Tawanda 1998";
        private String gameName = "Mortal Kombat 11";

        public String getDate() {
            return LocalDate.now().toString() + " " + LocalTime.now().toString();
        }

        public int getScoreLeft() {
            return scoreLeft;
        }

        public void setScoreLeft(int scoreLeft) {
            this.scoreLeft += scoreLeft;
        }


        public void setScoreRight(int scoreRight) {
            this.scoreRight += scoreRight;
        }



        public void decreaseScoreLeft(int scoreLeft) {

            if(this.scoreLeft <= 0)
            {
                this.scoreLeft = 0;
                return;

            }

            this.scoreLeft -= scoreLeft;
        }


        public void decreaseScoreRight(int scoreRight) {

            if(this.scoreRight <= 0)
            {
                this.scoreRight = 0;
                return;
            }

            this.scoreRight -= scoreRight;
        }



        public int getScoreRight() {
            return scoreRight;
        }


        public String getPlayerOne() {
            return playerOne;
        }

        public void setPlayerOne(String playerOne) {
            this.playerOne = playerOne;
        }

        public String getPlayerTwo() {
            return playerTwo;
        }

        public void setPlayerTwo(String playerTwo) {
            this.playerTwo = playerTwo;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

    }

    @FXML
    private Text gameName;

    @FXML
    private Text playerNameLeft, playerNameRight;

    @FXML
    private Text counterLeft;

    @FXML
    private Text counterRight;

    @FXML
    private JFXListView<HBox> savedMatchesList;

    private Match match;

    private SaveMatch saveMatch;



    @FXML
    public void onNameChangeLeft(MouseEvent mouseEvent) {

        showInputNameDialog(playerNameLeft, 0);
        System.out.println(playerNameLeft.getText());

    }

    @FXML
    public void onNameChangeRight(MouseEvent mouseEvent) {

        showInputNameDialog(playerNameRight, 1);

    }

    public void changeGameName(MouseEvent mouseEvent) {
        showInputNameDialog(gameName, 2);
    }

    private void initializeList() {

        JSONArray gameMatches = saveMatch.getMatchLists();

        boolean IsCreated = true;

        int count = 0;
        for (Object match : gameMatches) {

            count++;
            JSONObject jsonObject = (JSONObject) match;
            String labelName = count + " " + jsonObject.toString();


            Label label = new Label(labelName);
            JFXButton button = new JFXButton("x");
            JFXButton loadButton = new JFXButton("Load");
            loadButton.setStyle("-fx-background-color:  #2E86C1");
            button.setStyle("-fx-background-color:  #E74C3C");
            button.setTextFill(Paint.valueOf("WHITE"));
            button.setAlignment(Pos.CENTER_LEFT);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    final int selectedIdx = savedMatchesList.getSelectionModel().getSelectedIndex();
                    if (selectedIdx != -1) {
                        // String itemToRemove = savedMatchesList.getSelectionModel().getSelectedItem();

                        final int newSelectedIdx =
                                (selectedIdx == savedMatchesList.getItems().size() - 1)
                                        ? selectedIdx - 1
                                        : selectedIdx;

                        gameMatches.remove(selectedIdx);

                        savedMatchesList.getItems().remove(selectedIdx);
                        savedMatchesList.getSelectionModel().select(newSelectedIdx);

                    }
                }
            });


            loadButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    final int selectedIdx = savedMatchesList.getSelectionModel().getSelectedIndex();
                    if (selectedIdx != -1) {
                        // String itemToRemove = savedMatchesList.getSelectionModel().getSelectedItem();

                        final int newSelectedIdx =
                                (selectedIdx == savedMatchesList.getItems().size() - 1)
                                        ? selectedIdx - 1
                                        : selectedIdx;


                        JSONObject object = gameMatches.getJSONObject(selectedIdx);

                        int score = object.getInt("scoreLeft");
                        if(score < 10)
                            counterLeft.setText("0"+score);
                        else
                            counterLeft.setText(""+score);

                        score = object.getInt("scoreRight");
                        if(score < 10)
                            counterRight.setText("0"+score);
                        else
                            counterRight.setText(""+score);


                        playerNameLeft.setText(object.getString("playerOne"));
                        playerNameRight.setText(object.getString("playerTwo"));




                    }
                }
            });


            HBox layout = new HBox(3);
            layout.getChildren().addAll(label, button, loadButton);
            savedMatchesList.getItems().add(layout);


        }

        saveMatch.saveMatches(gameMatches);

    }


    private void updateList() {
        savedMatchesList.getItems().clear();
        initializeList();
    }

    public void refreshList(MouseEvent mouseEvent) {

        updateList();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        match = new Match();
        saveMatch = new SaveMatch();
        initializeList();

    }


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void increaseScoreRight(MouseEvent mouseEvent) {

        int score = match.getScoreRight();
        match.setScoreRight(1);
        if (score < 10)
            counterRight.setText("0" + score);
        else
            counterRight.setText(score + "");

    }

    @FXML
    public void decreaseScoreRight(MouseEvent mouseEvent) {
        match.decreaseScoreRight(1);
        counterRight.setText(match.getScoreRight() + "");
        if (match.getScoreRight() < 10)
            counterRight.setText("0"+match.getScoreRight());
        else
            counterRight.setText(""+match.getScoreRight());
    }

    @FXML
    public void increaseScoreLeft(MouseEvent mouseEvent) {
        System.out.println("Hello Left Increase");
        match.setScoreLeft(1);
        int score = match.getScoreLeft();
        if (score < 10)
            counterLeft.setText("0" + score);
        else
            counterLeft.setText(score + "");
    }

    @FXML
    public void decreaseScoreLeft(MouseEvent mouseEvent) {

        match.decreaseScoreLeft(1);

        if (match.getScoreLeft() < 10)
            counterLeft.setText("0"+match.getScoreLeft());
        else
            counterLeft.setText(""+match.getScoreLeft());
    }

    private void showInputNameDialog(Text playerNameText, int dialogMode) {
        TextField enterPlayerName = new TextField();
        JFXButton saveButton = new JFXButton("Save");
        saveButton.setStyle("-fx-background-color: #FEFEFE");
        Text inputText = new Text("Input name");
        inputText.setFill(Paint.valueOf("WHITE"));

        saveButton.setAlignment(Pos.CENTER);
        saveButton.setStyle("-fx-background-color: #FEFEFE");

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                playerNameText.setText(enterPlayerName.getText());
                if (dialogMode == DialogMode.valueOf("PLAYER_ONE").ordinal()) {
                    match.setPlayerOne(enterPlayerName.getText());
                } else if (dialogMode == DialogMode.valueOf("PLAYER_TWO").ordinal()) {
                    match.setPlayerTwo(enterPlayerName.getText());
                } else if (dialogMode == DialogMode.valueOf("GAME_NAME").ordinal()) {
                    match.setGameName(enterPlayerName.getText());
                }


            }
        });

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(App.getStage());
        VBox dialogVbox = new VBox(1);
        dialogVbox.getChildren().add(inputText);
        dialogVbox.getChildren().add(enterPlayerName);
        dialogVbox.getChildren().add(saveButton);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setPadding(new Insets(10, 50, 50, 50));
        dialogVbox.setSpacing(10);
        dialogVbox.setStyle("-fx-background-color: #373737");


        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();



    }

    public void saveMatch(MouseEvent mouseEvent) {
        saveMatch.saveMatchToFile(match);

        updateList();

    }


}
