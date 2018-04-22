package com.themaster.android.needlemanager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NeedleStore
{
    private static NeedleStore sNeedleStore;
    public static final int Capacity = 68;
    public static NeedleStore get()
    {
        if(sNeedleStore==null)
            sNeedleStore=new NeedleStore();
        return sNeedleStore;
    }
    private NeedleStore()
    {
        mNeedles=new ArrayList<Needle>(Capacity);
        for(int i=0;i<Capacity;i++)
        {
            Needle needle=new Needle();
            mNeedles.add(needle);
        }

    }
    private List<Needle> mNeedles;

    public List<Needle> getNeedles()
    {
        return mNeedles;
    }

    public Needle getNeedle(UUID id)
    {
        for(Needle n:mNeedles)
            if(n.getID().equals(id))
                return n;
        return null;
    }
}
