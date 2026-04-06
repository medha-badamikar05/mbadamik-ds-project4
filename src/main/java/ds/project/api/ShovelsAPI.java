package ds.project.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ShovelsAPI {
    private static final String API_KEY = "";
    private static final String SEARCH_URL = "https://api.shovels.ai/v2/permits/search";

    public static void main(String[] args) throws Exception {

        // Build the request URL with query parameters
        String url = SEARCH_URL
                + "?geo_id=15213"
                + "&permit_from=2024-01-01"
                + "&permit_to=2024-12-31"
                + "&size=5";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-API-Key", API_KEY)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());

        // Parse JSON response using Gson
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray permits = jsonResponse.getAsJsonArray("items");

        System.out.println("Total number of permits for ZIP 15213: " + permits.size());

        for (int i = 0; i < permits.size(); i++) {
            JsonObject permit = permits.get(i).getAsJsonObject();
            String number = permit.has("number")
                    && !permit.get("number").isJsonNull()
                    ? permit.get("number").getAsString() : "N/A";
            String description = permit.has("description")
                    && !permit.get("description").isJsonNull()
                    ? permit.get("description").getAsString() : "N/A";
            String status = permit.has("status")
                    && !permit.get("status").isJsonNull()
                    ? permit.get("status").getAsString() : "N/A";
            String fileDate = permit.has("file_date")
                    && !permit.get("file_date").isJsonNull()
                    ? permit.get("file_date").getAsString() : "N/A";
            String jurisdiction = permit.has("jurisdiction")
                    && !permit.get("jurisdiction").isJsonNull()
                    ? permit.get("jurisdiction").getAsString() : "N/A";


            System.out.println("Permit number:" + (i + 1));
            System.out.println("Number:" + number);
            System.out.println("Description:" + description);
            System.out.println("Status:" + status);
            System.out.println("Filed:" + fileDate);
            System.out.println("Jurisdiction:" + jurisdiction);
        }
    }
}

