package org.example.Client.Gui.GuiBoard;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

/**
 * Singular field in GUI - its singluar place where player can place stone
 */
public class GuiField extends StackPane {
    private int x;
    private int y;

    private GuiBoard guiBoard;

    private Circle myCircle;

    public GuiField(int x, int y, GuiBoard guiBoard, int fieldSize) {
        this.x = x;
        this.y = y;
        this.guiBoard = guiBoard;
        setOnMouseClicked(event -> handleMouseClicked());

        Rectangle background = new Rectangle(fieldSize, fieldSize);
        background.setFill(Color.valueOf("#E3C588"));
        background.setStroke(Color.BLACK);
        background.setStrokeType(StrokeType.OUTSIDE);
        background.setStrokeWidth(1);


        Line line1 = new Line(0, fieldSize / 2, fieldSize, fieldSize / 2);
        Line line2 = new Line(fieldSize / 2, 0, fieldSize / 2, fieldSize);
        line1.setStroke(Color.RED);
        line2.setStroke(Color.RED);
        myCircle = new Circle(fieldSize / 2);
        myCircle.setFill(Color.TRANSPARENT);
        getChildren().addAll(background, line1, line2);
        getChildren().add(myCircle);
    }



    private void handleMouseClicked() {
        if (guiBoard.clickOnGuiField(x, y)) {
            myCircle.setFill(Color.PINK);
        }
    }

    public void addStoneToMe(Color color) {
        myCircle.setFill(color);
    }

    public void deleteStoneFromMe() {
        myCircle.setFill(Color.TRANSPARENT);
    }

}
