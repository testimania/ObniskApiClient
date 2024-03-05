package redundant;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//    This Java program uses HTTP Client API to submit form data in a JSON format(application/json).

// TODO: 04.10.2023  reformat as singleton
// TODO: 10.10.2023 DONE in other class
public class ObninskAuthPostRequest {

    String token = null;
    String server = "https://dev.sintec.club";
    String port = "11001";
    String path = "/auth/sign-in";
    // flag - to show that the returned token is valid. If true -  you use getToken(), if not - use getNewToken()
    boolean actualToken = true;


    // the base public method to get the current token
    public String getToken() {
//        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQwMjg5NzYsImlhdCI6MTY5Mzk4NTc3NiwiYXV0aF9kZWFsZXJfaWQiOiIxYzAyM2ZjZC1iMDMzLTRlZjMtYjAxOS05MTkyM2QxYWU4ZTMifQ.EKjGAVhBPKibhr74ht4Hehb0Xv8FIIExJFkOdQyrqC4";
        if ( actualToken || token == null) {
            try {
                if ( getNewToken())
                    return token;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return token;
    }


    public boolean getNewToken() throws IOException, InterruptedException {
        boolean result = false;

        // json formatted data to receive token
        String json = new StringBuilder()
                .append("{")
                .append("\"login\":\"ultra\",")
                .append("\"password\":\"CYSbgf85F8\"")
                .append("}").toString();
        // test
            System.out.println( json.toString());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create( server + ":" + port + path))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String body = response.body();
            System.out.println("The debug out:");
            System.out.println("Status code: " + statusCode);
            System.out.println("Headers : " + response.headers().toString());
            System.out.println("Body: " + body);
            System.out.println("The end of debug out." + System.lineSeparator());

            if ( statusCode == 200 || statusCode == 201) {
                System.out.println("  - Ok - ");
                token = getTokenFromResponse(body);
                result = true;
            } else if (statusCode == 401 ) {
                System.out.println(" Error with status code: " + statusCode);
                System.out.println(" The new token must be received.");
            } else {
                System.out.println(" Error with status code: " + statusCode);
            }

        return result;
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

    public static void main(String[] args)  {
        ObninskAuthPostRequest obninskAuthPostRequest = new ObninskAuthPostRequest();
        try {
            if (obninskAuthPostRequest.getNewToken()){
                System.out.println( obninskAuthPostRequest.getToken());
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }

    }
}