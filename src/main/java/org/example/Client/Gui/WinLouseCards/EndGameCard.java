package org.example.Client.Gui.WinLouseCards;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a controler to EndGame cards - it can ether make a win card or a losue card
 */
public class EndGameCard {

    @FXML
    private ImageView endGameImageView;

    @FXML
    private Label InfoLabel;

    private String louseImagePath = "/org/example/louse_pic.png";
    public void createRightCard(boolean didPlayerWon) {
        if (didPlayerWon) {
            return;
        }
        Image endImage = new Image(getClass().getResourceAsStream(louseImagePath));
        endGameImageView.setImage(endImage);
        InfoLabel.setText("YOU LOSE");
    }


}
