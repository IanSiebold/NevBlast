import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.forester.phylogeny.data.Sequence;

import java.io.*;
import java.net.MalformedURLException;

public class GraphController extends Application {
    private SequenceHits sequenceHits;

    private SequenceHit[] hits = Blaster.getSequenceHits().toArray();

    @FXML
    BrowserView browserView;
    @FXML
    AnchorPane testPane;
    @FXML
    TableView tableView;
    @FXML
    TableColumn nameC;
    @FXML
    TableColumn xC;
    @FXML
    TableColumn yC;
    @FXML
    TableColumn zC;
    @FXML
    Label sig1;
    @FXML
    Label sig2;
    @FXML
    Label raw1;
    @FXML
    Label raw2;
    @FXML
    Label norm1;
    @FXML
    Label norm2;
    @FXML
    Label hitSeq;
    @FXML
    Label eVal;
    @FXML
    Label vEss;

    public static void main(){
        launch();
    }

    public void start(Stage stage) throws IOException {
        BrowserPreferences.setChromiumSwitches("--disable-web-security", "--allow-file-access-from-files", "--allow-file-access");
        Parent root = FXMLLoader.load(getClass().getResource("Graph.fxml"));
        stage.setTitle("Nev Blast");
        stage.getIcons().add(new Image("resources/blast mini logo.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/default.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initialize() throws MalformedURLException {
        File file = new File("src/graphHTML.html");
        browserView.getBrowser().loadURL(file.toURI().toURL().toString());
        fillTable();
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    SequenceHit selectedItem = (SequenceHit) tableView.getSelectionModel().getSelectedItem();
                    populatePane(selectedItem);
                }
            }
        });
    }

    public void fillTable() {
        nameC.setCellValueFactory(new PropertyValueFactory<>("hitSequence"));
        xC.setCellValueFactory(new PropertyValueFactory<>("scoreOne"));
        yC.setCellValueFactory(new PropertyValueFactory<>("scoreTwo"));
        zC.setCellValueFactory(new PropertyValueFactory<>("eValue"));
        for(SequenceHit h: hits){
            tableView.getItems().add(h);
        }
    }

    public void addPoints() {
        try {
            FileWriter fw = new FileWriter("src/data.csv");

            StringBuilder print = new StringBuilder();

            print.append("x1,y1,z1\n");
            for (SequenceHit h : hits) {
                print.append(h.scoreOne);
                print.append(",");
                print.append(h.scoreTwo);
                print.append(",");
                print.append(h.eValue);
                print.append("\n");
            }

            fw.write(print.toString());
            fw.close();
        } catch (IOException e){
            //TODO let the user know something is fucked
        }
    }

    private void populatePane(SequenceHit hit){
        sig1.setText(hit.getSigOneMatch());
        sig2.setText(hit.getSigTwoMatch());
        raw1.setText(hit.scoreOne + "");
        raw2.setText(hit.scoreTwo + "");
        norm1.setText(hit.getNormalizedScoreOne() + "");
        norm2.setText(hit.getNormalizedScoreTwo() + "");
        hitSeq.setText(hit.hitSequence);
        eVal.setText(hit.eValue + "");
        vEss.setText(hit.accession);
    }

}
