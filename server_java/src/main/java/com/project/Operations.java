package com.project;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Operations {

    public String[][] board = new String[4][4];

    public int[][] showBoard = new int[4][4]; // 0: Don't show, 1: Show permanently, 2: Show temporarily
    
    String[] colors = {"Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Black"};

    ArrayList<String> players = new ArrayList<String>();

    String firstCol;

    int tunFlips = 2; 

    Operations(){
        ArrayList<String> colorPairs = new ArrayList<>();
        
        for (String color : colors) {
            colorPairs.add(color);
            colorPairs.add(color);
        }

        Collections.shuffle(colorPairs, new Random());

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                showBoard[i][j] = 0;
                board[i][j] = colorPairs.get(index);
                index++;
            }
        }
        
    }

    public void printBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void flipCard(String playerName, int row, int col){
        if(showBoard[row][col] == 0){
            showBoard[row][col] = 2;
            if(firstCol == null){
                firstCol = board[row][col];
            }else if(firstCol == board[row][col]){
                
            }
        }
    }
        
}
