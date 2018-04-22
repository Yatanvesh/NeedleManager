package com.themaster.android.needlemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


public class DetailFragment extends Fragment
{
    Needle mNeedle;
    private TextView mOperatorID;
    private TextView mOperatorName;
    private TextView mMachineID;
    private TextView mMachineType;
    private TextView mLine;
    private TextView mStyleInformation;
    private TextView mOperationName;
    private TextView mFabricType;
    private TextView mNeedleToBeProcured;
    private TextView mSizeOfNeedle;
    private TextView mRefusedNeedleType;
    private TextView mBoxNo;
    private TextView mColumnNo;
    private TextView mLifeOfNeedle;
    private TextView mTimeOfDay;
    private TextView mIssuingTime;
    private TextView mDateOfProcurement;
    private TextView mDateOfReplacement;
    private TextView mTimeOfReplacement;
    private TextView mTimeOfProcurement;



    public static final String BUNDLE_SERIALISABLE ="com.themaster.android.needlemanager.BUNDLE_SERIALISABLE";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_needle_detail,container,false);
        UUID NeedleID=(UUID) getActivity().getIntent().getSerializableExtra(BUNDLE_SERIALISABLE);
        mNeedle=NeedleStore.get().getNeedle(NeedleID);
        if(mNeedle==null)
            Toast.makeText(getContext(),"needle is null, file io error",Toast.LENGTH_SHORT).show();

        mOperatorID=v.findViewById(R.id.needle_detail_view_operator_id);
        mOperatorName=v.findViewById(R.id.needle_detail_view_operator_name);
        mMachineID=v.findViewById(R.id.needle_detail_view_machine_id);
        mMachineType=v.findViewById(R.id.needle_detail_view_machine_type);
        mLine=v.findViewById(R.id.needle_detail_view_line);
        mStyleInformation=v.findViewById(R.id.needle_detail_view_style_information);
        mOperationName=v.findViewById(R.id.needle_detail_view_operation_name);
        mFabricType=v.findViewById(R.id.needle_detail_view_fabric_type);
        mNeedleToBeProcured=v.findViewById(R.id.needle_detail_view_needle_to_be_procured);
        mSizeOfNeedle=v.findViewById(R.id.needle_detail_view_size_of_needle);
        mRefusedNeedleType=v.findViewById(R.id.needle_detail_view_refused_needle_type);
        mBoxNo=v.findViewById(R.id.needle_detail_view_box_no);
        mColumnNo=v.findViewById(R.id.needle_detail_view_column_no);
        mLifeOfNeedle=v.findViewById(R.id.needle_detail_view_life_of_needle);
        mTimeOfDay=v.findViewById(R.id.needle_detail_view_time_of_day);
        mIssuingTime=v.findViewById(R.id.needle_detail_view_issuing_time);
        mDateOfProcurement=v.findViewById(R.id.needle_detail_view_date_of_procurement);
        mDateOfReplacement=v.findViewById(R.id.needle_detail_view_date_of_replacement);
        mTimeOfReplacement=v.findViewById(R.id.needle_detail_view_time_of_replacement);
        mTimeOfProcurement=v.findViewById(R.id.needle_detail_view_time_of_procurement);


        mOperatorID.setText(mNeedle.getOperatorID());
        mOperatorName.setText(removeUnderscore(mNeedle.getOperatorName()));
        mMachineID.setText( removeUnderscore(mNeedle.getMachineID()));
        mMachineType.setText(mNeedle.getMachineType());
        mLine.setText(mNeedle.getLine());
        mStyleInformation.setText(removeUnderscore( mNeedle.getStyleInformation()));
        mOperationName.setText( removeUnderscore(mNeedle.getOperationName()));
        mFabricType.setText(removeUnderscore(mNeedle.getFabricType()));
        mNeedleToBeProcured.setText(mNeedle.getNeedleToBeProcured());
        mSizeOfNeedle.setText(mNeedle.getSizeOfNeedle());
        mRefusedNeedleType.setText(mNeedle.getRefusedNeedleType());
        mBoxNo.setText(mNeedle.getBoxNo());
        mColumnNo.setText(mNeedle.getColumnNo());
        mLifeOfNeedle.setText(mNeedle.getLifeOfNeedle());

        mTimeOfDay.setText(removeUnderscore(mNeedle.getTimeOfDay()));
        mIssuingTime.setText(mNeedle.getIssuingTime());
        mDateOfProcurement.setText(mNeedle.getDateOfProcurement());
        mDateOfReplacement.setText(mNeedle.getDateOfReplacement());
        mTimeOfReplacement.setText(mNeedle.getTimeOfReplacement());
        mTimeOfProcurement.setText(mNeedle.getTimeOfProcurement());



        return v;
    }
   public static String removeUnderscore(String s)
    {

      s= s.replaceAll("_", " ");
       return s;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_back:
                getActivity().finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
