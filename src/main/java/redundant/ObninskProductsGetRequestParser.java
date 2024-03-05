package redundant;

import com.google.gson.Gson;
import json.classes.Product;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// not necessary class anymore...

public class ObninskProductsGetRequestParser {

//    Java HttpClient POST Request JSON Example - Send JSON Data
//    This Java program uses HTTP Client API to submit form data in a JSON format(application/json).

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String server = "https://dev.sintec.club";
        String port = "11001";
        String path = "/api/products";
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM0NTQyNDUsImlhdCI6MTY5MzQxMTA0NSwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.wmQPuPXoy6CCu0g6Q9iFCIgkMkPgfgPsm_7Dv0QnD0s";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQwMjg5NzYsImlhdCI6MTY5Mzk4NTc3NiwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.EKjGAVhBPKibhr74ht4Hehb0Xv8FIIExJFkOdQyrqC4";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
//                .header("Content-Type", "application/json")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Integer statusCode = response.statusCode();
        String body = response.body();
            System.out.println("Status code: " + statusCode);
            System.out.println("Headers : " + response.headers().toString());
//            System.out.println("\n Body: " + body);

        if ( statusCode == 200 || statusCode == 201){
        // run..
            Product[] products = new Gson().fromJson(body, Product[].class);
            System.out.println( " Length: " + products.length);
            for (int i = 0; i < products.length; i++) {
                System.out.println( products[i].toString());
            }

        } else {
            System.out.println(" Error connection with status code: " + statusCode);
        }
    }
   //
}