package com.themaster.android.needlemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.UUID;

public class DetailActivity extends  SingleFragmentActivity
{
    private TextView mTextView;

    @Override
    protected Fragment createFragment()
    {
        return new DetailFragment();
    }

    public static Intent newIntent(Context context,UUID id)
    {
        Intent i=new Intent(context,DetailActivity.class);
        i.putExtra(DetailFragment.BUNDLE_SERIALISABLE,id);
        return i;
    }
}
