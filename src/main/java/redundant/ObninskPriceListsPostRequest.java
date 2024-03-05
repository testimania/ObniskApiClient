package redundant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.classes.PriceList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ObninskPriceListsPostRequest {

// TODO: 30.08.2023
 /*
  Body: [{"element":2,"errors":"Запрос содежит ошибку: внешний id ТТ не в формате UUID или GUID"}]
  Error with status code: 206

  - ? - not error - В цене указано дробное число вместо целого. Такой формат данных не принимается.
 */



/*
▸product_article - артикул продукта
▸sale_point_external_id - внешний ID ТТ
▸cost - базовая цена для ТТ с НДС
▸discount - максимально возможная скидка в процентах

! ВАЖНО Все поля обязательны для заполнения
В результате успешного выполнения запроса сервис вернет: STATUS 200

*/


    public static void main(String[] args) throws IOException, InterruptedException {


        String  json_l;


        List<PriceList> listPL = new ArrayList<>();
            listPL.add( new PriceList( "322543", "f5e2807a-a785-11db-bf7d-001731538e0a", 49095.02F, 0));
            listPL.add( new PriceList( "322543", "69c39bd9-238e-11-02f746c6-23b7-11", 19095.02F, 0));


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        json_l = gson.toJson( listPL);
            System.out.println( json_l);
//            System.exit(0);

        HttpClient client = HttpClient.newHttpClient();

        String server = "https://dev.sintec.club";
        String port = "11001";
        String path = "/api/data/price-lists";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM0NTQyNDUsImlhdCI6MTY5MzQxMTA0NSwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.wmQPuPXoy6CCu0g6Q9iFCIgkMkPgfgPsm_7Dv0QnD0s";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(json_l))
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



}