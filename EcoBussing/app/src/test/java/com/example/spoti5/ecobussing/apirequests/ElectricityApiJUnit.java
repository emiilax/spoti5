package com.example.spoti5.ecobussing.apirequests;

import com.example.spoti5.ecobussing.controller.apirequest.ECIApi;
import com.example.spoti5.ecobussing.model.bus.Busses;
import com.example.spoti5.ecobussing.model.jsonclasses.eciapi.EARespond;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by emilaxelsson on 14/10/15.
 */
public class ElectricityApiJUnit {

    ECIApi eci = new ECIApi();


    @Test(expected = IllegalArgumentException.class)
    public void getRequestUrlWithAWrongDwgInputShouldThrowIllegalArgumentException()
            throws IllegalArgumentException{

        String GPS2 = "Ericsson$GPS2";
        // Not a correct dwg
        String dwg = "2";

        long t2 = System.currentTimeMillis();
        long t1 = t2-(1000*5);

        String url = eci.getRequestUrl(true, GPS2, dwg, t1, t2);

    }

    @Test(expected = IOException.class)
    public void getResponseFromECIApiWithBadInputShouldThrowIOEception() throws IOException{

        String response = eci.getEAResponse("www.google.se");

    }

    @Test
    public void getResponseWithGPSDataAndPutItIntoAList() {

        try{
            List<EARespond> eaRespondList = eci.getGPSInfo(Busses.simulated.getDwg());

            assertNotNull(eaRespondList);

        }catch(IOException w){
            fail("Should not have happend, the input is correct. Check internet connection, or" +
                    "check that the simulated bus is still up and going");
        }

    }

    @Test
    public void getSpecificValueFromTheGPSResponse(){
        try {
            List<EARespond> eaRespondList = eci.getGPSInfo(Busses.simulated.getDwg());

            double latitude = eci.getEAValue("Latitude2_Value", eaRespondList);
            double longitude = eci.getEAValue("Longitude2_Value", eaRespondList);

            // Gothenburg is within 57 <= lat<=58.5, expect a value between that
            assertTrue(latitude >= 57 && latitude <= 58.5);

            // Gothenburg is within 11.5 <= long <=12.4, expect a value between that
            assertTrue(longitude >= 11.5 && longitude <= 12.4);

        } catch (IOException e){
            fail("Should not have happend, the input is correct. Check internet connection, or" +
                    "check that the simulated bus is still up and going");
        }

    }



}
