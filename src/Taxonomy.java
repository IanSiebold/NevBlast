import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Taxonomy {

     //should make this a hash map with the scientific name as the key and taxID as the value
    //can use getKeys() to get an array of the keys and sent that to the text box

    private HashMap<String, Integer> taxonomies;

    public Taxonomy(){
        taxonomies = new HashMap<String, Integer>();
        try {
            this.initialize();
        } catch (Exception e)
        {

        }

    }

    public void initialize() throws FileNotFoundException, IOException {
        File file = new File("alphaTax.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        int id = 0;

        while ((line = br.readLine()) != null) {
            String name;
            String[] tokens = line.split("\'");

            name = tokens[0];
            taxonomies.put(name, id);
             id++;
        }
        br.close();
    }

    public Collection<String> getTaxNames(){
        return taxonomies.keySet();
    }


    public static void main(String[] args) throws Exception{
        File file = new File("alphaTax.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        int id = 0;

        while ((line = br.readLine()) != null) {
            String name;
            String[] tokens = line.split("\'");
            name = tokens[0];
            id++;
        }
        br.close();
        System.out.println("done");
    }

}
