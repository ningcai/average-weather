package js.caf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;

/**
 * Created by mentlsve on 22/11/16.
 */
public class ResponseHandler {

    public List<DailyForecast> extractDailyForecast(JsonObject object){
        //URL url = new URL("https://graph.facebook.com/search?q=java&type=post");

        // return value
        List<DailyForecast> dailyForecasts = new ArrayList<DailyForecast>();

        JsonString timezone = object.getJsonString("timezone");

        // find daily forecast data in the nested JSON object
        JsonObject dailyForecastWrapper = object.getJsonObject("daily");
        JsonArray dailyForecastsRaw = dailyForecastWrapper.getJsonArray("data");

        // map each raw forecast to a nice forecast object, extracting only some data
        for(JsonObject singleForecastRaw : dailyForecastsRaw.getValuesAs(JsonObject.class)) {
            DailyForecast df = extractForecastForOneDay(timezone, singleForecastRaw);
            dailyForecasts.add(df);
        }

        return dailyForecasts;
    }

    /**
     * extracts the time and the temperatureMax value from the raw forecast
     *
     * @param singleForecast
     * @return
     */
    private DailyForecast extractForecastForOneDay(JsonString timezone, JsonObject singleForecast){
        // raw data
        JsonNumber time = singleForecast.getJsonNumber("time");
        JsonNumber temperature = singleForecast.getJsonNumber("temperatureMax");

        // transform into domain object
        DailyForecast df = new DailyForecast(timezone.getString(), time.bigIntegerValue().longValue(), temperature.bigDecimalValue().floatValue());

        return df;
    }

}

