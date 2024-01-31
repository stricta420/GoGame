package org.example.Client.Gui.GuiBoard;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GuiBoardView {

    private int size_x;
    private int size_y;
    private final int button_size = 100;


    private GridPane gridPane = new GridPane();

    private Button buttonSurrender;

    private Button buttonSkipMove;

    private HBox wholeGui = new HBox();

    public void crateGuiBoardView(int size, int size_of_square, GuiBoard guiBoard) {
        createGridPaneAndListOfGuiFields(size, size_of_square, guiBoard);
        createMiniLayout();
        wholeGui.getChildren().add(gridPane);
        wholeGui.getChildren().add(miniLayout);
        this.size_x += 50;
    }



    private ArrayList<ArrayList<GuiField>> listOfGuiFields = new ArrayList<>();

    private void createGridPaneAndListOfGuiFields(int size, int size_of_square, GuiBoard guiBoard) {
        this.size_x = size_of_square * size;
        this.size_y = this.size_x + 2 * size + 5;
        for (int x = 0; x < size; x++) {
            ArrayList<GuiField> singularLine = new ArrayList<>();
            for (int y = 0; y < size; y++) {
                GuiField field = new GuiField(x, y, guiBoard, size_of_square);
                singularLine.add(field);
                gridPane.add(field, x, y);
            }
            listOfGuiFields.add(singularLine);
        }
    }

    private VBox miniLayout;

    private void createMiniLayout() {
        this.miniLayout = new VBox();
        createFirstRowOfButtons();
        miniLayout.setAlignment(Pos.CENTER);
        miniLayout.getChildren().add(buttonsLayout);
        createLabelUnderButtons();
        this.miniLayout.getChildren().add(secondButtonsLayout);
    }

    private HBox secondButtonsLayout;

    private Button continueGameButton;
    private Button endGameButton;

    private void createLabelUnderButtons() {
        HBox buttonsLayout = new HBox();
        this.size_x += 100;
        Button continueGameButton = createStyledButton("Continue", "green");
        this.size_x += 100;
        Button endGameButton = createStyledButton("End game", "red");
        buttonsLayout.getChildren().addAll(continueGameButton, endGameButton);
        this.continueGameButton = continueGameButton;
        this.endGameButton = endGameButton;
        this.secondButtonsLayout = buttonsLayout;
    }



    private HBox buttonsLayout;
    private void createFirstRowOfButtons() {
        HBox buttonsLayout = new HBox();
        Button surrender_button = createStyledButton("Surrender", "red");
        Button skipMoveButton = createStyledButton("Skip move", "gray");
        buttonsLayout.getChildren().addAll(surrender_button, skipMoveButton);
        this.buttonSurrender = surrender_button;
        this.buttonSkipMove = skipMoveButton;
        this.buttonsLayout = buttonsLayout;
    }




    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setMinWidth(button_size);
        button.setMinHeight(button_size/2);
        button.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-border-color: white; " +
                        "-fx-font-size: 14px;"
        );
        return button;
    }

    public Button getButtonSurrender() {
        return this.buttonSurrender;
    }

    public Button getButtonSkipMove() {
        return this.buttonSkipMove;
    }

    public Button getContinueGameButton() {return this.continueGameButton;}
    public Button getEndGameButton() {return this.endGameButton;}

    public VBox getMiniLayout() {
        return miniLayout;
    }

    public int getSize_x() {
        return this.size_x;
    }

    public int getSize_y() {
        return this.size_y;
    }

    public ArrayList<ArrayList<GuiField>> getListOfGuiFields() {
        return this.listOfGuiFields;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
