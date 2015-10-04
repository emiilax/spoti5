package com.example.spoti5.ecobussing;

import com.example.spoti5.ecobussing.Bus;
import com.example.spoti5.ecobussing.Profiles.BusinessProfile;
import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by hilden on 2015-09-30.
 */
public class BusinessProfileTest {

    private User bert;
    private User tomas;
    private User lars;
    private User anna;
    private User sara;
    private User lovisa;

    private BusinessProfile mcDonalds;

    public BusinessProfileTest() {
        lovisa = new User("f@gmail.com");
        sara = new User("e@gmail.com");
        anna = new User("d@gmail.com");
        lars = new User("c@gmail.com");
        tomas = new User("b@gmail.com");
        bert = new User("a@gmail.com");

        mcDonalds = new BusinessProfile("MC Donalds" ,bert);
    }

    public void test1(BusinessProfile testBusiness) {
        testBusiness.addMember(lars);
        testBusiness.addMember(anna);
        testBusiness.addMember(tomas);
        testBusiness.addModeratorMember(bert, tomas);
        testBusiness.addModeratorMember(bert, anna);
        testBusiness.removeModeratorMember(bert, anna);

        System.out.println("TEST 1 -----------------------------------------");
        printTestResults(testBusiness);
        System.out.println("ANSWERS 1 ----------------------------------------");
        System.out.println("Creator: Bert");
        System.out.println("Members: Lars, Anna, Tomas");
        System.out.println("Moderator members: Bert, Tomas");
        testBusiness.getMembers().clear();
        testBusiness.getModeratorMembers().clear();
    }

    public void test2(BusinessProfile testBusiness) {
        testBusiness.addMember(lars); //true
        testBusiness.addMember(anna); //true
        testBusiness.addMember(tomas); //true
        testBusiness.addModeratorMember(bert, tomas); //true
        testBusiness.addMember(lars); //false
        testBusiness.addMember(lovisa); //true
        testBusiness.removeMember(anna); //true
        testBusiness.addModeratorMember(anna, lars); //false
        testBusiness.addModeratorMember(tomas, lars); //false
        testBusiness.addModeratorMember(bert, sara); //false
        testBusiness.addModeratorMember(bert, lovisa); //true
        testBusiness.removeMember(tomas); //true
        testBusiness.removeModeratorMember(bert, bert); //false

        System.out.println("TEST 2 -----------------------------------------");
        printTestResults(mcDonalds);
        System.out.println("ANSWERS 2 ----------------------------------------");
        System.out.println("Creator: Bert");
        System.out.println("Members: Lars, Lovisa");
        System.out.println("Moderator members: Bert, Lovisa");
        testBusiness.getMembers().clear();
        testBusiness.getModeratorMembers().clear();
    }

    public void test3(BusinessProfile testBusiness) {
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

        System.out.println("TEST 3 -----------------------------------------");
        printTestResults(mcDonalds);
        System.out.println("ANSWERS 3 ----------------------------------------");
        System.out.println("Creator: Bert");
        System.out.println("Members: Lars, Anna, Tomas, Lovisa, Sara");
        System.out.println("Moderator members: Bert, Lars, Anna, Tomas, Lovisa, Sara");
        testBusiness.getMembers().clear();
        testBusiness.getModeratorMembers().clear();
    }

    public void test4(BusinessProfile testBusiness) {
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
        testBusiness.removeMember(bert);

        System.out.println("TEST 4 -----------------------------------------");
        printTestResults(mcDonalds);
        System.out.println("ANSWERS 4 ----------------------------------------");
        System.out.println("Creator: Bert");
        System.out.println("Members: ");
        System.out.println("Moderator members: Bert");

        testBusiness.getMembers().clear();
        testBusiness.getModeratorMembers().clear();
    }

    private void printTestResults(BusinessProfile testBusiness) {
        System.out.println("");

        System.out.print("The creator of '" + testBusiness.getName() + "' is: " + testBusiness.getCreatorMember());

        System.out.println("");

        String results = "";
        System.out.print("The members of '" + testBusiness.getName() + "' are: ");
        for (int i = 0; i < testBusiness.getMembers().size(); i++) {
            results = results + testBusiness.getMembers().get(i).getName() + ", ";
        }
        System.out.print(results);

        System.out.println("");
        results = "";
        System.out.print("The moderator members of '" + testBusiness.getName() + "' are: ");
        for (int i = 0; i < testBusiness.getModeratorMembers().size(); i++) {
            results = results + testBusiness.getModeratorMembers().get(i).getName() + ", ";
        }
        System.out.print(results);

    }
}
