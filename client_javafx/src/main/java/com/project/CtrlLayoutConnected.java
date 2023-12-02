package com.project;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CtrlLayoutConnected {

     @FXML
    private Label torn; 

    @FXML
    private Label espera; 

    @FXML
    private GridPane grid;

    private AnchorPane[][] anchorPanes;
    private int rows = 4;
    private int cols = 4;

    AppData appData;


    public void initialize() {
        anchorPanes = new AnchorPane[rows][cols];
        // Set new selection (or deselect)
        appData = AppData.getInstance();
        
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setStyle("-fx-background-color: gray;");
                anchorPanes[row][col] = anchorPane;

                configureAnchorPaneClick(anchorPane,  row,  col);
                grid.add(anchorPane, col, row); 
            }
        }
    }
    
    private void configureAnchorPaneClick(AnchorPane anchorPane, final int row, final int col) {
        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clic en la posición (" + row + ", " + col + ")");
                appData.flipCard(row, col);
            }
        });
    }

    public void setColorForAnchorPane(int row, int col, String color) {
    if (row >= 0 && row < rows && col >= 0 && col < cols) {
        AnchorPane anchorPane = anchorPanes[row][col];
        anchorPane.setStyle("-fx-background-color: " + color + ";");
    }
}
    
    @FXML
    private void handleDisconnect(ActionEvent event) {
        AppData appData = AppData.getInstance();
        appData.disconnectFromServer();
    }

    @FXML
    private void handleSend(ActionEvent event) {
        AppData appData = AppData.getInstance();
    }

    public void updateInfo() {
        AppData appData = AppData.getInstance();
    }

    public void updateMessages(String messages) {
    }

}
