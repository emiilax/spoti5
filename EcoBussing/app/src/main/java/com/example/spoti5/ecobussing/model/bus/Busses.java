package com.example.spoti5.ecobussing.model.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by emilaxelsson on 28/09/15.
 *
 * A class that contains all the busses. This makes it very easy to access the buses. This is
 * not a solution we are happy with, but it works good in this project
 */
public final class Busses {


    // Electric busses
    public static Bus epo131 = new Bus("Ericsson$100020", "YV3U0V222FA100020", "EPO131", "0013951349f5");
    public static Bus epo136 = new Bus("Ericsson$100021", "YV3U0V222FA100021", "EPO136", "04f0211009df");
    public static Bus epo143 = new Bus("Ericsson$100022", "YV3U0V222FA100022", "EPO143", "04f0211009e8");

    // Hybrid busses
    public static Bus eog604 = new Bus("Ericsson$171164", "YV3T1U22XF1171164", "EOG604", "04f0211009b8");
    public static Bus eog606 = new Bus("Ericsson$171234", "YV3T1U225F1171234", "EOG606", "0013951349f7");
    public static Bus eog616 = new Bus("Ericsson$171235", "YV3T1U227F1171235", "EOG616", "04f02110095b");
    public static Bus eog622 = new Bus("Ericsson$171327", "YV3T1U221F1171327", "EOG622", "04f021100953");
    public static Bus eog627 = new Bus("Ericsson$171328", "YV3T1U223F1171328", "EOG627", "04f0211009b9");
    public static Bus eog631 = new Bus("Ericsson$171329", "YV3T1U225F1171329", "EOG631", "001395143bf2");
    public static Bus eog634 = new Bus("Ericsson$171330", "YV3T1U223F1171330", "EOG634", "04f0211009b7");

    // The simulated bus
    public static Bus simulated = new Bus("Ericsson$Vin_Num_001", "", "", "");


    // List of the busses
    public static List<Bus> theBusses = new ArrayList<>(Arrays.asList(epo131, epo136, epo143,
            eog604, eog606, eog616, eog622, eog627, eog631, eog634, simulated));



}


