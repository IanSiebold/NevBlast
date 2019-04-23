import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController {

@FXML
RadioButton aboutHelpButton;

@FXML
RadioButton queryHelpButton;

@FXML
RadioButton graphHelpButton;

@FXML
TextArea helpText;

private ToggleGroup toggleGroup;

private final int QUERY = 0;
private final int GRAPH = 1;
private final int ABOUT = 2;

private boolean init = false;

private final String QUERY_TEXT = "Query Entry\n" +
        "_______________________________________________________________\n" +
        "\n" +
        "Query Name: Name user associates with query. \n" +
        "\n" +
        "FASTA Sequence: Amino Acid to send to the BLAST API.\n" +
        "This is made of letters, and can't contain B, J, O, X, Y, or Z.\n" +
        "\n" +
        "Signatures: User created sequence of letter and count pairs\n" +
        " that should be contained in the amino acid. For example:\n" +
        "\t\t\n" +
        "\t\tR 4 E 3\n" +
        "Delineates that the string should have 4 copies of R, and \n" +
        "3 copies of E.\n" +
        "SIGNATURE ONE IS REQUIRED, SIGNATURE TWO IS NOT.\n" +
        "\n" +
        "Taxonomy: Type the first few letters, and then select one from\n" +
        "the dropdown menu that matches what you are looking for.\n" +
        "\n" +
        "E Value: This is a value betwen 0.0 and 0.1, with a linear slope.\n" +
        "\n" +
        "Number Of Results: This field delineates how many results the \n" +
        "Blast query will return to you.\n" +
        "\n" +
        "Go Button: This processes the query and prints out the graph\n" +
        " screen.\n" +
        "\n" +
        "_______________________________________________________________\n" +
        "\n" +
        "File Menu\n" +
        "\n" +
        "Load: Load file of .blst format\n" +
        "\n" +
        "Save: Save your query using the .blst file\n" +
        "\n" +
        "Close: Close the window.";

    private final String GRAPH_TEXT = "This needs to be implemented";
    private final String ABOUT_TEXT = "A little bit of nev, a little bit of blast.\n\n" +
            "Project: NevBLAST\nAuthors: Tyler Gottlieb, John O'Brien, Ian Siebold, and Eric Albrecht\n" +
            "RIP Eric. On a new project, but not forgotten.";

    public void setButtons(int option) {
        queryHelpButton.setSelected(QUERY==option);
        graphHelpButton.setSelected(GRAPH==option);
        aboutHelpButton.setSelected(ABOUT==option);
    }


    public void queryHelpHandler(ActionEvent actionEvent) {
        setButtons(QUERY);
        helpText.setText(QUERY_TEXT);
    }

    public void graphHelpHandler(ActionEvent actionEvent) {
        setButtons(GRAPH);
        helpText.setText(GRAPH_TEXT);
    }



    public void aboutHelpHandler(ActionEvent actionEvent) {
        setButtons(ABOUT);
        helpText.setText(ABOUT_TEXT);
    }
}
