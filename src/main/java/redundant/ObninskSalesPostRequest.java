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

public class ObninskSalesPostRequest {

// TODO: 30.08.2023
 /*
  Body: [{"element":2,"errors":"Запрос содежит ошибку: внешний id ТТ не в формате UUID или GUID"}]
  Error with status code: 206
 */

/*
В теле запроса ожидает массив с объектами.

▸id - ID продажи
▸date - дата продажи
▸sale_point_external_id - внешний ID ТТ
▸sale_point_name - название ТТ
▸sale_point_address - Адрес ТТ
▸sale_point_tin - ИНН ТТ
▸trade_representative - ФИО ТП
▸trade_representative_external_id - внешний ID ТП
▸positions - массив объектов с позициями продажи:
    ▸article_number - артикул продукта
    ▸quantity - количество проданного артикула
    ▸amount - сумма продажи артикула с НДС

! ВАЖНО Все поля обязательны для заполнения
В результате успешного выполнения запроса сервис вернет: STATUS 200
*/



    public static void main(String[] args) throws IOException, InterruptedException {


        String   jsonSales;

        List<Sale> saleList = new ArrayList<>();
//            Position[] positions;
//
//            positions =  new Position[] { new Position( "600139",1,1550)};
//            saleList.add( new Sale("494feef6-4739-11ee-b0d1-086266264dbc", "30.08.2023", "2f98d747-9192-11e0-a686-001517463040", "РОЗНИЦА", "Рязань, ул. Михайловское шоссе, дом № 3Б", "620000000016", "Мещерякова Светлана Александровна",
//                    "b09acf47-9a9b-11ed-a1b8-086266264dbc", positions ));
//            positions = null;
//
//            positions =  new Position[] { new Position( "805600",2,1100), new Position( "600139",2,3105)};
//            saleList.add( new Sale("554e8ad6-4734-11ee-b0d1-086266264dbc", "30.08.2023", "9e4786ad-c402-11e9-b382-086266264dbc", "МОСНЕРУДСТРОЙ ООО", "Московская область, г.Луховицы, д.Матыра, дом № 1", "9718138065",
//                    "Дьяченко Марина Евгеньевна", "b09acf44-9a9b-11ed-a1b8-086266264dbc", positions ));
//            positions = null;


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        jsonSales = gson.toJson( saleList);
            System.out.println( jsonSales);
//            System.exit(0);

        HttpClient client = HttpClient.newHttpClient();

        String server = "https://dev.sintec.club";
        String port = "11001";
        String path = "/api/data/sales";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM0NTQyNDUsImlhdCI6MTY5MzQxMTA0NSwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.wmQPuPXoy6CCu0g6Q9iFCIgkMkPgfgPsm_7Dv0QnD0s";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Token", token)
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(jsonSales))
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

/*


    //  POST:    api/data/sales
    static class Sale{
       String id;
       String date;
       String sale_point_external_id;
       String sale_point_name;
       String sale_point_address;
       String sale_point_tin;
       String trade_representative;
       String trade_representative_external_id;
       Position[] positions;

        public Sale(String id, String date, String sale_point_external_id, String sale_point_name, String sale_point_address, String sale_point_tin, String trade_representative, String trade_representative_external_id, Position[] positions) {
            this.id = id;
            this.date = date;
            this.sale_point_external_id = sale_point_external_id;
            this.sale_point_name = sale_point_name;
            this.sale_point_address = sale_point_address;
            this.sale_point_tin = sale_point_tin;
            this.trade_representative = trade_representative;
            this.trade_representative_external_id = trade_representative_external_id;
            this.positions = positions;
        }


   }
    static class Position{
        String article_number;
        Integer quantity;
        Integer amount;

        public Position(String article_number, Integer quantity, Integer amount) {
            this.article_number = article_number;
            this.quantity = quantity;
            this.amount = amount;
        }
    }

*/

}