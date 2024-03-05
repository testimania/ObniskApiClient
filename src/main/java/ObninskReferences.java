import json.classes.Reference;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;



/*
▸sale_point_external_id - внешний ID ТТ
▸sale_point_name - название ТТ
▸sale_point_address - Адрес ТТ
▸sale_point_tin - ИНН ТТ
▸client - ФИО контактного лица
▸client_phone - телефон контактного лица
▸client_email - email контактного лица
▸trade_representative - ФИО ТП
▸trade_representative_external_id - внешний ID ТП

! ВАЖНО Все поля, кроме client,client_phone и client_email обязательны для заполнения

*/

public class ObninskReferences {

    List<Reference> list; // = new ArrayList<>();
    File file = new File("references.xlsx");
    String path = "/api/data/reference";

    {
//        list.add(new Reference("eb75da6b-1d40-11e9-84aa-086266264dbc", "Алешинский С.В. ИП", "мкр.Южный, р-н Санэпидемстанции, гаражи, дом № 1", "623202327324", "",
//                "", "", "Бахарев Дмитрий Андреевич", "b09acf43-9a9b-11ed-a1b8-086266264dbc"));
//        list.add(new Reference("69c39bd9-238e-11-02f746c6-23b7-11333", "Летцев М.В. ИП (4)", "390026, Рязанская область, г.Рязань, ул.Островского, дом № 101, стр.1", "", "622800921620",
//                "", "", "Котунова Маргарита Сергеевна", "b09acf42-9a9b-11ed-a1b8-086266264dbc"));
//        list.add(new Reference("69c39bd9238e11-02f746c6-23b7-1155555", "Летцев М.В. ИП (4)", "390026, Рязанская область, г.Рязань, ул.Островского, дом № 101, стр.1", "", "622800921621",
//                "", "", "Котунова Маргарита Сергеевна", "b09acf42-9a9b-11ed-a1b8-086266264dbc"));
    }

    // interface: *******************
    // constructor:
    public ObninskReferences() {
        //
    }
    // main interface method:
    public boolean sendReferences(){

        // to create List<PriceList>:
        list = getListFromFile(file);
        if ( list == null){
            System.out.println( " Error: creating references list from file" );
            return false;
        }

        //
//        System.exit(0);

        // to send List to server
        ObninskHttpClient<Reference> obninskHttpClient = new ObninskHttpClient<Reference>(path);
        if ( !obninskHttpClient.sendListToServer(list)){
            System.out.println(" Error: sending references list to server");
            return false;
        }
        return true;
    }

    // get list from xlsx:
    private List<Reference> getListFromFile(File file) {

        List<Reference> list = null;

        try (InputStream is = new FileInputStream(file); ReadableWorkbook wb = new ReadableWorkbook(is);) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {

                List<Reference> finalList = new ArrayList<>();

                //
                Set<Reference> referenceSet = new HashSet<>();

                rows.forEach(r -> {

                    // reading and converting of row in file!
                    // to don't read headers:
                    if ((r.getRowNum()!=1)&&(r.getCellCount()==9)) {

                        // SPECIAL CODE  - this code is special for every file!!!

                        // get String sale_point_external_id;
                        Cell sale_point_external_id = r.getCell(0);
//                            System.out.println(" sale_point_external_id:" + sale_point_external_id);
                        String str0 = "";
                        if (sale_point_external_id != null){
                            str0 = sale_point_external_id.getText();
                            str0 = str0.strip();
                        }

                        // get  String sale_point_name;
                        Cell sale_point_name = r.getCell(1);
//                            System.out.println(" sale_point_name: " + sale_point_name);
                        String str1 = "";
                        if (sale_point_name != null){
                            str1 = sale_point_name.getText();
                            str1 = str1.strip();
                        }

                        // get  String sale_point_address;
                        Cell sale_point_address = r.getCell(2);
//                            System.out.println(" sale_point_address: " + sale_point_address);
                        String str2 = "";
                        if (sale_point_address != null){
                            str2 = sale_point_address.getText();
                            str2 = str2.strip();
                        }

                        // get String sale_point_tin;
                        Cell sale_point_tin = r.getCell(3);
//                        System.out.println(" sale_point_tin:" + sale_point_tin);
                        String str3 = "";
                        if (sale_point_tin != null){
                            str3 = sale_point_tin.getText();
                            str3 = str3.strip();
                        }

                        // get String client;
                        Cell client = r.getCell(4);
//                        System.out.println(" client: " + client);
                        String str4 = "";
                        if (client != null){
                            str4 = client.getText();
                            str4 = str4.strip();
                        }


                        // get String client_phone;
                        Cell client_phone = r.getCell(5);
//                        System.out.println(" client_phone: " + client_phone);
                        String str5 = "";
                        if (client_phone != null){
                            str5 = client.getText();
                            str5 = str5.strip();
                        }

                        // get String client_email;
                        Cell client_email = r.getCell(6);
//                        System.out.println(" client_email: " + client_email);
                        String str6 = "";
                        if (client_email != null){
                            str6 = client.getText();
                            str6 = str6.strip();
                        }

                        // get String trade_representative;
                        Cell trade_representative = r.getCell(7);
//                        System.out.println(" trade_representative:" + trade_representative);
                        String str7 = "";
                        if (trade_representative != null){
                            str7 = trade_representative.getText();
                            str7 = str7.strip();
                        }

                        // get String trade_representative_external_id;
                        Cell trade_representative_external_id = r.getCell(8);
//                        System.out.println(" trade_representative_external_id:" + trade_representative_external_id);
                        String str8 = "";
                        if (trade_representative_external_id != null){
                            str8 = trade_representative_external_id.getText();
                            str8 = str8.strip();
                        }

                        // if any of necessary cell is empty - ignore the row!
                        // not strict checking - client fields are not necessary
                        if (
                                (sale_point_external_id != null)
                                        &&(sale_point_name != null)
                                        &&(sale_point_address != null)
                                        &&(sale_point_tin != null)
                                        &&(trade_representative != null)
                                        &&(trade_representative_external_id != null)
                        ) {
                            Reference reference = new Reference(str0,str1,str2,str3,str7,str8);
                            finalList.add(reference);
                            // counter
                            referenceSet.add(reference);
                        }

                        // END OF SPECIAL CODE!!!

                    }
                    // end of loop
                });
                list = finalList;
                // counter
                System.out.println(" referenceSet.size: " + referenceSet.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
        // end of getListFromFile
    }

    // ******** end of interface

    // test
    public static void main(String[] args) {
        ObninskReferences obninskReferences = new ObninskReferences();
        boolean result = obninskReferences.sendReferences();
        System.out.println("obninskReferences.sendPrices(): " + result);
    }

}
