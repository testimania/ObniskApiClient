import com.google.gson.Gson;
import json.classes.Product;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Worker {

    static String token;
//    static String server; // = "https://dev.sintec.club";
    static String server; // = "https://sintec.club";
//    static String port; // = "11001";
    static String port; // = "11000";
    static PropertiesInterface prop;

    static {
        prop = PropertiesDispatcher.getInstance();
        server = prop.getPropertyByName("property_server");
        port = prop.getPropertyByName("property_server_port");
            System.out.println("Worker:");
            System.out.println("Server: " + server);  System.out.println("Port: " + port); System.out.println();
    }


    public static void main(String[] args) {

        // get the actual Token
        token =  ObninskTokenSingleton.getToken();
            System.out.println( token);
//                System.exit(0);

            if (token == null){
                System.out.println(" Can't get token!");
                System.exit(0);
            }


        // now we can get product list:
        try {
            getProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        System.exit(0);
//

        // check the files to send
        if (!checkFiles()) {
            System.out.println(" Can't get files to send!");
            System.exit(0);
        }


        // send file stocks.xlsx
        sendStocks();
        // send file references.xlsx
        sendReferences();
        // send file sales.xlsx
        sendSales();
        // send file prices.xlsx
        sendPrices();


    }

    // get product list and just out to terminal and file
    private static void getProducts() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String path = "/api/products";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .GET()
                .build();

        System.out.println("Worker.getProducts():");
        System.out.println("Server: " + server);  System.out.println("Port: " + port); System.out.println("Token: " + token); System.out.println();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Integer statusCode = response.statusCode();
        String body = response.body();
        System.out.println("Products request status code: " + statusCode);
        System.out.println("Products response headers: " + response.headers().toString());
// terminal out
        // System.out.println("\n Body: " + body);

            if ( statusCode == 200 || statusCode == 201){
                // ok..
                Product[] products = new Gson().fromJson(body, Product[].class);
                // on screen:
                    System.out.println( " Products .. Length: " + products.length);
// terminal out
//                    for (int i = 0; i < products.length; i++) {
//                          System.out.println( products[i].toString());
//                    }

                // to file:
                String[] headers = {"article_number","product_name", "category_id", "category_name", "brand_id", "brand_name" };
                var f = new File("products.xlsx");
                int row = 0;
                int col = 0;
                try (var fos = new FileOutputStream(f)) {
                    var wb = new Workbook(fos, "Application", "1.0");
                    Worksheet ws = wb.newWorksheet("products");
                    // write headers:
                    for (int i = 0; i < headers.length; i++) {
                        ws.value(row, i, headers[i]);
                    }
                    // write data:
                    row = 1;
                    for ( Product p: products){
                        ws.value(row, 0, p.getArticle_number());
                        ws.value(row, 1, p.getProduct_name());
                        ws.value(row, 2, p.getCategory_id());
                        ws.value(row, 3, p.getCategory_name());
                        ws.value(row, 4, p.getBrand_id());
                        ws.value(row, 5, p.getBrand_name());
                        row++;
                    }
                    wb.finish();
                }
            } else {    // if bad status code:
                System.out.println(" Products request .. Error connection with status code: " + statusCode);
            }
    }

    private static void sendStocks() {
        System.out.println("" + System.lineSeparator() + " Begin with obninskStocks.sendStocks()" + System.lineSeparator());
        ObninskStocks obninskStocks = new ObninskStocks();
        boolean result = obninskStocks.sendStocks();
        System.out.println("" + System.lineSeparator() + "obninskStocks.sendStocks(): " + result + System.lineSeparator());
    }
    private static void sendReferences() {
        System.out.println("" + System.lineSeparator() + " Begin with  obninskReferences.sendReferences()" + System.lineSeparator());
        ObninskReferences obninskReferences = new ObninskReferences();
        boolean result = obninskReferences.sendReferences();
        System.out.println("" + System.lineSeparator() + "obninskReferences.sendReferences(): " + result + System.lineSeparator());
    }

    private static void sendSales() {
        System.out.println("" + System.lineSeparator() + " Begin with  ObninskSales.sendPSales()" + System.lineSeparator());
        ObninskSales obninskSales = new ObninskSales();
        boolean result = obninskSales.sendPSales();
        System.out.println("" + System.lineSeparator() + "ObninskSales.sendPSales(): " + result + System.lineSeparator());
    }
    private static void sendPrices() {
        System.out.println("" + System.lineSeparator() + " Begin with  obninskPriceLists.sendPrices()" + System.lineSeparator());
        ObninskPriceLists obninskPriceLists = new ObninskPriceLists();
        boolean result = obninskPriceLists.sendPrices();
        System.out.println("" + System.lineSeparator() + "obninskPriceLists.sendPrices(): " + result + System.lineSeparator());

    }




    // check the existence 4 files in directory to convert
    // TODO: 20.11.2023  - to real checking of files existence
    private static boolean checkFiles() {
        // TODO: 04.10.2023 make checking
        return true;
    }

}
