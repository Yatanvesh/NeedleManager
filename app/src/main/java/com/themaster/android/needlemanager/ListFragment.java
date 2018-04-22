package com.themaster.android.needlemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ListFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private NeedleAdapter mNeedleOperatorAdapter;
    private NeedleAdapter mNeedleMachineAdapter;
    public  enum AdapterType{OperatorDetails,MachineDetails} ;
    private AdapterType mCurrentAdpater;
    private static AdapterType sLastKnownAdpater;
    public static final String NEEDLE_LIST_FRAGMENT_ARGUMENTS="com.themaster.android.needlemanager.NEEDLE_LIST_FRAGMENT_ARGUMENTS";
    MenuItem mMenuSwitchTitle;

    private TextView mID;
    private TextView mName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_needle_list,container,false);
        mRecyclerView= v.findViewById(R.id.needle_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mID=v.findViewById(R.id.fragment_needle_list_id);
        mName=v.findViewById(R.id.fragment_needle_list_name);

        return v;
    }

    public void updateUI()
    {
        NeedleStore needleStore=NeedleStore.get();
        List<Needle> needles=needleStore.getNeedles();
        if(mNeedleOperatorAdapter ==null)
        {
            mNeedleOperatorAdapter =new NeedleAdapter(needles,AdapterType.OperatorDetails);
           // mRecyclerView.setAdapter(mNeedleOperatorAdapter);
        }
        else
        {
            mNeedleOperatorAdapter.setNeedles(needles);
            mNeedleOperatorAdapter.notifyDataSetChanged();
        }
        if(mNeedleMachineAdapter==null)
        {
            mNeedleMachineAdapter=new NeedleAdapter(needles,AdapterType.MachineDetails);
        }
        else
        {
            mNeedleMachineAdapter.setNeedles(needles);
            mNeedleMachineAdapter.notifyDataSetChanged();
        }

        if(mCurrentAdpater.equals(AdapterType.MachineDetails))
        {
            mCurrentAdpater=AdapterType.OperatorDetails;
            flipAdapter(mMenuSwitchTitle);
        }
        else
        {
            mCurrentAdpater=AdapterType.MachineDetails;
            flipAdapter(mMenuSwitchTitle);
        }
    }


    private class NeedleHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Needle mNeedle;
        private TextView mSno;
        private TextView mID;
        private TextView mName;

        public NeedleHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mSno=itemView.findViewById(R.id.needle_list_view_serial);
            mID =itemView.findViewById(R.id.needle_list_view_id);
            mName=itemView.findViewById(R.id.needle_list_view_name);
        }
        public void bindNeedle(Needle needle,AdapterType adapterType)
        {
            mNeedle=needle;
            mSno.setText(mNeedle.getIndex());
            switch (adapterType)
            {
                case OperatorDetails:
                    mID.setText(mNeedle.getOperatorID());
                    mName.setText(removeUnderscore(mNeedle.getOperatorName()));
                    break;
                case MachineDetails:
                    mID.setText(removeUnderscore(mNeedle.getMachineID()));
                    mName.setText(mNeedle.getMachineType());
                    break;
                default:break;
            }

        }

        @Override
        public void onClick(View view)
        {
            Intent i= DetailActivity.newIntent(getContext(),mNeedle.getID());
            startActivity(i);

        }

    }

    private class NeedleAdapter extends RecyclerView.Adapter<NeedleHolder>
    {
        private List<Needle> mNeedles;
        private AdapterType mAdapterType;
        public NeedleAdapter(List<Needle> needles,AdapterType type)
        {
            mNeedles=needles;
            mAdapterType=type;
        }

        @Override
        public NeedleHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.needle_list_view,parent,false);
            return new NeedleHolder(view);
        }

        @Override
        public void onBindViewHolder(NeedleHolder holder, int position)
        {
            Needle needle=mNeedles.get(position);
            holder.bindNeedle(needle,mAdapterType);
        }
        @Override
        public int getItemCount()
        {
            return mNeedles.size();
        }
        public void setNeedles (List<Needle> needles)
        {
            mNeedles=needles;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(mCurrentAdpater==null)
        mCurrentAdpater= (AdapterType) getActivity().getIntent().getSerializableExtra(NEEDLE_LIST_FRAGMENT_ARGUMENTS);

        if(mCurrentAdpater!=null)sLastKnownAdpater=mCurrentAdpater;
       // Toast.makeText(getContext(),mCurrentAdpater.toString(),Toast.LENGTH_SHORT).show();
        if(mCurrentAdpater==null&&sLastKnownAdpater!=null)
            mCurrentAdpater=sLastKnownAdpater;
        ///if(mCurrentAdpater==null)
           // mCurrentAdpater=AdapterType.OperatorDetails;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
        mMenuSwitchTitle=  menu.getItem(0);
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return flipAdapter(item);

    }

    boolean flipAdapter(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_flip_details:
                if(mCurrentAdpater.equals(AdapterType.OperatorDetails))
                {
                    mRecyclerView.setAdapter(mNeedleMachineAdapter);
                    mCurrentAdpater=AdapterType.MachineDetails;
                    mID.setText(R.string.machine_id);
                    mName.setText(R.string.machine_type);
                    item.setTitle(R.string.operator_view);

                }
                else
                {
                    mRecyclerView.setAdapter(mNeedleOperatorAdapter);
                    mCurrentAdpater=AdapterType.OperatorDetails;
                    mID.setText(R.string.operator_id);
                    mName.setText(R.string.operator_name);
                    item.setTitle(R.string.machine_view);

                }

                sLastKnownAdpater=mCurrentAdpater;
            default:     return super.onOptionsItemSelected(item);
        }
    }
    String removeUnderscore(String s)
    {

        s= s.replaceAll("_", " ");
        return s;
    }

}
