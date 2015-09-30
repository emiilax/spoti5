package com.example.spoti5.ecobussing;

import com.example.spoti5.ecobussing.Profiles.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by emilaxelsson on 29/09/15.
 */
public class ToplistCalculator {

    public static void main(String [] args){

        List<User> users = new ArrayList<User>();
        // 2
        User one = new User("one", "e", "");
        one.incMoneySaved(200);
        users.add(one);
        // 5
        User two = new User("two", "e", "");
        two.incMoneySaved(10);
        users.add(two);
        //4
        User three = new User("three", "e", "");
        three.incMoneySaved(100);
        users.add(three);
        // 3
        User four = new User("four", "e", "");
        four.incMoneySaved(199);
        users.add(four);

        // 1
        User five = new User("five", "e", "");
        five.incMoneySaved(500);
        users.add(five);

        for(User usr: users){
            System.out.println(usr.getUsername());
        }
        System.out.println("----------");

        users = sortUsers(users);

        for(User usr: users){
            System.out.println(usr.getUsername());
        }

    }

    public static List<User> sortUsers(List<User> theUsers){

        Collections.sort(theUsers, new Comparator<User>() {
            public int compare(User usr1, User usr2) {
                return Double.compare(usr2.getMoneySaved(),usr1.getMoneySaved());
            }
        });

        return theUsers;
    }

}
