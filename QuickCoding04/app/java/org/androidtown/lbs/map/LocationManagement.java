package org.androidtown.lbs.map;

/**
 * Created by 유지은 on 2016-10-31.
 */

public class LocationManagement
{
    private LocationNode head;
    private LocationNode tail;
    int numOfNodes;

    public LocationManagement()
    {
        this.head = null;
        this.tail = null;
        numOfNodes=0;
    }

    public boolean IsEmptyList()
    {
        if(head == null)
            return true;
        return false;
    }

    public void AddLocationList(double inputLatitude, double inputLongtitude)
    {
        this.numOfNodes++;
        LocationNode newNode = new LocationNode(inputLatitude, inputLongtitude);
        if(this.IsEmptyList())
        {
            head = newNode;
            tail = newNode;
            return;
        }

        tail.SetRLink(newNode);
        newNode.SetLLink(this.tail);
        tail = newNode;
    }

    public void PrintLocationList()
    {
        LocationNode temp = this.head;

        if(IsEmptyList())
            return;

        while(temp != null)
        {
            System.out.println("Latitude : " + temp.GetLatitude() + "  Longtitude : " + temp.GetLongtitude());
            temp = temp.GetRLink();
        }
    }

}
