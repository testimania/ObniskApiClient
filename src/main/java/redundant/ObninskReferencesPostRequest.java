package redundant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.classes.Reference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ObninskReferencesPostRequest {

// TODO: 30.08.2023
 /*
  Body: [{"element":2,"errors":"Запрос содежит ошибку: внешний id ТТ не в формате UUID или GUID"}]
  Error with status code: 206
 */

/*
▸sale_point_external_id - внешний ID ТТ
▸sale_point_name - название ТТ
▸sale_point_address - Адрес ТТ
▸client - ФИО контактного лица
▸sale_point_tin - ИНН ТТ
▸client_phone - телефон контактного лица
▸client_email - email контактного лица
▸trade_representative - ФИО ТП
▸trade_representative_external_id - внешний ID ТП

! ВАЖНО Все поля, кроме client, client_phone и client_email обязательны для заполнения

*/


    public static void main(String[] args) throws IOException, InterruptedException {


        String  json_l;


        List<Reference> list = new ArrayList<>();
            list.add( new Reference("eb75da6b-1d40-11e9-84aa-086266264dbc", "Алешинский С.В. ИП", "мкр.Южный, р-н Санэпидемстанции, гаражи, дом № 1", "", "623202327324",
                    "", "", "Бахарев Дмитрий Андреевич", "b09acf43-9a9b-11ed-a1b8-086266264dbc")) ;
            list.add( new Reference("69c39bd9-238e-11-02f746c6-23b7-11333", "Летцев М.В. ИП (4)", "390026, Рязанская область, г.Рязань, ул.Островского, дом № 101, стр.1", "", "622800921620",
                    "", "", "Котунова Маргарита Сергеевна", "b09acf42-9a9b-11ed-a1b8-086266264dbc")) ;
            list.add( new Reference("69c39bd9238e11-02f746c6-23b7-1155555", "Летцев М.В. ИП (4)", "390026, Рязанская область, г.Рязань, ул.Островского, дом № 101, стр.1", "", "622800921621",
                    "", "", "Котунова Маргарита Сергеевна", "b09acf42-9a9b-11ed-a1b8-086266264dbc")) ;

        System.out.println("eb75da6b-1d40-11e9-84aa-086266264dbc".length());
        System.out.println("69c39bd9-238e-11-02f746c6-23b7-11333".length());
        System.out.println("69c39bd9238e11-02f746c6-23b7-1155555".length());
//        System.exit(0);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        json_l = gson.toJson( list);
            System.out.println( json_l);
//            System.exit(0);

        HttpClient client = HttpClient.newHttpClient();

        String server = "https://dev.sintec.club";
        String port = "11001";
        String path = "/api/data/reference";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQwMjg5NzYsImlhdCI6MTY5Mzk4NTc3NiwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.EKjGAVhBPKibhr74ht4Hehb0Xv8FIIExJFkOdQyrqC4";
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
    //
}