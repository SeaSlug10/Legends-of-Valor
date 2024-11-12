import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        System.out.println(new File(".").getAbsolutePath());
        GamePlayer player = new GamePlayer("gameInfo.json");
    }
}
