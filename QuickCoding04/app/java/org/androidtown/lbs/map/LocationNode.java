package org.androidtown.lbs.map;

/**
 * Created by 유지은 on 2016-10-31.
 */

public class LocationNode {
    private double longtitude;
    private double latitude;
    private LocationNode lLink;
    private LocationNode rLink;

    public LocationNode()
    {}

    public LocationNode(double inputLatitude, double inputLongtitude)
    {
        longtitude = inputLongtitude;
        latitude = inputLatitude;
        lLink = null;
        rLink = null;
    }

    public double GetLatitude()
    {
        return this.latitude;
    }

    public double GetLongtitude()
    {
        return this.longtitude;
    }

    public void SetLatitude(double inputLatitude)
    {
        this.latitude = inputLatitude;
    }

    public void SetLongtiude(double inputLongtitude)
    {
        this.longtitude = inputLongtitude;
    }

    public void SetRLink(LocationNode node)
    {
           this.rLink = node;
    }

    public void SetLLink(LocationNode node)
    {
        this.lLink = node;
    }

    public LocationNode GetRLink()
    {
        return this.rLink;
    }

    public LocationNode GetLLink()
    {
        return this.lLink;
    }


}
