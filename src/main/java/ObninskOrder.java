
// получить заказ ( заказы ), вывести в терминал, файл json ->  one order -> one excel file

public class ObninskOrder {


    static String token;
    static String server;
    static String port;
    static PropertiesInterface prop;

    static {
//        prop = PropertiesDispatcher.getInstance();
//        server = prop.getPropertyByName("property_server");
//        port = prop.getPropertyByName("property_server_port");
//        System.out.println("Worker:");
//        System.out.println("Server: " + server);  System.out.println("Port: " + port); System.out.println();
    }

    // to update all connection properties for http client request
    static boolean refreshConnectionData(){
        boolean result = false;
            try {
                prop = PropertiesDispatcher.getInstance();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
            server = prop.getPropertyByName("property_server");
            port = prop.getPropertyByName("property_server_port");
            result = true;
                System.out.println("ObninskOrder:");
                System.out.println("Server: " + server);  System.out.println("Port: " + port); System.out.println();
        return  result;
    }

    // получить заказ ( заказы ), вывести в терминал
    public void testGet(){
        System.out.println("" + System.lineSeparator() + " Begin with ObninskOrder.testGet()" + System.lineSeparator());
        if (!refreshConnectionData()){
            System.out.printf(" Error with refreshConnectionData()");
            return;
        }



        System.out.println("" + System.lineSeparator() + " ... End with ObninskOrder.testGet()" + System.lineSeparator());
    }


    // test
    public static void main(String[] args) {
        ObninskOrder order = new ObninskOrder();
        order.testGet();

    }

}
