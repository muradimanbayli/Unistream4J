import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static String getTodayRFC1123() {
        Instant instant = Instant.now();
        String formatted = DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(instant);
        return formatted;
    }

    static String hmacSha256(String secret, String message) throws Exception {
        byte[] secretInByte = Base64.getDecoder().decode(secret);

        String algo = "HmacSHA256";
        javax.crypto.spec.SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(secretInByte, algo);

        javax.crypto.Mac sha256_HMAC = javax.crypto.Mac.getInstance(algo);
        sha256_HMAC.init(secret_key);

        byte [] result = sha256_HMAC.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(result);
    }

    public static String getAuthorizationHeader(String applicationId, String secret, String today, String url, Map<String, String> headers) throws Exception {
        String path = new URL(url).getFile().toLowerCase();
        String message = "GET\n\n" + today + "\n" + path;

        String headerInString = headers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith("X-Unistream-"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining("\n"));
        message = message + "\n" +headerInString;

        System.out.println(message);

        String signature = hmacSha256(secret, message);

        System.out.println(String.format("UNIHMAC %s:%s", applicationId, signature));
        return String.format("UNIHMAC %s:%s", applicationId, signature);
    }

    public static void main(String[] args) throws Exception {
        String secret = "<secret key here>";
        String appId= "<application id here>";
        String bankId = "<bank id here>";
        String today = getTodayRFC1123();
        String url = "https://slt-test.api.unistream.com/v2/clients/test1234?";
        String authHeader = getAuthorizationHeader(appId, secret, today, url, Map.of("X-Unistream-Security-PosId", bankId));


        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
                .setHeader("Date", today)
                .setHeader("X-Unistream-Security-PosId", bankId)
                .setHeader("Authorization", authHeader)
                .build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        System.out.println(response.statusCode());

    }
}
