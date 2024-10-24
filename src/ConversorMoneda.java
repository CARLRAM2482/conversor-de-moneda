import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorMoneda {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/bece8e0c2995393f39b66ba9/latest/USD";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Conversor de Moneda");
        System.out.println("1. Dólar a Peso Argentino");
        System.out.println("2. Dólar a Peso Colombiano");
        System.out.println("3. Dólar a Real Brasileño");
        System.out.println("Elija una opción:");

        int opcion = scanner.nextInt();
        System.out.println("Ingrese la cantidad en USD:");
        double cantidad = scanner.nextDouble();

        String currencyCode = "";
        switch (opcion) {
            case 1 -> currencyCode = "ARS";
            case 2 -> currencyCode = "COP";
            case 3 -> currencyCode = "BRL";
            default -> {
                System.out.println("Opción no válida");
                System.exit(0);
            }
        }

        double tasaCambio = obtenerTasaCambio(currencyCode);
        if (tasaCambio != 0) {
            double resultado = cantidad * tasaCambio;
            System.out.println("El equivalente es: " + resultado + " " + currencyCode);
        }
    }

    public static double obtenerTasaCambio(String currencyCode) {
        try {
            // Conexión a la API
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Verificación del código de respuesta HTTP
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error: " + responseCode);
            } else {
                // Parseo del JSON recibido
                InputStreamReader reader = new InputStreamReader(conn.getInputStream());
                JsonObject data = JsonParser.parseReader(reader).getAsJsonObject();
                JsonObject conversionRates = data.getAsJsonObject("conversion_rates");
                return conversionRates.get(currencyCode).getAsDouble();
            }

        } catch (Exception e) {
            System.out.println("Error al obtener tasa de cambio: " + e.getMessage());
            return 0;
        }
    }
}
