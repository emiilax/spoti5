package Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IProfile {

    public String getUsername();
    public String getName();
    public double getDistance();

    public void setUsername(String username);
    public void setName(String name);
    public void incDistance(double addedDistance);
    public void decDistance(double reducedDistance);

}