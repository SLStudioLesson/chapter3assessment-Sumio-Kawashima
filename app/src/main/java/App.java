import com.recipeapp.datahandler.CSVDataHandler;
import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.datahandler.JSONDataHandler;
import com.recipeapp.ui.RecipeUI;
import java.io.*;

public class App {

    public static void main(String[] args) {

        System.out.println("Choose the file format:");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        System.out.print("Select (1/2): ");
        String choice = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            choice = reader.readLine();

            DataHandler dataHandler;
            switch (Integer.parseInt(choice)) {
                case 2:
                    dataHandler = new JSONDataHandler();
                    break;
                default:
                    dataHandler = new CSVDataHandler();
                    break;
            }

            RecipeUI recipeUI = new RecipeUI(dataHandler);
            recipeUI.displayMenu();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}