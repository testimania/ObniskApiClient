import json.classes.Stock;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//  POST:    api/data/stocks

public class ObninskStocks {

    List<Stock> list;
    String path; // = "/api/data/stocks";
    String server; // = "https://dev.sintec.club";
    String port; // = "11001";


    {
/*
        stockList = new ArrayList<>();
        stockList.add(new Stock("10.10.2023", "000039", 4));
        stockList.add(new Stock("10.10.2023", "AFR-1004", 23));
        stockList.add(new Stock("10.10.2023", "А-9", 9));
        stockList.add(new Stock("10.10.2023", "100100", 1));
        stockList.add(new Stock("10.10.2023", "SNF-TR82SET-B", 6));
*/
    }

    // interface: *******************

    // constructor:
    public ObninskStocks() {
        //
        PropertiesInterface prop = PropertiesDispatcher.getInstance();
//        server = prop.getPropertyByName("property_server");
//        port = prop.getPropertyByName("property_server_port");
//            System.out.println(server);  System.out.println(port);
        path = "/api/data/stocks";
            System.out.println("PATH: " + path);

    }

    // basic interface method:
    public boolean sendStocks(){

        // to create List<Stock>:
        File file = new File("stocks.xlsx");
        list = getStockListFromFile(file);
            if ( list == null){
                System.out.println( " Error: creating stocks list from file" );
                return false;
            }

        // tempo
//        System.out.println( "this.stockList: " + this.list);
//            System.exit(0);


        // to send List to server
        ObninskHttpClient<Stock> obninskHttpClient = new ObninskHttpClient<Stock>(path);
        if ( !obninskHttpClient.sendListToServer(list)){
            System.out.println(" Error: sending stock list to server");
            return false;
        }

        return true;
    }
    // ******** end of interface

    // TEST
    public static void main(String[] args) {
        ObninskStocks obninskStocks = new ObninskStocks();
        boolean result = obninskStocks.sendStocks();
        System.out.println("obninskStocks.sendListToServer(): " + result);
    }

    // to have List from file:
    // TODO: 10.10.2023
    private List<Stock> getStockListFromFile(File file){
        List<Stock> stockList = null;
        // TODO: 10.10.2023


        try (InputStream is = new FileInputStream(file); ReadableWorkbook wb = new ReadableWorkbook(is);) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {

                List<Stock> finalStockList = new ArrayList<>();

                rows.forEach(r -> {
                    // reading and converting of row in file!
                    // to don't read headers:
                    if (r.getRowNum()!=1) {

                        // get     String date;
                        String str1 = r.getCellAsString(0).orElse(null);
                        if (str1 != null)
                            str1 = str1.strip();

                        // get String article_number;
                        Cell obj2 = r.getCell(1);
//                        System.out.println(" Obj:" + obj2);
                        String str2 = "";
                        if (obj2 != null){
                            str2 = r.getCellText(1).toLowerCase();
                            str2 = obj2.getText();
                            str2 = str2.strip().toLowerCase();
                        }

                        // get Integer stock_count;
                        BigDecimal bdq = r.getCellAsNumber(2).orElse(null);
                        Integer qr;
                            if (bdq != null){
                                qr = bdq.intValue();
                            } else {
                                qr = 0;
                            }

                        // if any of necessary cell is empty - ignore the row!
                        if (obj2 != null){
                            Stock stock = new Stock(str1,str2,qr);
                            finalStockList.add(stock);
                        }
                    }
                // end of loop
                });
                stockList = finalStockList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stockList;
    }


}