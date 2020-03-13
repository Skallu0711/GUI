import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PlanszaController {
    @FXML
    private Button przyciskStart;
    @FXML
    private Button przyciskFinish;

    int sec = 0;
    Timer timer;

    ImageView pustePole;

    ArrayList<ImageView> imageViews;

    @FXML
    GridPane gridPane;

    public void initialize() throws IOException {

        for(int i=0; i<3; i++) {
            for(int j=0;j<3;j++) {
                ColumnConstraints c = new ColumnConstraints();
                c.setMinWidth(100.00);
                RowConstraints r = new RowConstraints();
                r.setMinHeight(100.0);
                gridPane.getColumnConstraints().add(c);
                gridPane.getRowConstraints().add(r);
            }
        }

        String file= "src\\obrazek2.jpg";
        BufferedImage bufferedImage= ImageIO.read(new File(file)); // pobranie pliku

        wstawieniePustegoPola();

        imageViews=new ArrayList<>();


        for(int i=0;i<3;i++){ //dodanie do listy
            for(int j=0;j<3;j++){

                imageViews.add( new ImageView(SwingFXUtils.toFXImage(bufferedImage.getSubimage( i*200,  j*200, 200, 200),null)));
                imageViews.get(i*3+j).addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                    try {
                        onMouseClick(event);
                    }catch (IOException ex){
                        System.out.println(ex);
                    }
                });
            }
        }

        imageViews.remove(3*3-1); //usuwa ostatnie pole w puzzlach
        imageViews.add(pustePole);

        showGrid();

    }

    public void wstawieniePustegoPola () {
        pustePole = new ImageView(); //utworzenie pustego pola
        pustePole.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> //po kliknieciu, action
        {
            try {
                onMouseClick(event);
            }catch (IOException ex){
                System.out.println(ex);
            }
        });
    }

    public void showGrid(){
        int i = 0, j = 0;
        for(ImageView imageView : imageViews){
            gridPane.add(imageView, i, j);
            if(j < 2){
                j++;
            } else {
                j = 0;
                i++;
            }
        }
    }

    public void onMouseClick(MouseEvent mouseEvent) throws IOException{
        ImageView imageView=(ImageView)(mouseEvent.getSource());
        if (isNeighbour(imageView,pustePole)){
            swap(imageView);
        }
    }

    public boolean isNeighbour(ImageView imageView,ImageView blankSpace){
        int colImage=gridPane.getColumnIndex(imageView);
        int colPustePole=gridPane.getColumnIndex(blankSpace);
        int rowImage=gridPane.getRowIndex(imageView);
        int rowPustePole=gridPane.getRowIndex(blankSpace);
        if(Math.abs(colPustePole - colImage) == 1 && Math.abs(rowPustePole - rowImage) == 0
                || Math.abs(rowPustePole - rowImage) == 1 && Math.abs(colPustePole - colImage) == 0 ) {
            return true;
        }
        return false;
    }

    @FXML
    public void swap(ImageView imageView){
        ImageView pom=new ImageView();

        pom.setImage(imageView.getImage());
        imageView.setImage(pustePole.getImage());
        pustePole.setImage(pom.getImage());

        pom = imageView;
        imageView = pustePole;
        pustePole = pom;
    }


    @FXML
    public void start(ActionEvent actionEvent) { //zaczyna odliczanie
        if (actionEvent.getSource() == przyciskStart) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    sec++;
                    System.out.println(sec);
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 1000, 1000);
        }
    }

    @FXML
    public void finish(ActionEvent actionEvent) throws IOException { //konczy odliczanie, zapisuje do pliku i wyłącza okno z gra
        if(actionEvent.getSource() == przyciskFinish) {
            timer.cancel();
            FileWriter fileWriter = new FileWriter("myScore.txt", true);
            fileWriter.write("wynik: " + sec + " " + "sekund" + "\n");
            fileWriter.close();
            Stage stage = (Stage) przyciskFinish.getScene().getWindow(); //
            stage.close();
        }
    }

}
