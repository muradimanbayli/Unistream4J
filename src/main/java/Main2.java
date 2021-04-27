public class Main2 {
    public static void main(String[] args) throws Exception {
        String secret = "<secret her>";
        String appId= "<app id here>";
        String bankId = "<bank id here>";

        ApiClient apiClient = new ApiClient(appId, secret).bankId(bankId);

        System.out.println(apiClient.clients().get("e5f58ada-af06-40cb-9239-8de603292355"));

        System.out.println(apiClient.clients().create(requestBody));

    }

    static String requestBody = "{\n" +
            "  \"id\": \"e5f58ada-af06-40cb-9239-8de603292359\",\n" +
            "  \"countryOfResidence\": \"RUS\",\n" +
            "  \"firstName\": \"Тест\",\n" +
            "  \"lastName\": \"Тестовый\",\n" +
            "  \"middleName\": \"Тестович\",\n" +
            "  \"gender\": \"Male\",\n" +
            "  \"birthPlace\": \"г.Москва\",\n" +
            "  \"birthDate\": \"1980-01-31T00:00:00\",\n" +
            "  \"phoneNumber\": \"89096101726\",\n" +
            "  \"phoneNumberVerificationTimestamp\": null,\n" +
            "  \"taxpayerIndividualIdentificationNumber\": \"000000000000\",\n" +
            "  \"kazId\": null,\n" +
            "  \"address\": {\n" +
            "    \"addressString\": \"\",\n" +
            "    \"apartment\": \"\",\n" +
            "    \"building\": \"\",\n" +
            "    \"city\": \"г.Москва\",\n" +
            "    \"countryCode\": \"RUS\",\n" +
            "    \"house\": \"20\",\n" +
            "    \"postcode\": \"\",\n" +
            "    \"state\": \"г.Москва\",\n" +
            "    \"street\": \"ул.Верхняя Масловка\",\n" +
            "    \"district\": \"Южное Бутово\",\n" +
            "    \"dataFias\": {\n" +
            "      \"fiasActualityState\": \"0\",\n" +
            "      \"fiasLevel\": \"8\",\n" +
            "      \"fiasId\": \"827e46ee-1a96-44bf-8717-e89484480f18\",\n" +
            "      \"fiasCode\": \"61617467161\",\n" +
            "      \"kladrId\": \"6200900012100040015\",\n" +
            "      \"okato\": \"61217851001\",\n" +
            "      \"oktmo\": \"61617467161\",\n" +
            "      \"taxOffice\": \"6219\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"documents\": [\n" +
            "    {\n" +
            "      \"type\": \"Passport.RUS\",\n" +
            "      \"uniqueNumber\": null,\n" +
            "      \"legend\": null,\n" +
            "      \"fields\": {\n" +
            "        \"Series\": \"1234\",\n" +
            "        \"Number\": \"123556\",\n" +
            "        \"Issuer\": \"МВД г.Тестовый\",\n" +
            "        \"IssuerDepartmentCode\": \"123-456\",\n" +
            "        \"IssueDate\": \"2002-01-31T00:00:00\"\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"bankId\": null,\n" +
            "  \"concentByMinor\": null\n" +
            "}";
}
