import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MenuController {
    @FXML
    private Button przyciskGraj;
    @FXML
    private Button przyciskWyniki;


    public void nextScene(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource() == przyciskGraj) {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("plansza.fxml"));
            primaryStage.setTitle("Puzzle Game");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    public void odczyt(ActionEvent actionEvent) throws  IOException{ //nowe okno z zapisanymi wczesniej wynikami
        if(actionEvent.getSource() == przyciskWyniki) {
            Pane pane=new Pane();
            Stage window = new Stage();
            Scene scene = new Scene(pane, 600, 600);
            pane.setMaxSize(600,600);
            window.setTitle("Tabela Wynikow");
            window.setScene(scene);
            TextArea textArea=new TextArea();
            pane.getChildren().add(textArea);
            textArea.setPrefSize(600,600);
            window.show();

            FileReader fileReader = new FileReader("myScore.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String pom; // zmienna do odczytu linijka po linijce
            while ((pom = bufferedReader.readLine())!=null){
                textArea.appendText(pom+"\n");
            }
            fileReader.close();
        }
    }






}
