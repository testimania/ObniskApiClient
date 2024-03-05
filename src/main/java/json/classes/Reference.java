package json.classes;

//  POST:    api/data/reference

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

public class Reference {
    String sale_point_external_id;
    String sale_point_name;
    String sale_point_address;
    String sale_point_tin;
    String client; // not necessary
    String client_phone;  // not necessary
    String client_email;  // not necessary
    String trade_representative;
    String trade_representative_external_id;

    public Reference(String sale_point_external_id, String sale_point_name, String sale_point_address, String sale_point_tin, String client, String client_phone, String client_email, String trade_representative, String trade_representative_external_id) {
        this.sale_point_external_id = sale_point_external_id;
        this.sale_point_name = sale_point_name;
        this.sale_point_address = sale_point_address;
        this.sale_point_tin = sale_point_tin;
        this.client = client;
        this.client_phone = client_phone;
        this.client_email = client_email;
        this.trade_representative = trade_representative;
        this.trade_representative_external_id = trade_representative_external_id;
    }

    public Reference(String sale_point_external_id, String sale_point_name, String sale_point_address, String sale_point_tin, String trade_representative, String trade_representative_external_id) {
        this.sale_point_external_id = sale_point_external_id;
        this.sale_point_name = sale_point_name;
        this.sale_point_address = sale_point_address;
        this.sale_point_tin = sale_point_tin;
        this.client = "";
        this.client_phone = "";
        this.client_email = "";
        this.trade_representative = trade_representative;
        this.trade_representative_external_id = trade_representative_external_id;
    }
}
