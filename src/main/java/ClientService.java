import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


public class ClientService {
    private String applicationId;
    private String secretKey;
    private String bankId;

    public ClientService(String applicationId, String secretKey, String bankId) {
        this.applicationId = applicationId;
        this.secretKey = secretKey;
        this.bankId = bankId;
    }

    public String create(String requestBody) throws Exception {
        String today = ApiClient.getTodayRFC1123();
        String url = "https://slt-test.api.unistream.com/v2/clients/";
        Map<String, String> headers = Map.of("Date", today, "X-Unistream-Security-PosId", bankId);
        String signString = ApiClient.signString("POST", today, url, headers);
        String authHeader = ApiClient.getAuthorizationHeader(applicationId, secretKey, signString);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url))
                .setHeader("Date", today)
                .setHeader("X-Unistream-Security-PosId", bankId)
                .setHeader("Authorization", authHeader)
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String get(String clientId) throws Exception {
        String today = ApiClient.getTodayRFC1123();
        String url = "https://slt-test.api.unistream.com/v2/clients/"+clientId;
        Map<String, String> headers = Map.of("Date", today, "X-Unistream-Security-PosId", bankId);
        String signString = ApiClient.signString("GET", today, url, headers);
        String authHeader = ApiClient.getAuthorizationHeader(applicationId, secretKey, signString);

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(url))
                .setHeader("Date", today)
                .setHeader("X-Unistream-Security-PosId", bankId)
                .setHeader("Authorization", authHeader)
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
