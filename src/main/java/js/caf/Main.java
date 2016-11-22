package js.caf;

import js.caf.domain.DailyForecast;
import js.caf.logic.AverageForecastCalculator;
import js.caf.logic.ResponseHandler;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by mentlsve on 22/11/16.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        final String WEATHER_SERVICE_API_ENDPOINT = "https://api.darksky.net/forecast/<your key goes here>/37.8267,-122.4233";

        JsonObject weatherServiceResponse = null;

        // call the API
        URL url = new URL(WEATHER_SERVICE_API_ENDPOINT);
        try (InputStream is = url.openStream();
            JsonReader jsonReader = Json.createReader(is)) {
            weatherServiceResponse =jsonReader.readObject();
        }


        // extract the response
        ResponseHandler responseHandler = new ResponseHandler();
        List<DailyForecast> dailyForecasts = responseHandler.extractDailyForecast(weatherServiceResponse);

        // calculate the average max temperature for the next five days
        AverageForecastCalculator averageForecast = new AverageForecastCalculator();
        float averageMaxTemperatureOverNextFiveDays = averageForecast.
                calculateAverageMaxTemperatureOverDays(dailyForecasts, 5);

        System.out.println("The temperature over the next five days will be "
                + averageMaxTemperatureOverNextFiveDays
                + " degrees celsius");
    }
}
