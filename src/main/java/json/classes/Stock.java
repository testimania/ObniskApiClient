package json.classes;


//  POST:    api/data/stocks

/*
▸date - дата снятия остатков по артикулу
▸article_number - артикул продукта
▸stock_count - количество на складе

! ВАЖНО Все поля обязательны для заполнения
! ВАЖНО Количество stock_count указывать без учета резерва
В результате успешного выполнения запроса сервис вернет: STATUS 200

*/

public class Stock {
    String date;
    String article_number;
    Integer stock_count;

    public Stock(String date, String article_number, Integer stock_count) {
        this.date = date;
        this.article_number = article_number;
        this.stock_count = stock_count;
    }
}
