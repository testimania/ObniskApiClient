package json.classes;

//  POST:    api/data/sales


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


import java.util.List;

public class Sale {
    String id;
    String date;
    String sale_point_external_id;
    String sale_point_name;
    String sale_point_address;
    String sale_point_tin;
    String trade_representative;
    String trade_representative_external_id;
    List<Position> positions;

//    List<Position> positionList;

    public Sale(String id, String date, String sale_point_external_id, String sale_point_name, String sale_point_address, String sale_point_tin, String trade_representative, String trade_representative_external_id, List<Position> positions) {
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

    public String getId() {
        return id;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "id: " + id + ", date: " + date + ",sale_point_external_id" + sale_point_external_id + ",sale_point_tin: " + sale_point_tin +",positions: " + positions;
//        System.out.println("id: " + id + ", date: " + date + ",sale_point_external_id" + sale_point_external_id + ",sale_point_tin: " + sale_point_tin +",positions: " + positions.toString());
    }
}
