package redundant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.classes.Sale;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// The class to send POST request accordingly String server, String port, String path, String token and get response
public class ObninskPostRequester<T> {

    String server = "https://dev.sintec.club";
    String port = "11001";
    String path = "/api/data/sales";
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM0NTQyNDUsImlhdCI6MTY5MzQxMTA0NSwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.wmQPuPXoy6CCu0g6Q9iFCIgkMkPgfgPsm_7Dv0QnD0s";

    List<T> requestList;
    String requestJSON;

    public ObninskPostRequester(String server, String port, String path, String token) {
        this.server = server;
        this.port = port;
        this.path = path;
        this.token = token;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        ObninskPostRequester<Sale> obninskPostRequester = new ObninskPostRequester<Sale>("https://dev.sintec.club","11001","/api/data/sales","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM0NTQyNDUsImlhdCI6MTY5MzQxMTA0NSwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.wmQPuPXoy6CCu0g6Q9iFCIgkMkPgfgPsm_7Dv0QnD0s");
        obninskPostRequester.requestList = obninskPostRequester.getSaleListExample();
        obninskPostRequester.sendRequest();

    }


    public void sendRequest() throws IOException, InterruptedException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

//        requestList = (List<T>) getSaleListExample();

        requestJSON = gson.toJson( requestList);
            System.out.println( requestJSON);
            //            System.exit(0);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(requestJSON))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String body = response.body();
            System.out.println("The debug out:");
            System.out.println("Status code: " + statusCode);
            System.out.println("Headers : " + response.headers().toString());
            System.out.println("Body: " + body);
            System.out.println("The end of debug out." + System.lineSeparator());

        if ( statusCode == 200 || statusCode == 201) {
            System.out.println("  - Ok - ");
        } else if (statusCode == 401 ) {
            System.out.println(" Error with status code: " + statusCode);
            System.out.println(" The new token must be received.");
        } else {
            System.out.println(" Error with status code: " + statusCode);
        }

    }

    List<Sale> getSaleListExample(){

        List<Sale> saleList = new ArrayList<>();
//        Position[] positions;
//
//        positions =  new Position[] { new Position( "600139",1,1550)};
//        saleList.add( new Sale("494feef6-4739-11ee-b0d1-086266264dbc", "30.08.2023", "2f98d747-9192-11e0-a686-001517463040", "РОЗНИЦА", "Рязань, ул. Михайловское шоссе, дом № 3Б", "620000000016", "Мещерякова Светлана Александровна",
//                "b09acf47-9a9b-11ed-a1b8-086266264dbc", positions ));
//        positions = null;
//
//        positions =  new Position[] { new Position( "805600",2,1100), new Position( "600139",2,3105)};
//        saleList.add( new Sale("554e8ad6-4734-11ee-b0d1-086266264dbc", "30.08.2023", "9e4786ad-c402-11e9-b382-086266264dbc", "МОСНЕРУДСТРОЙ ООО", "Московская область, г.Луховицы, д.Матыра, дом № 1", "9718138065",
//                "Дьяченко Марина Евгеньевна", "b09acf44-9a9b-11ed-a1b8-086266264dbc", positions ));
//        positions = null;

        return  saleList;
    }


}