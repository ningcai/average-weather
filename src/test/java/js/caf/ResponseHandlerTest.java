package js.caf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.*;
import java.io.*;
import java.util.List;

/**
 * Created by mentlsve on 22/11/16.
 */
public class ResponseHandlerTest {

    JsonObject responseAsJsonObject;

    String responseFileName = "sample-response.json";

    ResponseHandler responseHandler = new ResponseHandler();

    @Before
    public void setup() throws FileNotFoundException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(responseFileName);
        JsonReader jsonReader = Json.createReader(inputStream);
        responseAsJsonObject = jsonReader.readObject();
    }



    @Test
    public void extractAllDailyForecasts(){
        List<DailyForecast> dailyForecasts = responseHandler.extractDailyForecast(responseAsJsonObject);
        Assert.assertEquals(8, dailyForecasts.size());
    }


    @Test
    public void verifyDataPointsOfFirstExtractedDailyForecast(){
        List<DailyForecast> dailyForecasts = responseHandler.extractDailyForecast(responseAsJsonObject);
        Assert.assertEquals("2016-11-21T00:00:00-08:00[America/Los_Angeles]", dailyForecasts.get(0).getDate());
        Assert.assertEquals(27.41, dailyForecasts.get(0).getMaxTemperatureInCelsius().floatValue(),0.1);
    }
}
