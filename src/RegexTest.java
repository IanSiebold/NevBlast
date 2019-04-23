import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {
        String signature = "V3A38G193";
        Pattern pattern = Pattern.compile("[A-Z](\\d+)");
        Matcher matcher = pattern.matcher(signature);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
