import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {

    // Reemplaza TU_API_KEY por tu clave real
    private static final String API_KEY = "bece8e0c2995393f39b66ba9";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/bece8e0c2995393f39b66ba9/latest/USD";

    public static void main(String[] args) {
        try {
            // Realiza la solicitud a la API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lee la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Cierra las conexiones
            in.close();
            connection.disconnect();

            // Imprimir la respuesta completa de la API para debug
            System.out.println("Respuesta completa de la API: " + content.toString());

            // Procesa el JSON de la respuesta
            JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonObject rates = jsonResponse.getAsJsonObject("rates");

            // Verificar si "rates" es nulo
            if (rates != null) {
                // Imprimir la tasa de cambio de algunas monedas
                System.out.println("Tasa de cambio USD a EUR: " + rates.get("EUR").getAsDouble());
                System.out.println("Tasa de cambio USD a GBP: " + rates.get("GBP").getAsDouble());
                System.out.println("Tasa de cambio USD a JPY: " + rates.get("JPY").getAsDouble());
            } else {
                System.out.println("No se encontró el objeto 'rates' en la respuesta.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
