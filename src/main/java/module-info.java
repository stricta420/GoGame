module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.example.Client.Gui.WinLouseCards;
    opens org.example.Client.Gui.WinLouseCards to javafx.fxml;
    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.Client;
    opens org.example.Client to javafx.fxml;
    exports org.example.GameLogic;
    opens org.example.GameLogic to javafx.fxml;
    exports org.example.Serwer;
    opens org.example.Serwer to javafx.fxml;
    exports org.example.Player;
    opens org.example.Player to javafx.fxml;
    exports org.example.ClientSerwerComunication;
    opens org.example.ClientSerwerComunication to javafx.fxml;
    exports org.example.Bot;
    opens org.example.Bot to javafx.fxml;
    exports org.example.Client.Gui.GuiBoard;
    opens org.example.Client.Gui.GuiBoard to javafx.fxml;
    exports org.example.Client.Gui.Controler;
    opens org.example.Client.Gui.Controler to javafx.fxml;
    exports org.example.Client.Gui.Logic;
    opens org.example.Client.Gui.Logic to javafx.fxml;
}