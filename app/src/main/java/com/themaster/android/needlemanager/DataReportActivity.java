package com.themaster.android.needlemanager;

import android.support.v4.app.Fragment;

public class DataReportActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new DataReportFragment();
    }
}
