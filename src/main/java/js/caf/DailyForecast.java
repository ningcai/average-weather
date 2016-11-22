package js.caf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by mentlsve on 22/11/16.
 */
public class DailyForecast {

    private String date;

    private Float maxTemperatureInCelsius;

    /**
     * @param timeStamp The UNIX time at which this data point begins.
     *                  Minutely data point are always aligned to the top of the minute,
     *                  hourly data point objects to the top of the hour,
     *                  and daily data point objects to midnight of the day,
     *                  all according to the local time zone.

     * @param maxTemperatureInFahrenheit The maximum value of temperature during a given day in degrees Fahrenheit.
     */
    public DailyForecast(String timeZone, long timeStamp, Float maxTemperatureInFahrenheit){
        ZoneId id = ZoneId.of(timeZone);
        ZonedDateTime zoned = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), id);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        date = zoned.format(formatter);

        this.maxTemperatureInCelsius = maxTemperatureInFahrenheit - 32;
    }

    public String getDate() {
        return date;
    }

    public Float getMaxTemperatureInCelsius() {
        return maxTemperatureInCelsius;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return "Max. temperature on the  " + date + " will be " + df.format(maxTemperatureInCelsius) + " degrees Celsius";
    }


}
