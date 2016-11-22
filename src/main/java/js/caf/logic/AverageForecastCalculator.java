package js.caf.logic;

import js.caf.domain.DailyForecast;
import js.caf.exception.NotEnoughDataPointsException;

import java.util.List;

import javax.print.attribute.standard.NumberUpSupported;

/**
 * Created by mentlsve on 22/11/16.
 */
public class AverageForecastCalculator {

    public float calculateAverageMaxTemperatureOverDays(List<DailyForecast> dailyForecasts, int numberOfDays){
    	if(numberOfDays == 1 || numberOfDays > dailyForecasts.size()){
    		throw new NotEnoughDataPointsException();
    	}
    	float sum = 0.0f;
    	for(int i = 0; i < numberOfDays; i++){
    		sum += dailyForecasts.get(i).getMaxTemperatureInCelsius() + 32;
    	}
    	float average = sum / numberOfDays;
    	return average;
    }

}
