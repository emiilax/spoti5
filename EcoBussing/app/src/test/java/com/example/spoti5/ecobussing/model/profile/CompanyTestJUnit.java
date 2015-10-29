package com.example.spoti5.ecobussing.model.profile;

import com.example.spoti5.ecobussing.controller.profile.Company;
import com.example.spoti5.ecobussing.controller.profile.User;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2015-10-29.
 */
public class CompanyTestJUnit {
    private User testUser1 = new User("tu1@mail.com", "tu1");
    private User testUser2 = new User("tu2@mail.com", "tu2");

    private Company testCompany1 = new Company("tc1", testUser1, 30);

    @Test
    public void testGetCreatorMember(){
        assertTrue(testUser1.getEmail().equals(testCompany1.getCreatorMember()));
    }

    @Test
    public void testUserIsMember(){
        assertTrue(testCompany1.userIsMember(testUser1));
        assertFalse(testCompany1.userIsMember(testUser2));
    }

    @Test
    public void testUserIsCreator(){
        assertTrue(testCompany1.userIsCreator(testUser1));
        assertFalse(testCompany1.userIsCreator(testUser2));
    }

    @Test
    public void testUserIsModerator() {
        assertTrue(testCompany1.userIsModerator(testUser1));
        assertFalse(testCompany1.userIsModerator(testUser2));
    }

    @Test
    public void testGetSetNbrOfEmployees() {
        assertTrue(testCompany1.getNbrEmployees() == 30);

        testCompany1.setNbrEmployees(35);

        assertTrue(testCompany1.getNbrEmployees() == 35);
    }
}