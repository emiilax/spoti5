package com.example.spoti5.ecobussing.model.profile;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2015-10-29.
 */
public class CompanyTestJUnit {
    private User testUser1 = new User("tu1@mail.com", "tu1");
    private User testUser2 = new User("tu2@mail.com", "tu2");
    private User testUser3 = new User("tu3@mail.com", "tu3");

    private Company testCompany1 = new Company("tc1", testUser1, 30);

    @Test
    public void testGetCreatorMember(){
        assertTrue(testUser1.getEmail().equals(testCompany1.getCreatorMember()));
    }

    @Test
    public void testUserIsMember(){
        testCompany1.addMember(testUser2);
        assertTrue(testCompany1.userIsMember(testUser2));
        assertTrue(testCompany1.userIsMember(testUser1));
    }

    @Test
    public void testUserIsCreator(){
        assertTrue(testCompany1.userIsCreator(testUser1));
    }

}