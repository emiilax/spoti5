package com.example.spoti5.ecobussing;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.spoti5.ecobussing.Profiles.BusinessProfile;
import com.example.spoti5.ecobussing.Profiles.User;

import java.util.ArrayList;

/**
 * Created by hilden on 2015-09-30.
 */
public class BusinessProfileTest extends AndroidTestCase {

    private User bert;
    private User tomas;
    private User lars;
    private User anna;
    private User sara;
    private User lovisa;

    private BusinessProfile mcDonalds;
    private BusinessProfile burgerKing;
    private BusinessProfile max;
    private BusinessProfile pizzaHut;

    private ArrayList<String> answers1;
    private ArrayList<String> answers2;
    private ArrayList<String> answers3;
    private ArrayList<String> answers4;


    public BusinessProfileTest() {
        lovisa = new User("f@gmail.com", "Lovisa");
        sara = new User("e@gmail.com", "Sara");
        anna = new User("d@gmail.com", "Anna");
        lars = new User("c@gmail.com", "Lars");
        tomas = new User("b@gmail.com", "Tomas");
        bert = new User("a@gmail.com", "Bert");

        mcDonalds = new BusinessProfile("MC Donalds", bert);
        burgerKing = new BusinessProfile("Burger King", bert);
        max = new BusinessProfile("MAX", bert);
        pizzaHut = new BusinessProfile("Pizza Hut", bert);

        test1(mcDonalds);
        test2(burgerKing);
        test3(max);
        test4(pizzaHut);
    }

    private void test1(BusinessProfile testBusiness) {
        testBusiness.addMember(lars);
        testBusiness.addMember(anna);
        testBusiness.addMember(tomas);
        testBusiness.addModeratorMember(bert, tomas);
        testBusiness.addModeratorMember(bert, anna);
        testBusiness.removeModeratorMember(bert, anna);

        answers1 = new ArrayList<String>();

        answers1.add("Bert");
        answers1.add("Bert,Lars,Anna,Tomas,");
        answers1.add("Bert,Tomas,");

        printTestResults(testBusiness, answers1, "TEST 1");
      }

    private void test2(BusinessProfile testBusiness) {
        testBusiness.addMember(lars);
        testBusiness.addMember(anna);
        testBusiness.addMember(tomas);
        testBusiness.addModeratorMember(bert, tomas);
        testBusiness.addMember(lars);
        testBusiness.addMember(lovisa);
        testBusiness.removeMember(anna);
        testBusiness.addModeratorMember(anna, lars);
        testBusiness.addModeratorMember(tomas, lars);
        testBusiness.addModeratorMember(bert, sara);
        testBusiness.addModeratorMember(bert, lovisa);
        testBusiness.removeMember(tomas);
        testBusiness.removeModeratorMember(bert, bert);

        answers2 = new ArrayList<String>();

        answers2.add("Bert");
        answers2.add("Bert,Lars,Lovisa,");
        answers2.add("Bert,Lovisa,");

        printTestResults(testBusiness, answers2, "TEST 2");
    }

    private void test3(BusinessProfile testBusiness) {
        testBusiness.addMember(lars);
        testBusiness.addMember(anna);
        testBusiness.addMember(tomas);
        testBusiness.addMember(lovisa);
        testBusiness.addMember(sara);
        testBusiness.addModeratorMember(bert, lars);
        testBusiness.addModeratorMember(bert, anna);
        testBusiness.addModeratorMember(bert, tomas);
        testBusiness.addModeratorMember(bert, lovisa);
        testBusiness.addModeratorMember(bert, sara);

        answers3 = new ArrayList<String>();

        answers3.add("Bert");
        answers3.add("Bert,Lars,Anna,Tomas,Lovisa,Sara,");
        answers3.add("Bert,Lars,Anna,Tomas,Lovisa,Sara,");

        printTestResults(testBusiness, answers3, "TEST 3");
    }

    private void test4(BusinessProfile testBusiness) {
        testBusiness.addMember(lars);
        testBusiness.addMember(anna);
        testBusiness.addMember(tomas);
        testBusiness.addMember(lovisa);
        testBusiness.addMember(sara);
        testBusiness.addModeratorMember(bert, lars);
        testBusiness.addModeratorMember(bert, anna);
        testBusiness.addModeratorMember(bert, tomas);
        testBusiness.addModeratorMember(bert, lovisa);
        testBusiness.addModeratorMember(bert, sara);
        testBusiness.removeMember(lars);
        testBusiness.removeMember(anna);
        testBusiness.removeMember(tomas);
        testBusiness.removeMember(lovisa);
        testBusiness.removeModeratorMember(bert, sara);
        testBusiness.removeMember(bert);

        answers4 = new ArrayList<String>();

        answers4.add("Bert");
        answers4.add("Bert,Sara,");
        answers4.add("Bert,");

        printTestResults(testBusiness, answers4, "TEST 4");
    }

    private void printTestResults(BusinessProfile testBusiness, ArrayList<String> answers, String testName) {
        String members = "";
        String moderatorMembers = "";
        System.out.println(testName + " RESULTS ----------------------------------------------------");
        System.out.println("The creator of '" + testBusiness.getName() + "' is: " + testBusiness.getCreatorMember().getName());
        System.out.print("The members of '" + testBusiness.getName() + "' are: ");
        for (int i = 0; i < testBusiness.getMembers().size(); i++) {
            members = members + testBusiness.getMembers().get(i).getName() + ",";
        }
        System.out.print(members);
        System.out.println("");
        System.out.print("The moderator members of '" + testBusiness.getName() + "' are: ");
        for (int i = 0; i < testBusiness.getModeratorMembers().size(); i++) {
            moderatorMembers = moderatorMembers + testBusiness.getModeratorMembers().get(i).getName() + ",";
        }
        System.out.println(moderatorMembers);

        System.out.println(testName + " ANSWERS:");
        System.out.println("The creator of '" + testBusiness.getName() + "' should be: " + answers.get(0));
        System.out.println("The members of '" + testBusiness.getName() + "' should be: " + answers.get(1));
        System.out.println("The moderator members of '" + testBusiness.getName() + "' should be: " + answers.get(2));

        System.out.println(testName + " SUMMARY:");
        if (testBusiness.getCreatorMember().getName() == answers.get(0)) {
            System.out.println("CORRECT: The business creators match");
        } else {
            System.out.println("WRONG: The business creator does not match");
        }

        if (members.equals(answers.get(1))) {
            System.out.println("CORRECT: The members match");
        } else {
            System.out.println("WRONG: The members does not match");
        }

        if (moderatorMembers.equals(answers.get(2))) {
            System.out.println("CORRECT: The moderator members match");
        } else {
            System.out.println("WRONG: The members moderator does not match");
        }
        System.out.println("----------------------------------------------------");
    }
}
