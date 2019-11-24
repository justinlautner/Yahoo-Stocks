import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Stocks {

    private static final String yahooURL = "https://finance.yahoo.com/quote/";

    public static void main (String[] args) throws IOException {

        System.out.print("Please enter a company symbol to see its stocks: ");
        Scanner input = new Scanner(System.in);
        final String userInput = input.next();

        getYahooStocks(userInput);

    }

    private static void getYahooStocks(String userInput) {

        try{

            String string = URLEncoder.encode(userInput, StandardCharsets.UTF_8);
            URL url = new URL(yahooURL + string + "/?p=" + string);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String decodeString;
            String stockPrice = "";
            while ((decodeString = in.readLine()) != null){

                if (decodeString.contains("\"currentPrice\":{\"raw\":")){
                    stockPrice = decodeString.substring(decodeString.lastIndexOf("\"currentPrice\":{\"raw\":"), decodeString.lastIndexOf("earningsGrowth"));
                    stockPrice = stockPrice.substring(stockPrice.lastIndexOf("\"raw\":"), stockPrice.indexOf(","));
                    stockPrice = stockPrice.substring(6);
                    System.out.println();
                    System.out.println(string + " is at " + stockPrice + ".");
                    in.close();
                    System.exit(0);
                }

            }

            System.out.println();
            System.out.println("The company symbol you have has not yielded the current stock price. Try entering another!");
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
