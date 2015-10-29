package com.example.spoti5.ecobussing.controller.calculations;

import com.example.spoti5.ecobussing.io.CheckUserInput;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2015-10-28.
 */
public class CheckUserInputTestJUnit {

    @Test
    public void passwordWithLessThan7LengthShouldReturn0(){
        assertTrue(CheckUserInput.checkPassword("asdfgh") == 0);
        assertTrue(CheckUserInput.checkPassword("123456") == 0);
    }

    @Test
    public void passwordLongerThan6ButWithoutUppercaseShouldReturn1(){
        assertTrue(CheckUserInput.checkPassword("asdfghj") == 1);
        assertTrue(CheckUserInput.checkPassword("1234567") == 1);
    }

    @Test
    public void passwordLongerThan6ButWithoutLowercaseShouldReturn2(){
        assertTrue(CheckUserInput.checkPassword("ASDFGHJ") == 2);
    }

    @Test
    public void emailMustHaveAtAndDot(){
        assertFalse(CheckUserInput.checkEmail("1234567"));
        assertFalse(CheckUserInput.checkEmail("1234567@"));
        assertFalse(CheckUserInput.checkEmail("ab.com"));
        assertTrue(CheckUserInput.checkEmail("abc@mail.com"));
    }

    @Test
    public void emailMustHaveAtAndDotInCorrectOrder(){
        assertFalse(CheckUserInput.checkEmail("abc.mail@com"));
    }

    @Test
    public void emailMustContainLettersBetweenAndBeforeAfterAtAndDot(){
        assertFalse(CheckUserInput.checkEmail("abc@mail."));
        assertFalse(CheckUserInput.checkEmail("@mail.com"));
        assertFalse(CheckUserInput.checkEmail("@."));
    }

}