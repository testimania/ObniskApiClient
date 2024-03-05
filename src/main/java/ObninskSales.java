import json.classes.Position;
import json.classes.Sale;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObninskSales {

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


//    String server = "https://dev.sintec.club";
//    String port = "11001";
    String path = "/api/data/sales";

    List<Sale> list;

    {
//        list = new ArrayList<>();
//        Position[] positions;
//
//        positions =  new Position[] { new Position( "600139",1,1550)};
//        list.add( new Sale("494feef6-4739-11ee-b0d1-086266264dbc", "30.08.2023", "2f98d747-9192-11e0-a686-001517463040", "РОЗНИЦА", "Рязань, ул. Михайловское шоссе, дом № 3Б", "620000000016", "Мещерякова Светлана Александровна",
//                "b09acf47-9a9b-11ed-a1b8-086266264dbc", positions ));
//        positions = null;
//
//        positions =  new Position[] { new Position( "805600",2,1100), new Position( "600139",2,3105)};
//        list.add( new Sale("554e8ad6-4734-11ee-b0d1-086266264dbc", "30.08.2023", "9e4786ad-c402-11e9-b382-086266264dbc", "МОСНЕРУДСТРОЙ ООО", "Московская область, г.Луховицы, д.Матыра, дом № 1", "9718138065",
//                "Дьяченко Марина Евгеньевна", "b09acf44-9a9b-11ed-a1b8-086266264dbc", positions ));
//        positions = null;
    }

    // interface: *******************
    // constructor:
    public ObninskSales() {
        //
    }
    // main interface method
    public boolean sendPSales(){

        // to create List<PriceList>:
        File file = new File("sales.xlsx");
        list = getListFromFile(file);
        if ( list == null){
            System.out.println( " Error: creating prices list from file" );
            return false;
        }
//        System.out.println(list.toString());
//        list.stream().forEach(System.out::println);

        // TODO: 20.11.2023 свертка списка по продаже
        list = getAggregationList( list);

//        System.exit(0);

        // to send List to server
        ObninskHttpClient obninskHttpClient = new ObninskHttpClient("/api/data/sales");
        if ( !obninskHttpClient.sendListToServer(list)){
            System.out.println(" Error: sending prices list to server");
            return false;
        }
        return true;
    }

    private List<Sale> getAggregationList( List<Sale> saleList) {
        // TODO: 20.11.2023
        List<Sale> aggregatedSaleList = new ArrayList<>();
        Map<String, List<Sale>> listMap = saleList.stream().collect(Collectors.groupingBy(Sale::getId));
//            System.out.println(listMap);
            System.out.println("Before Agg size:" + saleList.size());
            System.out.println("After Agg size:" + listMap.size());

        // обходим мапу заполняя агрегатный лист:
        for ( Map.Entry entry: listMap.entrySet()
             ) {
            Position p;
            // base Sale with one Position
            Sale base_s;
            Sale s;
            List<Position> positionList;
//                System.out.println(entry);                System.out.println("key: " + entry.getKey());                System.out.println("value: " + entry.getValue());                System.out.println("value: " + entry.getValue().getClass());
            List<Sale> l = (List<Sale>) entry.getValue();
//            System.out.println(l.size());            System.out.println(l.get(0));
            base_s = l.get(0);
            positionList = base_s.getPositions();
//            System.out.println(s.getPositions());
            p = base_s.getPositions().get(0);
//            System.out.println("Position: " + p);
//            System.out.println();

            if (l.size() > 1){
                for (int i = 1; i < l.size(); i++) {
                    // sale with Position to aggregate
                    s = l.get(i);
                    p = s.getPositions().get(0);
                    positionList.add(p);
                }
                base_s.setPositions(positionList);
            }
            aggregatedSaleList.add(base_s);
        }

        System.out.println("aggregatedSaleList Size: " + aggregatedSaleList.size());

//        return saleList;
        return aggregatedSaleList;
    }
    // ******** end of interface

    // test
    public static void main(String[] args) {
        ObninskSales obninskPriceLists = new ObninskSales();
        boolean result = obninskPriceLists.sendPSales();
//        System.out.println("sendPrices.sendListToServer(): " + result);
    }

    private List<Sale> getListFromFile(File file) {

        List<Sale> list = null;
// TODO: 12.10.2023 -> sales

        try (InputStream is = new FileInputStream(file); ReadableWorkbook wb = new ReadableWorkbook(is);) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {

                List<Sale> finalList = new ArrayList<>();

                Set<Sale> saleSet = new HashSet<>();

                rows.forEach(r -> {

                    // reading and converting of row in file!
                    // to don't read headers:
                    if (r.getRowNum()!=1) {

                        // SPECIAL CODE  - this code is special for every file!!!


                        // get String id;
                        Cell id = r.getCell(0);
//                            System.out.println(" id:" + id);
                        String str0 = "";
                        if (id != null){
                            str0 = id.getText();
                            str0 = str0.strip();
                        }

                        // get String date;
                        Cell date = r.getCell(1);
//                            System.out.println(" id:" + date);
                        String str1 = "";
                        if (date != null){
                            str1 = date.getText();
                            str1 = str1.strip();
                        }

                        // get String sale_point_external_id;
                        Cell sale_point_external_id = r.getCell(2);
//                            System.out.println(" sale_point_external_id:" + sale_point_external_id);
                        String str2 = "";
                        if (sale_point_external_id != null){
                            str2 = sale_point_external_id.getText();
                            str2 = str2.strip();
                        }

                        // get  String sale_point_name;
                        Cell sale_point_name = r.getCell(3);
//                            System.out.println(" sale_point_name: " + sale_point_name);
                        String str3 = "";
                            if (sale_point_name != null){
                                str3 = sale_point_name.getText();
                                str3 = str3.strip();
                            }

                        // get  String sale_point_address;
                        Cell sale_point_address = r.getCell(4);
//                        System.out.println(" sale_point_address: " + sale_point_address);
                        String str4 = "";
                        if (sale_point_address != null){
                            str4 = sale_point_address.getText();
                            str4 = str4.strip();
                        }

                        // get String sale_point_tin;
                        Cell sale_point_tin = r.getCell(5);
//                        System.out.println(" sale_point_tin:" + sale_point_tin);
                        String str5 = "";
                        if (sale_point_tin != null){
                            str5 = sale_point_tin.getText();
                            str5 = str5.strip();
                        }

                        // get String trade_representative;
                        Cell trade_representative = r.getCell(6);
//                        System.out.println(" trade_representative:" + trade_representative);
                        String str6 = "";
                        if (trade_representative != null){
                            str6 = trade_representative.getText();
                            str6 = str6.strip();
                        }

                        // get String trade_representative_external_id;
                        Cell trade_representative_external_id = r.getCell(7);
//                        System.out.println(" trade_representative_external_id:" + trade_representative_external_id);
                        String str7 = "";
                        if (trade_representative_external_id != null){
                            str7 = trade_representative_external_id.getText();
                            str7 = str7.strip();
                        }


// TODO: 12.10.2023 get position !!!!
                        // get String article_number;
                        Cell article_number = r.getCell(8);
//                        System.out.println(" Obj:" + article_number);
                        String str8 = "";
                        if (article_number != null){
                            str8 = article_number.getText().strip().toLowerCase();
                        }
                        // get integer quantity;
                        Cell quantity = r.getCell(9);
//                        System.out.println(" quantity:" + quantity);
                        Integer intQuantity= 0;
                        if (quantity != null){
                            var bd = quantity.asNumber();
                            intQuantity = bd.intValue();
                        }
                        // get integer amount;
                        Cell amount = r.getCell(10);
//                        System.out.println(" amount:" + amount);
                        Integer intAmount= 0;
                        if (quantity != null){
                            var bd = amount.asNumber();
                            intAmount = bd.intValue();
                        }
                        Position p = new Position(str8,intQuantity, intAmount);
                        List<Position> positionList = new ArrayList<>();
                        positionList.add(p);

                        // if any of necessary cell is empty - ignore the row!
                        if ((id != null)&&(date != null)&&(sale_point_external_id != null)&&(sale_point_name != null)&&(sale_point_address != null)&&(sale_point_tin != null)&&(trade_representative != null)&&(trade_representative_external_id != null)) {
                            // TODO: 12.10.2023 normal constructor
//                            Sale sale = new Sale(str0,str1,str2,str3,str4,str5,str6,str7,new Position[] { new Position( "600139",1,1550)});
//                            Sale sale = new Sale(str0,str1,str2,str3,str4,str5,str6,str7,new Position[] { p, p}, Arrays.asList(p,p));
//                            Sale sale = new Sale(str0,str1,str2,str3,str4,str5,str6,str7,new Position[] {p}, stringList);
                            Sale sale = new Sale(str0,str1,str2,str3,str4,str5,str6,str7, positionList);
                            finalList.add(sale);

                            // counter
//                            Sale saleC = new Sale("","","","","","","","",new Position[] { new Position( str8,1,1)});
                            Sale saleC = new Sale("","","","","","","","",Arrays.asList( new Position( str8,1,1)));
                            saleSet.add(saleC);
                        }

                        // END OF SPECIAL CODE!!!

                    }
                    // end of loop
                });
                list = finalList;
                // counter
                System.out.println(" saleSet.size(): " + saleSet.size());
                System.out.println(" list.size(): " + list.size());


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