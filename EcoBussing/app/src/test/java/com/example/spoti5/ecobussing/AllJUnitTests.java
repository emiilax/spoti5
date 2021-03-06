package com.example.spoti5.ecobussing;

import com.example.spoti5.ecobussing.apirequests.ElectricityApiJUnit;
import com.example.spoti5.ecobussing.controller.calculations.CalculatorTestJUnit;
import com.example.spoti5.ecobussing.controller.calculations.CheckUserInputTestJUnit;
import com.example.spoti5.ecobussing.model.profile.CompanyTestJUnit;
import com.example.spoti5.ecobussing.model.profile.DeepMapTestJUnit;
import com.example.spoti5.ecobussing.model.profile.UserTestJUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Hampus on 2015-10-13.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorTestJUnit.class, ElectricityApiJUnit.class, UserTestJUnit.class,
        CheckUserInputTestJUnit.class, CompanyTestJUnit.class, DeepMapTestJUnit.class})
public class AllJUnitTests {
}
