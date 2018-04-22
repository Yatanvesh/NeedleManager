package com.themaster.android.needlemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

public class DataReportFragment extends Fragment
{
    private Spinner mSelectorSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
    View v=inflater.inflate(R.layout.fragment_data_report,container,false);
    mSelectorSpinner=v.findViewById(R.id.fragment_data_report_selector);
    mSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            android.support.v4.app.FragmentManager fm=getFragmentManager();
            DataReportDialog dialog=new DataReportDialog();
            Bundle args=new Bundle();
            switch (i)
            {
                case 0:
                    args.putSerializable(DataReportDialog.DATA_REPORT_ARGUMENTS, DataReportDialog.ViewToShow.General);

                    break;

                case 1:
                    args.putSerializable(DataReportDialog.DATA_REPORT_ARGUMENTS, DataReportDialog.ViewToShow.RefusalsByMachine);
                    break;
                case 2:
                    args.putSerializable(DataReportDialog.DATA_REPORT_ARGUMENTS, DataReportDialog.ViewToShow.RefusalsByOperator);
                    break;
                case 3:
                    args.putSerializable(DataReportDialog.DATA_REPORT_ARGUMENTS, DataReportDialog.ViewToShow.Graph);
                    break;
                case 4:
                    args.putSerializable(DataReportDialog.DATA_REPORT_ARGUMENTS, DataReportDialog.ViewToShow.NeedleRefusalDetail);


            }
            dialog.setArguments(args);
            fm.beginTransaction()
                    .replace(R.id.fragment_data_report_container,dialog)
                    .commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
        }
    });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}
