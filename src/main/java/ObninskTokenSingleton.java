// the class receives, keeps and sent actual token for authorised http sessions

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

public class ObninskTokenSingleton {

    private String token;
    private Date timeUp;
    private Date timeDown;
    static ObninskTokenSingleton instance = null;
//    static String mode = "DEV";
    static String mode = "PRODUCTION";

//    String server = "https://dev.sintec.club";
    String server = "https://sintec.club";
//    String port = "11001";
    String port = "11000";
    String path = "/auth/sign-in";

    {
        // TODO: 16.11.2023  - how better?
        if (!mode.equalsIgnoreCase(  "DEV")) {
            server = "https://sintec.club";
            port = "11000";
            System.out.println(" Production mode !!!!!!!!!!!!");
        } else
            System.out.println(" Dev mode !!!!!!!!!!!!");

    }


    public static String getMode() {
        return mode;
    }

    // public interface
    public static String getToken(){
        if ( instance == null){
            instance = new ObninskTokenSingleton();
            instance.setNewToken();
        }
        instance.updateData();
        return instance.token;
    }

    // update data if necessary
    private void updateData(){
        if ( token == null ){
            setNewToken();
        }
        Date currentTime = new Date();
        if (currentTime.after( timeDown)){
            setNewToken();
        }
    }


    // request of new token
    private void setNewToken(){
        timeUp =  new Date();
        timeDown = new Date();

        try {
            token = getCurrentTokenFromServer();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if ( token != null){
            // set time limit
            timeDown.setHours( timeUp.getHours() + 12);  //      System.out.println( "timeUp: " + timeUp); System.out.println( "timeDown: " + timeDown);
//            token = String.format( " %s ", timeDown);
        }
    }

    // getCurrentTokenFromServer of Obninsk
    private String getCurrentTokenFromServer() throws IOException, InterruptedException {


        // json formatted data to receive token
        String json = new StringBuilder()
                .append("{")
                .append("\"login\":\"ultra\",")
                .append("\"password\":\"CYSbgf85F8\"")
                .append("}").toString();
        // test
        //        System.out.println( json.toString());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String body = response.body();
        //            System.out.println("The debug out:");
        //            System.out.println("Status code: " + statusCode);
        //            System.out.println("Headers : " + response.headers().toString());
        //            System.out.println("Body: " + body);
        //            System.out.println("The end of debug out." + System.lineSeparator());

        if ( statusCode == 200 || statusCode == 201) {
            System.out.println(" Server response: Ok - statusCode: " + statusCode);
            token = getTokenFromResponse(body);
        } else if (statusCode == 401 ) {
            System.out.println(" Error with status code: " + statusCode);
            System.out.println(" The new token must be received.");
        } else {
            System.out.println(" Error with status code: " + statusCode);
        }

        return token;
    }

    private String getTokenFromResponse( String r){
        String token = null;
        Gson gson = new Gson();
        TokenResponse tokenResponse =  gson.fromJson( r, TokenResponse.class);
        token = tokenResponse.token;
        return token;
    }

    private static class TokenResponse{
        String token;
    }




    // private constructor
    private ObninskTokenSingleton() {
        token = null;
    }



}
