package redundant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.classes.Stock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ObninskStocksPostRequest {

//    This Java program uses HTTP Client API to submit form data in a JSON format(application/json).

    public static void main(String[] args) throws IOException, InterruptedException {

//  POST:    api/data/stocks

        String  jsonStocks;

        List<Stock> stockList = new ArrayList<>();
            stockList.add(new Stock( "30.08.2023", "000039", 4 )) ;
            stockList.add(new Stock( "30.08.2023", "AFR-1004", 23 )) ;
            stockList.add(new Stock( "30.08.2023", "А-9", 9 )) ;
            stockList.add(new Stock( "30.08.2023", "100100", 1 )) ;
            stockList.add(new Stock( "30.08.2023", "SNF-TR82SET-B", 6 )) ;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        jsonStocks = gson.toJson( stockList);
            System.out.println( jsonStocks);
//            System.exit(0);

        HttpClient client = HttpClient.newHttpClient();

        String server = "https://dev.sintec.club";
        String port = "11001";
        String path = "/api/data/stocks";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM0NTQyNDUsImlhdCI6MTY5MzQxMTA0NSwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.wmQPuPXoy6CCu0g6Q9iFCIgkMkPgfgPsm_7Dv0QnD0s";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(jsonStocks))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Integer statusCode = response.statusCode();
        String body = response.body();
            System.out.println("Status code: " + statusCode);
            System.out.println("Headers : " + response.headers().toString());
            System.out.println("\nBody: " + body);

        if ( statusCode == 200 || statusCode == 201){
        // run..
            System.out.println("  - Ok - ");
        } else {
            System.out.println(" Error with status code: " + statusCode);
        }
    }
    //
}