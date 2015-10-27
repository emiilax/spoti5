package com.example.spoti5.ecobussing;

import com.example.spoti5.ecobussing.apirequests.ElectricityApiJUnit;
import com.example.spoti5.ecobussing.calculations.CalculatorTestJUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Hampus on 2015-10-13.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorTestJUnit.class, ElectricityApiJUnit.class})
public class AllJUnitTests {
}
