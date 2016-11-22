package js.caf.logic;

import js.caf.domain.DailyForecast;
import js.caf.exception.NotEnoughDataPointsException;
import js.caf.logic.AverageForecastCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mentlsve on 22/11/16.
 */
public class AverageForecastCalculatorTest {

    AverageForecastCalculator averageForecastCalculator;
    List<DailyForecast> dailyForecasts;

    @Before
    public void setup(){
        averageForecastCalculator = new AverageForecastCalculator();
        dailyForecasts = new ArrayList<>();
    }

    @Test(expected = NotEnoughDataPointsException.class)
    public void throwExceptionIfThereIsOnlyOneForecast() {

        // prepare test data
        dailyForecasts.add(new DailyForecast("irrelevant", 1L, 1.0f));

        // object under test
        averageForecastCalculator.calculateAverageMaxTemperatureOverDays(dailyForecasts, 10);
    }

    @Test(expected = NotEnoughDataPointsException.class)
    public void throwExceptionIfThereAreNotEnoughDataPoints() {

        // we want to calculate the average max temperature over the next 10 days,
        // but we only have two data points. Therefore this is not possible.

        // prepare test data
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 1L, 1.0f));
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 2L, 3.0f));

        // object under test
        averageForecastCalculator.calculateAverageMaxTemperatureOverDays(dailyForecasts, 10);
    }

    @Test
    public void calculateAverageOverTheNextThreeDays() {

        // prepare test data
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 1L, 22.0f));
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 2L, 23.0f));
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 3L, 26.0f));

        // object under test
        float res = averageForecastCalculator.calculateAverageMaxTemperatureOverDays(dailyForecasts, 3);

        Assert.assertEquals(23.67f, res, 0.1f);
    }

    @Test
    public void calculateAverageOverTheNextTwoDays() {

        // we have more data points than needed, so we have to take the first two only.

        // prepare test data
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 1L, 22.0f));
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 2L, 23.0f));
        dailyForecasts.add(new DailyForecast("America/Los_Angeles", 3L, 26.0f));

        // object under test
        float res = averageForecastCalculator.calculateAverageMaxTemperatureOverDays(dailyForecasts, 2);

        Assert.assertEquals(22.50f, res, 0.1f);
    }


}
