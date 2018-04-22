package com.themaster.android.needlemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

public class SearchFragment extends Fragment
{
    private SearchView mOperatorSearchView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_search,container,false);
        mOperatorSearchView=v.findViewById(R.id.fragment_search_operator_search);
       // mOperatorSearchView.setFocusedByDefault(true);
        mOperatorSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                for (Needle needle:NeedleStore.get().getNeedles())
                {
                    if(needle.getOperatorID().equals(s))
                    {
                        mOperatorSearchView.setQuery("",false);
                        mOperatorSearchView.clearFocus();

                        Intent i=DetailActivity.newIntent(getContext(),needle.getID());
                        startActivity(i);
                        return true;
                    }
                    else if(DetailFragment.removeUnderscore( needle.getMachineID()).equals(s))
                    {
                        mOperatorSearchView.setQuery("",false);
                        mOperatorSearchView.clearFocus();

                        Intent i=DetailActivity.newIntent(getContext(),needle.getID());
                        startActivity(i);
                        return true;
                    }
                    else
                    {
                        mOperatorSearchView.setQuery("No Results",false);
                        mOperatorSearchView.clearFocus();
                    }
                }
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                return false;
            }
        });
        return v;
    }
}
