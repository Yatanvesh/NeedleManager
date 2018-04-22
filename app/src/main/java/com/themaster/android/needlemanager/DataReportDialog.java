package com.themaster.android.needlemanager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class DataReportDialog extends DialogFragment
{
    public static final String DATA_REPORT_ARGUMENTS="com.themaster.android.needlemanager.DATA_REPORT_ARGUMENTS";
    private ViewToShow mViewToShow;

    private TextView mStyleRefusalsTextView;
    private TextView mLineRefusalsTextView;
    private TextView mTimeConsumedTextView;
    private String mStyleWithMostRefusals;
    private String mLineWithMostRefusals;
    private String mTimeConsumed;

    private RecyclerView mRefusalsRecyclerView;
    private RefusalsAdapter mRefusalsAdapter;
    Map<String ,Float> mRefusalsMap;
    private TextView mKeyTextView;
    private TextView mValueTextView;

    private GraphView mGraphView;

    private TextView mBrokenTextView,mBentTextView,mBluntTextView,mTimeConsumedIssuing;
    RefusalCounter mRefusalCounter;
    public static final String BLUNT="BLUNT", BENT="BENT", BROKEN="BROKEN";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v=null;
        switch (mViewToShow)
        {
            case General:
                  v=inflater.inflate(R.layout.fragment_data_report_needle_refused,container,false);
                mStyleRefusalsTextView=v.findViewById(R.id.fragment_data_report_style_refusals);
                mLineRefusalsTextView=v.findViewById(R.id.fragment_data_report_line_refusals);
                mTimeConsumedTextView=v.findViewById(R.id.fragment_data_report_time_consumed);
                mStyleRefusalsTextView.setText(mStyleWithMostRefusals);
                mLineRefusalsTextView.setText(mLineWithMostRefusals);
                mTimeConsumedTextView.setText(mTimeConsumed);

                break;
            case RefusalsByMachine:
                v = InitialiseRecyclerView(inflater, container);
                mValueTextView.setText(R.string.machine_type);
                mKeyTextView.setText(R.string.no_of_refusals);
                break;
            case RefusalsByOperator:
                v = InitialiseRecyclerView(inflater, container);
                mValueTextView.setText(R.string.operator_name);
                mKeyTextView.setText(R.string.no_of_refusals);
                break;
            case Graph:
                v=inflater.inflate(R.layout.fragment_data_report_graph,container,false);

                mGraphView =  v.findViewById(R.id.graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, 0),
                        new DataPoint(30, mRefusalCounter.getNoOfNeedles(BROKEN) ),
                });
                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, 0),
                        new DataPoint(30, mRefusalCounter.getNoOfNeedles(BLUNT)),
                });
                LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, 0),
                        new DataPoint(30, mRefusalCounter.getNoOfNeedles(BENT)),
                });
                series.setColor( getResources().getColor(R.color.button_background) );
                series2.setColor(getResources().getColor(R.color.colorAccent));
                series3.setColor(getResources().getColor(R.color.home_title));


                mGraphView.addSeries(series);
                mGraphView.addSeries(series2);
                mGraphView.addSeries(series3);


                mGraphView.getViewport().setXAxisBoundsManual(true);
                mGraphView.getViewport().setMinX(0);
                mGraphView.getViewport().setMaxX(35);

                //mGraphView.setScaleX(0.7f);
               // mGraphView.setRotation(90);
                series.setTitle(BROKEN +" Needles");
                series2.setTitle(BLUNT+" Needles");
                series3.setTitle(BENT+" Needles");                mGraphView.getLegendRenderer().setVisible(true);
                mGraphView.getLegendRenderer().setVisible(true);
                mGraphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                mGraphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            return super.formatLabel(value, isValueX) +"\nDays";
                        } else {
                            // show currency for y values
                            return super.formatLabel(value, isValueX) ;
                        }
                    }
                });
                break;
            case NeedleRefusalDetail:
                v=inflater.inflate(R.layout.fragment_data_report_needle_refusal_details,container,false);
                mBrokenTextView=v.findViewById(R.id.fragment_data_report_needle_refusal_details_broken_needles);
                mBluntTextView=v.findViewById(R.id.fragment_data_report_needle_refusal_details_blunt_needles);
                mBentTextView=v.findViewById(R.id.fragment_data_report_needle_refusal_details_bent_needles);
                mTimeConsumedIssuing=v.findViewById(R.id.fragment_data_report_needle_refusal_details_time_consumed);

                mBrokenTextView.setText(mRefusalCounter.getNoOfNeedles(BROKEN) + " Needles");
                mBentTextView.setText(mRefusalCounter.getNoOfNeedles(BENT) + " Needles");
                mBluntTextView.setText(mRefusalCounter.getNoOfNeedles(BLUNT) + " Needles");
                mTimeConsumedIssuing.setText(getTimeConsumed()+" seconds");

                LinearLayout linearLayout=v.findViewById(R.id.fragment_data_report_needle_refusal_details_linear_layout);
                Map<Integer,Integer> map;
                Iterator itr;
                map=mRefusalCounter.getNoOfNeedlesByTypeAndSize(BROKEN);
                itr=map.entrySet().iterator();

                TextView textView=new TextView(getContext());
                textView.setText("Broken Needles:");
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(30);
                textView.setPadding(5,5,5,5);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                linearLayout.addView(textView);

                while (itr.hasNext())
                {
                    TextView needleTextView=new TextView(getContext());
                    needleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    needleTextView.setTextSize(25);

                    Map.Entry pair=(Map.Entry)itr.next();
                    needleTextView.setText("Size:   " +pair.getKey().toString() +" ------- "+pair.getValue().toString()+ "  Needles");

                    linearLayout.addView(needleTextView);

                }

                map=mRefusalCounter.getNoOfNeedlesByTypeAndSize(BENT);
                itr=map.entrySet().iterator();

                TextView textView2=new TextView(getContext());
                textView2.setText("Broken Needles:");
                textView2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView2.setTextSize(30);
                textView2.setPadding(5,5,5,5);
                textView2.setTypeface(Typeface.DEFAULT_BOLD);
                textView2.setGravity(Gravity.CENTER);
                textView2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                linearLayout.addView(textView2);

                while (itr.hasNext())
                {
                    TextView needleTextView=new TextView(getContext());
                    needleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    needleTextView.setTextSize(25);

                    Map.Entry pair=(Map.Entry)itr.next();
                    needleTextView.setText("Size:   " +pair.getKey().toString() +" ------- "+pair.getValue().toString()+ "  Needles");

                    linearLayout.addView(needleTextView);

                }

                map=mRefusalCounter.getNoOfNeedlesByTypeAndSize(BLUNT);
                itr=map.entrySet().iterator();

                TextView textView3=new TextView(getContext());
                textView3.setText("Broken Needles:");
                textView3.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView3.setTextSize(30);
                textView3.setPadding(5,5,5,5);
                textView3.setTypeface(Typeface.DEFAULT_BOLD);
                textView3.setGravity(Gravity.CENTER);
                textView3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                linearLayout.addView(textView3);

                while (itr.hasNext())
                {
                    TextView needleTextView=new TextView(getContext());
                    needleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    needleTextView.setTextSize(25);

                    Map.Entry pair=(Map.Entry)itr.next();
                    needleTextView.setText("Size:   " +pair.getKey().toString() +" ------- "+pair.getValue().toString()+ "  Needles");

                    linearLayout.addView(needleTextView);

                }
            default:
                break;
        }
        if(v==null)
            v=inflater.inflate(R.layout.fragment_data_report_needle_refused,container,false);


    return v;
    }

    @NonNull
    private View InitialiseRecyclerView(LayoutInflater inflater, @Nullable ViewGroup container)
    {
        View v;
        v=inflater.inflate(R.layout.fragment_data_report_recycler_list_view,container,false);
        Iterator itr= mRefusalsMap.entrySet().iterator();
        List<StringAndFloat> refusalsList=new ArrayList<>();
        mKeyTextView=v.findViewById(R.id.fragment_data_report_recycler_list_view_key);
        mValueTextView=v.findViewById(R.id.fragment_data_report_recycler_list_view_value);

        while (itr.hasNext())
        {
            Map.Entry pair=(Map.Entry)itr.next();
            StringAndFloat s=new StringAndFloat( pair.getKey().toString(),  Float.parseFloat(pair.getValue().toString() ));
            refusalsList.add(s);
        }
        mRefusalsRecyclerView =v.findViewById(R.id.fragment_data_report_machine_refusal_recycler_view);
        mRefusalsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefusalsAdapter =new RefusalsAdapter(refusalsList);
        mRefusalsRecyclerView.setAdapter(mRefusalsAdapter);
        return v;
    }

    public enum ViewToShow{General,RefusalsByMachine,RefusalsByOperator,Graph,NeedleRefusalDetail};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewToShow= (ViewToShow)getArguments().getSerializable(DATA_REPORT_ARGUMENTS);

        switch (mViewToShow)
        {
            case General:
                StringAndFloat StyleRefusals=getStyleWithMostRefusals();
                mStyleWithMostRefusals= StyleRefusals.mString+" - "+StyleRefusals.mFloat+ " Refusals";
                StringAndFloat LineRefusals=getLineWithMostRefusals();
                mLineWithMostRefusals= LineRefusals.mString + " - "+LineRefusals.mFloat+" Refusals";
                mTimeConsumed= getTimeConsumed()+" seconds";

                break;
            case RefusalsByMachine:
                mRefusalsMap = getMachineRefusals();
                break;
            case RefusalsByOperator:
                mRefusalsMap = getOperatorRefusals();
                break;
            case Graph:
                mRefusalCounter=new RefusalCounter();

                break;
            case NeedleRefusalDetail:
                mRefusalCounter=new RefusalCounter();



                break;
            default:break;

        }

    }

    private class StringAndFloat
    {
        public String mString;
        public Float mFloat;

        public StringAndFloat(String s,Float i)
        {
            mString=s;
            mFloat=i;
        }
    }
    private StringAndFloat getStyleWithMostRefusals()
    {
        float style1=0,style2=0;
        String stringStyle1="32908-0052-H2",stringStyle2="KB0KB04374_H";

        for(Needle needle:NeedleStore.get().getNeedles() )
        {
            float inverse;
            float life=  Float.parseFloat(needle.getLifeOfNeedle());
            if(life!=0)
                inverse=1/life;
            else inverse=0;
            if(needle.getStyleInformation().equals(stringStyle1))
                style1+=inverse;
            else
                style2+=inverse;
        }
        float fRefusals=0;
        for(Needle needle:NeedleStore.get().getNeedles() )
        {
            if(needle.getStyleInformation().equals(style1>style2?stringStyle1:stringStyle2))
            {
                float divider=Float.parseFloat( needle.getLifeOfNeedle()) ;
                if(divider!=0)
                    fRefusals+=  30f/divider  ;
            }
        }
        StringAndFloat returner= new StringAndFloat( DetailFragment.removeUnderscore( style1>style2?stringStyle1:stringStyle2),fRefusals);
        return returner;
    }

    private StringAndFloat getLineWithMostRefusals()
    {
        Map<String,Float> Refusals=new HashMap<String,Float>();
        // Log.d("boi",  " .....................................................");


        for (Needle needle:NeedleStore.get().getNeedles())
        {
            float inverse;
            float life=  Float.parseFloat(needle.getLifeOfNeedle());
            if(life!=0)
                inverse=1/life;
            else inverse=0;

            if(Refusals.containsKey(needle.getLine()))
            {
                Refusals.put(needle.getLine(),Refusals.get(needle.getLine())+inverse );
                // Log.d("boi",  Refusals.get(needle.getLine())+" ");

            }
            else
                Refusals.put(needle.getLine(),inverse);
        }
        Needle n=NeedleStore.get().getNeedles().get(0);

        Iterator itr=Refusals.entrySet().iterator();

        Float highestValue=-1f;
        Map.Entry highestPair=null;
        while (itr.hasNext())
        {
            Map.Entry pair=(Map.Entry)itr.next();

            if(highestValue< (Float)pair.getValue())
                highestPair=pair;

            // Log.d("boi",highestValue+" ");

            // Log.d("boi",pair.getKey() +"   "+pair.getValue());
        }

        // Log.d("boi","sach ka saamna:   "+highestPair.getKey() +"   "+highestPair.getValue());
        float NoOfRefusals=0;

        for (Needle needle:NeedleStore.get().getNeedles())
        {
            if(needle.getLine().equals(highestPair.getKey().toString()))
            {

                float divider=Float.parseFloat( needle.getLifeOfNeedle()) ;
                if(divider!=0)
                    NoOfRefusals+=30f/divider;
            }
        }
        StringAndFloat stringAndFloat=new StringAndFloat(highestPair.getKey().toString(),NoOfRefusals);
        return stringAndFloat;
    }
    int getTimeConsumed()
    {
        int TimeConsumed=0;
        for (Needle needle:NeedleStore.get().getNeedles())
        {
            String time=needle.getIssuingTime();

            String times[]=new String[2];
            times= time.split(":");
            int Time=Integer.parseInt(times[1]);
            TimeConsumed+=Time;
        }
        // Log.d("boi", TimeConsumed+" ");
        return TimeConsumed;

    }

    private class RefusalsHolder extends RecyclerView.ViewHolder
    {
        private TextView mKeyTextView;
        private TextView mValueTextView;
        public RefusalsHolder(View itemView)
        {
            super(itemView);
            mKeyTextView=itemView.findViewById(R.id.refusals_view_key);
            mValueTextView=itemView.findViewById(R.id.refusals_view_value);
        }
        public void bindViews(StringAndFloat stringAndFloat)
        {
            mKeyTextView.setText( DetailFragment.removeUnderscore(  stringAndFloat.mString ));
            mValueTextView.setText(stringAndFloat.mFloat.toString());
        }
    }
    private class RefusalsAdapter extends RecyclerView.Adapter<RefusalsHolder>
    {
        private List<StringAndFloat> mRefusals;
        public RefusalsAdapter(List<StringAndFloat> refusals)
        {
            mRefusals=refusals;


        }
        @Override
        public RefusalsHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View v=layoutInflater.inflate(R.layout.refusals_view,parent,false);
            return new RefusalsHolder(v);
        }
        @Override
        public void onBindViewHolder(RefusalsHolder holder, final int position)
        {
            StringAndFloat s=mRefusals.get(position);
            holder.bindViews(s);
        }
        @Override
        public int getItemCount()
        {
            return mRefusals.size();
        }
    }
    public Map<String,Float> getMachineRefusals()
    {
        Map<String,Float> MachineRefusals=new HashMap<String,Float>();

        for (Needle needle:NeedleStore.get().getNeedles())
            if(!MachineRefusals.containsKey(needle.getMachineType()))
                MachineRefusals.put(needle.getMachineType(),0f);

        for (Needle needle:NeedleStore.get().getNeedles())
        {   Float life=Float.parseFloat( needle.getLifeOfNeedle() );
            float refusedInMonth;
            if(life!=0)
                refusedInMonth=30f/life;
            else refusedInMonth=0;

            MachineRefusals.put(needle.getMachineType(),MachineRefusals.get(needle.getMachineType())+refusedInMonth);
        }

        return MachineRefusals;
    }
    public Map<String,Float> getOperatorRefusals()
    {
        Map<String,Float> OperatorRefusals=new HashMap<String,Float>();


        for (Needle needle:NeedleStore.get().getNeedles())
        {   Float life=Float.parseFloat( needle.getLifeOfNeedle() );
            float refusedInMonth;
            if(life!=0)
                refusedInMonth=30f/life;
            else refusedInMonth=0;

            OperatorRefusals.put(needle.getOperatorName(),refusedInMonth);
        }

        return OperatorRefusals;
    }

    private class RefusalCounter
    {
        public Vector<StringAndInteger> RefusedNeedles;

        public RefusalCounter()
        {
            RefusedNeedles=new Vector<StringAndInteger>();
            for (Needle needle:NeedleStore.get().getNeedles())
            {
                StringAndInteger stringAndInteger=new StringAndInteger(needle.getRefusedNeedleType(),Integer.parseInt(needle.getSizeOfNeedle()));
                RefusedNeedles.add(stringAndInteger);
            }
        }

       public int getNoOfNeedles(String type)
        {
            Iterator itr=RefusedNeedles.iterator();
            int Sum=0;

        while (itr.hasNext())
        {
            StringAndInteger stringAndInteger=(StringAndInteger) itr.next();
           // Log.d("boi",stringAndInteger.mKey +"   "+Sum );
            if(stringAndInteger.mKey.equals(type))
            Sum++;
        }
        return Sum;
        }
        public Map<Integer,Integer> getNoOfNeedlesByTypeAndSize(String type)
        {
            Iterator itr=RefusedNeedles.iterator();
            Map<Integer,Integer> CounterMap=new HashMap<>();
            while (itr.hasNext())
            {
                StringAndInteger stringAndInteger=(StringAndInteger) itr.next();
                if(stringAndInteger.mKey.equals(type))
                {
                    if(!CounterMap.containsKey(stringAndInteger.mValue))
                    {
                        CounterMap.put(stringAndInteger.mValue,1);
                    }
                    else
                        CounterMap.put(stringAndInteger.mValue,CounterMap.get(stringAndInteger.mValue)+1);
                }
            }
            return CounterMap;
        }
    }

     class StringAndInteger
    {
        public String mKey;
        public Integer mValue;
        StringAndInteger(String s,Integer i)
        {
            mKey=s;
            mValue=i;
        }
    }
    class IntegerAndInteger
    {
        public Integer mKey;
        public Integer mValue;
        IntegerAndInteger(Integer s,Integer i)
        {
            mKey=s;
            mValue=i;
        }
    }
}
