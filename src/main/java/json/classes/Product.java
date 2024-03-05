package json.classes;


// GET /api/products

public class Product {
    String article_number;
    String product_name;
    String category_id;
    String category_name;
    String brand_id;
    String brand_name;

    public String getArticle_number() {
        return article_number;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    @Override
    public String toString() {
        String p =
                "article_number: " + article_number + ", " +
                        "product_name: " + product_name + ", " +
                        "category_id: " + category_id + ", " +
                        "category_name: " + category_name + ", " +
                        "brand_id: " + brand_id + ", " +
                        "brand_name: " + brand_name // + ", " + System.lineSeparator()
                ;
        return p;
    }
}
