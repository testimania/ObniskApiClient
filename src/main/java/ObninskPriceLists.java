import json.classes.PriceList;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ObninskPriceLists {

// TODO: 30.08.2023
 /*

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
    String server; // = "https://dev.sintec.club";
    String port; // = "11001";
    String path = "/api/data/price-lists";

    List<PriceList> list;

    {
//        server = PropertiesDispatcher.getInstance().getPropertyByName("property_server");
//        port = PropertiesDispatcher.getInstance().getPropertyByName("property_server_port");

//        list = new ArrayList<>();
//        list.add( new PriceList( "322543", "f5e2807a-a785-11db-bf7d-001731538e0a", 49095.02F, 0));
//        list.add( new PriceList( "322543", "69c39bd9-238e-11-02f746c6-23b7-11", 19095.02F, 0));
    }

    // interface: *******************
    // constructor:
    public ObninskPriceLists() {
        //
    }
    // main interface method
    public boolean sendPrices(){

        // to create List<PriceList>:
        File file = new File("prices.xlsx");
        list = getListFromFile(file);
        if ( list == null){
            System.out.println( " Error: creating prices list from file" );
            return false;
        }

        // to send List to server
        ObninskHttpClient<PriceList> obninskHttpClient = new ObninskHttpClient<PriceList>(path);
        if ( !obninskHttpClient.sendListToServer(list)){
            System.out.println(" Error: sending references list to server");
            return false;
        }

        return true;
    }
    // ******** end of interface

    // test
    public static void main(String[] args) {
        ObninskPriceLists obninskPriceLists = new ObninskPriceLists();
        boolean result = obninskPriceLists.sendPrices();
        System.out.println("obninskPriceLists.sendPrices(): " + result);
    }

/*
    private boolean sendListToServer() {

        String  json_l;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        json_l = gson.toJson( list);
        // test
            System.out.println( json_l);
            System.exit(0);


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
            System.out.println(" Error: httpClient.send() ");
            e.printStackTrace();
            return false;
        }
        Integer statusCode = httpResponse.statusCode();
        // to debug only:
            String body = httpResponse.body();
            System.out.println("Status code: " + statusCode);
            System.out.println("Headers : " + httpResponse.headers().toString());
            System.out.println("\nBody: " + body);
        // end of debug

        if ( statusCode == 200 || statusCode == 201){
            System.out.println(" - Stocks: httpClient.send()  - Ok - ");
            return true;
        } else {
            System.out.println(" Error with status code: " + statusCode);
            return false;
        }
    // end of sendListToServer()
    }

 */
    private List<PriceList> getListFromFile(File file) {
// TODO: 11.10.2023

        List<PriceList> list = null;
        // TODO: 10.10.2023


        try (InputStream is = new FileInputStream(file); ReadableWorkbook wb = new ReadableWorkbook(is);) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {

                List<PriceList> finalList = new ArrayList<>();

                rows.forEach(r -> {

                    // reading and converting of row in file!
                    // to don't read headers:
                    if (r.getRowNum()!=1) {

                        // SPECIAL CODE  - this code is special for every file!!!

                        // get  String product_article;
                        Cell product_article = r.getCell(0);
//                            System.out.println(" product_article: " + product_article);
                        String str0 = "";
                            if (product_article != null){
                                //                            str0 = r.getCellText(0);
                                str0 = product_article.getText();
                                str0 = str0.strip().toLowerCase();
                            }

                        // get String sale_point_external_id;
                        Cell sale_point_external_id = r.getCell(1);
//                            System.out.println(" sale_point_external_id:" + sale_point_external_id);
                        String str1 = "";
                            if (sale_point_external_id != null){
                                //                            str2 = r.getCellText(1).toLowerCase();
                                str1 = sale_point_external_id.getText();
                                str1 = str1.strip();
                            }

                        // get float cost;
                        Cell cost = r.getCell(2);
//                            System.out.println(" cost:" + cost);
                        var flCost = 0f;
                        if (cost!=null){
                            var bd = cost.asNumber();
                            flCost = bd.floatValue();
                        }


/*
                        BigDecimal bdCostValue = r.getCellAsNumber(2).orElse(null);

                            Float fcost;
                            if (bdCostValue != null){
                                fcost = bdCostValue.floatValue();
                            } else {
                                fcost = 0.0f;
                            }
*/

                        // get integer discount;
                        Cell discount = r.getCell(3);
//                        System.out.println();
//                            System.out.println(r.getRowNum());
//                        System.out.println(discount.getRawValue());
//                        System.out.println(discount.getType());
//                        System.out.println(discount.getValue());
//                        System.out.println();
//                            System.out.println(" discount:" + discount);
                        Integer intDiscount = 0;
                        if (discount.getValue() != null){
                            var bd = discount.asNumber();
                            intDiscount = bd.intValue();
                        }


/*
                        BigDecimal bdDiscountValue = r.getCellAsNumber(3).orElse(null);
                        if (bdDiscountValue != null){
                                discount = bdDiscountValue.intValue();
                            } else {
                                discount = 0;
                            }
*/

                        // if any of necessary cell is empty - ignore the row!
                        if ((product_article != null)&&(sale_point_external_id != null)&&(cost != null)&&(discount != null)) {
                            PriceList priceList = new PriceList(str0,str1,flCost,intDiscount);
                            finalList.add(priceList);
                        }

                        // END OF SPECIAL CODE!!!

                    }
                    // end of loop
                });
                list = finalList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    // end of getListFromFile
    }


}