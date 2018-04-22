package com.themaster.android.needlemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;



public class ListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
    return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       /* TextInput textInput=new TextInput(getApplicationContext());
        for (int i=0;i<NeedleStore.Capacity;i++)
            textInput.BindInput(NeedleStore.get().getNeedles().get(i));
            */

    }
    public static Intent newIntent(Context context, ListFragment.AdapterType adapterType)
    {
        Intent i=new Intent(context,ListActivity.class);
        i.putExtra(ListFragment.NEEDLE_LIST_FRAGMENT_ARGUMENTS,adapterType);
        return i;
    }
}
