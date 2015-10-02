package com.example.spoti5.ecobussing.JsonClasses.EA;

/**
 * Created by emilaxelsson on 27/09/15.
 *
 * An instance of this class will be created when a requested answer has arrived.
 */
public class EARespond {

    /** The name of the request */
    private String resourceSpec;

    /** When the value was selected */
    private long timestamp;


    /** The answer */
    private String value;

    /** The bus "VIN"-number */
    private String gatewayId;


    public EARespond(String resourceSpec, long timeStamp, String value, String gatewayId){
        this.resourceSpec = resourceSpec;
        this.timestamp = timeStamp;

        this.value = value;
        this.gatewayId = gatewayId;
    }

    // Setters
    public void setResourceSpec(String resourceSpec) { this.resourceSpec = resourceSpec; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public void setValue(String value) { this.value = value; }

    public void setGatewayId(String gatewayId) { this.gatewayId = gatewayId;}


    //Getters
    public String getResourceSpec() { return resourceSpec; }

    public long getTimestamp() { return timestamp; }

    public String getValue() { return value; }

    public String getGatewayId() { return gatewayId; }

    public boolean equals(Object o){
        if(o instanceof EARespond){
            if (((EARespond)o).getResourceSpec().equals(this.resourceSpec)){
                return true;

            }else
                return false;
        }
        return false;
    }


    public String toString(){
        return "rescourceSpec: " + resourceSpec + "\n" +
                "time in milliseconds: " + timestamp + "\n" +
                "value: " + value + "\n" +
                "VIN-nr: "+ gatewayId;
    }
}
