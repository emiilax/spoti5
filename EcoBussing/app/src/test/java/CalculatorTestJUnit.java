import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.EcoTravelApplication;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2015-10-11.
 */
public class CalculatorTestJUnit extends TestCase {
    Calculator testCalc = Calculator.getCalculator();
    int dist;


    @Test
    public void calculatingDistanceBetweenTheSameLocationsShouldReturnZero() throws Exception {
        dist = 12;
        dist = testCalc.calculateDistance(4.0,12.9875,4.0,12.9875);
        assertTrue(dist == 0);
    }

    @Test(expected = Exception.class)
    public void test() throws Exception{
        dist = 12;
        dist = testCalc.calculateDistance(4.0, 12.9875, 91.0, 12.9875);
    }

}