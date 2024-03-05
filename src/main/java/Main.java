import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        class Sell{
            String id;
            String codeid;
            int quant;

            public Sell(String id, String codeid, int quant) {
                this.id = id;
                this.codeid = codeid;
                this.quant = quant;
            }

            @Override
            public String toString() {
                return ":" + id + " - " + codeid + " - " + quant;
            }

            public String getId() {
                return id;
            }
        }

        List<Sell> list = Arrays.asList( new Sell("1","45",10),new Sell("2","33",20), new Sell("2","61",20),new Sell("3","55",30) );
        list.stream().forEach( a -> System.out.println(a));
        System.out.println("Map:");
        Map<String, List<Sell>> map =  list.stream().collect(Collectors.groupingBy(a -> a.getId()));
        System.out.println(map);
        System.out.println(map.get("1").toString());
        System.out.println(map.get("2").toString());
        System.out.println(map.get("3").toString());
        List<Sell> testlist = map.get("2");
        System.out.println(testlist);
        testlist.stream().forEach(System.out::println);



    }

}
