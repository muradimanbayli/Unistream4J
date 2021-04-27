import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiClient {
    private final String applicationId;
    private final String secretKey;
    private String bankId;

    public ApiClient(String appId, String secretKey) {
        this.applicationId = appId;
        this.secretKey = secretKey;
    }

    public ApiClient bankId(String bankId) {
        this.bankId = bankId;
        return this;
    }

    public ClientService clients() {
        return new ClientService(applicationId, secretKey, bankId);
    }

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

    public static String getAuthorizationHeader(String applicationId, String secret, String signString) throws Exception {
        String signature = hmacSha256(secret, signString);
        return String.format("UNIHMAC %s:%s", applicationId, signature);
    }

    public static String signString(String httpMethod, String today, String url, Map<String, String> headers) throws MalformedURLException {
        String path = new URL(url).getFile().toLowerCase();

        String message = httpMethod+"\n\n" + today + "\n" + path;

        if(!headers.isEmpty()) {
            String headerInString = headers.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().startsWith("X-Unistream-"))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.joining("\n"));

            message = message + "\n" +headerInString;
        }

        return message;
    }
}
