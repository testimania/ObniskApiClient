package redundant;// redundant.Main class :)

import json.classes.Sale;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // connection properties
        String server = "https://dev.sintec.club";
        String port = "11001";
        // get actual token
        ObninskAuthPostRequest obninskAuthPostRequest = new ObninskAuthPostRequest();
//        String token = obninskAuthPostRequest.getToken();
        String token = null;
        try {
            if (obninskAuthPostRequest.getNewToken()){
                token = obninskAuthPostRequest.getToken();
                System.out.println( token);
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }
        // endpoint
        String path = "/api/data/sales";

        ObninskPostRequester<Sale> obninskPostRequester = new ObninskPostRequester<Sale>(server,port,path,token);
        obninskPostRequester.requestList = obninskPostRequester.getSaleListExample();
        try {
            obninskPostRequester.sendRequest();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }


    }


}
