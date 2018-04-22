package com.themaster.android.needlemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by boisahab on 21/4/18.
 */

public class SearchActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new SearchFragment();
    }

    public static Intent newIntent(Context context)
    {
        Intent i=new Intent(context,SearchActivity.class);
        return i;
    }
}
