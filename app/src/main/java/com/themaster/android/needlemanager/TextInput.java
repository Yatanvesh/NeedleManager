package com.themaster.android.needlemanager;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextInput
{
    InputStream mNMSInput;
    Scanner mScanner;
    TextInput(Context context)
    {
        AssetManager am=context.getAssets();
        mNMSInput=null;
        try {
            mNMSInput = am.open("nms_data.txt");
            mScanner=new Scanner(mNMSInput);
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void BindInput(Needle needle)
    {
        String s;

        s=mScanner.next();
        needle.setIndex(s);s=mScanner.next();
        needle.setOperatorID(s); s=mScanner.next();
        needle.setOperatorName(s);s=mScanner.next();
        needle.setMachineID(s);s=mScanner.next();
        needle.setMachineType(s);s=mScanner.next();
        needle.setLine(s);s=mScanner.next();
        needle.setStyleInformation(s);s=mScanner.next();
        needle.setOperationName(s);s=mScanner.next();
        needle.setFabricType(s);s=mScanner.next();
        needle.setNeedleToBeProcured(s);s=mScanner.next();
        needle.setSizeOfNeedle(s);s=mScanner.next();
        needle.setRefusedNeedleType(s);s=mScanner.next();
        needle.setBoxNo(s);s=mScanner.next();
        needle.setColumnNo(s);s=mScanner.next();
        needle.setLifeOfNeedle(s);s=mScanner.next();
        needle.setTimeOfDay(s);s=mScanner.next();
        needle.setIssuingTime(s);s=mScanner.next();
        needle.setDateOfProcurement(s);s=mScanner.next();
        needle.setDateOfReplacement(s);s=mScanner.next();
        needle.setTimeOfReplacement(s);s=mScanner.next();
        needle.setTimeOfProcurement(s);
    }
}