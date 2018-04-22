package com.themaster.android.needlemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment
{
    private Button mSearch;
    private Button mOperatorDetails;
    private Button mMachineDetails;
    private Button mDataReport;
    private Button mExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_home,container,false);

        mSearch=v.findViewById(R.id.fragment_home_search);
        mSearch.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent i=SearchActivity.newIntent(getContext());
                        startActivity(i);

                    }
                }
        );

        mOperatorDetails=v.findViewById(R.id.fragment_home_operator_details);
        mOperatorDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i= ListActivity.newIntent(getActivity(), ListFragment.AdapterType.OperatorDetails);
                startActivity(i);
            }
        });

        mMachineDetails=v.findViewById(R.id.fragment_home_machine_details);
        mMachineDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i= ListActivity.newIntent(getActivity(), ListFragment.AdapterType.MachineDetails);
                startActivity(i);
            }
        });

        mDataReport=v.findViewById(R.id.fragment_home_data_report);
        mDataReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(getContext(),DataReportActivity.class);
                startActivity(i);
            }
        });


        mExit=v.findViewById(R.id.fragment_home_exit);
        mExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getActivity().finish();
            }
        });

        return v;
    }
}
