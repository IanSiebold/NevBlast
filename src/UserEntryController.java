import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class UserEntryController extends Application{

    @FXML
    TextField queryNameBox;
    @FXML
    Button goButton;
    @FXML
    TextArea fastaSequenceBox;
    @FXML
    TextField sig1Box;
    @FXML
    TextField sig2Box;
    @FXML
    TextField eValueBox;
    @FXML
    Label resultLabel;
    @FXML
    Slider resultSlider;
    @FXML
    TextField taxonomyBox;
    @FXML
    Label errorTextBox;

    private final int QUERYNAME = 0;
    private final int FASTA = 1;
    private final int SIGONE = 2;
    private final int SIGTWO = 3;
    private final int TAXONOMY = 4;
    private final int EVAL = 5;
    private final int NUMRESULTS = 6;
    private final int FILESIZE = 7;

    private BlastQuery blastQuery;
    private Blaster blaster;
    private Taxonomy taxonomy;

    /**
     * This starts the UI.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UserEntry.fxml"));
        primaryStage.setTitle("Query Builder");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/default.css");
        primaryStage.getIcons().add(new Image("resources/blast mini logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method initializes the UI screen, and taxonomy features.
     */
    public void initialize(){
        taxonomy = new Taxonomy();
        blastQuery = new BlastQuery();
        blaster = new Blaster();
        TextFields.bindAutoCompletion(taxonomyBox, taxonomy.getTaxNames());
    }

    /**
     * Updates the label 🐱‍🐉🐱‍🐉🐱‍🐉🐱‍🐉🐱‍🐉
     */
    public void updateLabel(){
        int v = (int) resultSlider.getValue() * 100;
        resultLabel.setText(v + "");
    }

    public void customLabel(){
        String newText = "";
        TextInputDialog dialog = new TextInputDialog("0");
        while (newText.equals("")){
            dialog.setTitle("Text Input Dialog");
            dialog.setHeaderText("Look, a Text Input Dialog");
            dialog.setContentText("Please enter your name:");
            Optional<String> result = dialog.showAndWait();
            newText = (String) ((Optional) result).get();
        }
        resultLabel.setText(newText);
    }

    /**
     * This method initializes the blast query for BLAST API.
     */
    private void initializeBlastQuery(){
        /*blastQuery = new BlastQuery(queryNameBox.getText(), fastaSequenceBox.getText(), sig1Box.getText(), sig2Box.getText(), eValueBox.getText(), resultLabel.getText(), taxonomyBox.getText());
        errorTextBox.setText(blastQuery.getQueryName() + " " + blastQuery.getFastaSequence() + " " +
                blastQuery.getSignatureString(blastQuery.getSignatureOneBits()) + " " +
                blastQuery.getSignatureString(blastQuery.getSignatureTwoBits()) + " " +
                blastQuery.getEVal() + " " + blastQuery.getResultCount() + " " + blastQuery.getTaxonomy());
                */
        blastQuery = new BlastQuery();


        blastQuery.setFasta(fastaSequenceBox.getText());
//        System.out.println(blastQuery.getFastaSequence());

        blastQuery.setEVal(eValueBox.getText());
//        System.out.println(blastQuery.getEVal());

        blastQuery.setQueryName(queryNameBox.getText());
//        System.out.println(blastQuery.getQueryName());

        blastQuery.setResultCount(resultLabel.getText());
//        System.out.println(blastQuery.getResultCount());
        System.out.println("start");
        blastQuery.setSignatureOne(sig1Box.getText());

        blastQuery.setSignatureTwo(sig2Box.getText());


        //todo make this set the taxonomy
        blastQuery.setEntrez(taxonomyBox.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This is triggered by the Go Button. This processes the query.
     */
    public void goHandler(){
        try {
            initializeBlastQuery();
        } catch (IllegalArgumentException e) {
        errorTextBox.setText(e.getMessage());
        }

        Blaster.setBlastQuery(blastQuery);

//        try {
            Blaster.runBlast();
//        } catch (Exception e){
            //dont plot. show error
//            System.out.println("fucked");
//        }


        GraphController gc = new GraphController();

        gc.addPoints();

        try {
            gc.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if any fields are empty
     * (future versions should prompt user to enter values in empty fields)
     * @return Boolean True if all fields are empty, False otherwise.
     */
    private boolean fieldsEmpty(){
        boolean nullFlag = false;
        if(resultLabel.getText().equals(""))
            nullFlag = true;
        if(eValueBox.getText().equals(""))
            nullFlag = true;
        if (sig1Box.getText().equals(""))
            nullFlag = true;
        if (sig2Box.getText().equals(""))
            nullFlag = true;
        if (fastaSequenceBox.getText().equals(""))
            nullFlag = true;
        if (taxonomyBox.getText().equals(""))
            nullFlag = true;

        return nullFlag;
    }

    /**
     * This is triggered if the File->Load button is pressed. this saves the file to user's destination.
     * @param actionEvent
     */
    public void loadHandler(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BLAST Files", "*.blst"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            Save save = new Save();
            save.read(selectedFile);

            this.blastQuery = save.getBlastQuery();

            Blaster.setBlastQuery(save.getBlastQuery());
            Blaster.setSequenceHits(save.getSequenceHits());

            updateUI();
        } else {
            errorTextBox.setText("Selected file could not be found");
        }
    }

    /**
     * This method updates the UI after loading in a new file.
     */
    public void updateUI() {
        queryNameBox.setText(blastQuery.getQueryName());
        fastaSequenceBox.setText(blastQuery.getFastaSequence());
        eValueBox.setText(blastQuery.getEVal() + "");
        resultLabel.setText(blastQuery.getResultCount() + "");
        sig1Box.setText(blastQuery.getSignatureOneString());
        sig2Box.setText(blastQuery.getSignatureTwoString());
        taxonomyBox.setText(blastQuery.getEntrez());
    }

    /**
     * this method is triggered by File->Save button. This saves off the file to user location.
     * @param actionEvent
     */
    public void saveHandler(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BLAST Files", "*.blst"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile != null) {
                Save save = new Save(blastQuery, Blaster.getSequenceHits());
                try {
                    if (!save.write(selectedFile))
                        errorTextBox.setText("Selected file could not be saved to");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        else {
            errorTextBox.setText("Selected file is not valid");
        }
    }


    /**
     * triggered by the File->Close button. Closes the program.
     * @param actionEvent
     */
    public void closeHandler(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * This method resets the UI to what is currently stored in the BlastQuery object, if a file gets messed up.
     * @param actionEvent
     */
    public void resetHandler(ActionEvent actionEvent) {
        updateUI();
    }

    public void helpHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Help.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorTextBox.setText("Help Menu couldn't be loaded");
        }
    }
}
