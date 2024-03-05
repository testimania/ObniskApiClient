package json.classes;

public class Position {
    String article_number;
    Integer quantity;
    Integer amount;

    public Position(String article_number, Integer quantity, Integer amount) {
        this.article_number = article_number;
        this.quantity = quantity;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format(" article_number: %s, quantity: %s, amount: %s",article_number,quantity,amount);
    }
}
