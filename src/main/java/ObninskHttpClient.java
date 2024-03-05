import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.classes.Stock;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ObninskHttpClient<E> {

//    String server = "https://dev.sintec.club";
    String server = "https://sintec.club";
//    String port = "11001";
    String port = "11000";
    String path ;

    // interface: *******************

    // constructor:
    public ObninskHttpClient(String path) {
        this.path = path;
    }

    // main interface method
    boolean sendListToServer(List<E> list) {

        String  json_l;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        json_l = gson.toJson( list);
        // test
//            System.out.println( json_l);
//            System.exit(0);


        String token = ObninskTokenSingleton.getToken();
        if (token == null){
            System.out.println(" Error: token receiving");
            return false;
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(json_l))
                .build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(" Error: sendListToServer() ");
            e.printStackTrace();
            return false;
        }
        Integer statusCode = httpResponse.statusCode();
        // to debug only:
        String body = httpResponse.body();
            System.out.println("httpResponse debug data:");
            System.out.println(" Status code: " + statusCode);
            System.out.println(" Headers : " + httpResponse.headers().toString());
            System.out.println(" Body: " + body);
        // end of debug

        if ( statusCode == 200 || statusCode == 201){
            System.out.println("sendListToServer():  - Ok - ");
            return true;
        } else if ( statusCode == 206) {
            System.out.println("sendListToServer():  - partial errors - ");
            return true;
        } else {
            System.out.println("sendListToServer():  Error with status code: " + statusCode);
            return false;
        }
        // end of sendListToServer()
    }

    // ******** end of interface

    //test
    public static void main(String[] args) {
        ObninskHttpClient<Stock> obninskHttpClient = new ObninskHttpClient<Stock>("/api/data/stocks");

        List<Stock> stockList = new ArrayList<>();
//        stockList.add(new Stock("10.10.2023", "000039", 4));
//        stockList.add(new Stock("10.10.2023", "AFR-1004", 23));

        boolean result = obninskHttpClient.sendListToServer(stockList);
            System.out.println();
            System.out.println("obninskHttpClient.sendListToServer();: " + result);

    }

}
