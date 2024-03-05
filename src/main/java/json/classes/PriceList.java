package json.classes;

//  POST:    api/data/price-lists

/*
▸product_article - артикул продукта
▸sale_point_external_id - внешний ID ТТ
▸cost - базовая цена для ТТ с НДС
▸discount - максимально возможная скидка в процентах

! ВАЖНО Все поля обязательны для заполнения
В результате успешного выполнения запроса сервис вернет: STATUS 200

*/

public class PriceList {
    String product_article;
    String sale_point_external_id;
    Float cost;
    Integer discount;

    public PriceList(String product_article, String sale_point_external_id, Float cost, Integer discount) {
        this.product_article = product_article;
        this.sale_point_external_id = sale_point_external_id;
        this.cost = cost;
        this.discount = discount;
    }
}
