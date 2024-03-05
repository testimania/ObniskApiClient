package json.classes;

/*
        ▸positions - позиции заказа
            ▸article_number - артикул товара
            ▸quantity - количество заказанного товара
            ▸price - цена товара за единицу
            ▸discount_percentage - примененная скидка в процентах
            ▸discount_amount - сумма скидки
            ▸amount - сумма за товар
*/


public class OrderPosition {

    String article_number;
    Integer quantity;
    Integer amount;
    Integer price;
    Integer discount_percentage;
    Integer discount_amount;

    public OrderPosition(String article_number, Integer quantity, Integer amount, Integer price, Integer discount_percentage, Integer discount_amount) {
        this.article_number = article_number;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
        this.discount_percentage = discount_percentage;
        this.discount_amount = discount_amount;
    }

    @Override
    public String toString() {
        return String.format(" article_number: %s, quantity: %s, amount: %s price: %s, discount_percentage: %s, discount_amount: %s",
                article_number,quantity,amount,price, discount_percentage, discount_amount);
    }


}
