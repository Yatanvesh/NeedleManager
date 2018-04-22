package com.themaster.android.needlemanager;


import java.util.UUID;

public class Needle
{

   private UUID mID;
   private String mIndex;

    public String getIndex()
    {
        return mIndex;
    }

    public UUID getID()
    {
        return mID;
    }

    public void setIndex(String index)
    {
        mIndex = index;
    }

    private String mOperatorID;
   private String mOperatorName;
   private String mMachineID;
   private String mMachineType;
   private String mLine;
   private String mStyleInformation;
    private String mOperationName;
    private String mFabricType;
    private String mNeedleToBeProcured;
    private String mSizeOfNeedle;
    private String mRefusedNeedleType;
    private String mBoxNo;
    private String mColumnNo;
    private String mLifeOfNeedle;
    private String mTimeOfDay;
    private String mIssuingTime;
    private String mDateOfProcurement;
    private String mDateOfReplacement;
    private String mTimeOfReplacement;
    private String mTimeOfProcurement;

    public Needle()
    {
        mID= UUID.randomUUID();
    }
    public String getOperatorID()
    {
        return mOperatorID;
    }

    public void setOperatorID(String operatorID)
    {
        mOperatorID = operatorID;
    }

    public String getOperatorName()
    {
        return mOperatorName;
    }

    public void setOperatorName(String operatorName)
    {
        mOperatorName = operatorName;
    }

    public String getMachineID()
    {
        return mMachineID;
    }

    public void setMachineID(String machineID)
    {
        mMachineID = machineID;
    }

    public String getMachineType()
    {
        return mMachineType;
    }

    public void setMachineType(String machineType)
    {
        mMachineType = machineType;
    }

    public String getLine()
    {
        return mLine;
    }

    public void setLine(String line)
    {
        mLine = line;
    }

    public String getStyleInformation()
    {
        return mStyleInformation;
    }

    public void setStyleInformation(String styleInformation)
    {
        mStyleInformation = styleInformation;
    }

    public String getOperationName()
    {
        return mOperationName;
    }

    public void setOperationName(String operationName)
    {
        mOperationName = operationName;
    }

    public String getFabricType()
    {
        return mFabricType;
    }

    public void setFabricType(String fabricType)
    {
        mFabricType = fabricType;
    }

    public String getNeedleToBeProcured()
    {
        return mNeedleToBeProcured;
    }

    public void setNeedleToBeProcured(String needleToBeProcured)
    {
        mNeedleToBeProcured = needleToBeProcured;
    }

    public String getSizeOfNeedle()
    {
        return mSizeOfNeedle;
    }

    public void setSizeOfNeedle(String sizeOfNeedle)
    {
        mSizeOfNeedle = sizeOfNeedle;
    }

    public String getRefusedNeedleType()
    {
        return mRefusedNeedleType;
    }

    public void setRefusedNeedleType(String refusedNeedleType)
    {
        mRefusedNeedleType = refusedNeedleType;
    }

    public String getBoxNo()
    {
        return mBoxNo;
    }

    public void setBoxNo(String boxNo)
    {
        mBoxNo = boxNo;
    }

    public String getColumnNo()
    {
        return mColumnNo;
    }

    public void setColumnNo(String columnNo)
    {
        mColumnNo = columnNo;
    }

    public String getLifeOfNeedle()
    {
        return mLifeOfNeedle;
    }

    public void setLifeOfNeedle(String lifeOfNeedle)
    {
        mLifeOfNeedle = lifeOfNeedle;
    }

    public String getTimeOfDay()
    {
        return mTimeOfDay;
    }

    public void setTimeOfDay(String timeOfDay)
    {
        mTimeOfDay = timeOfDay;
    }

    public String getIssuingTime()
    {
        return mIssuingTime;
    }

    public void setIssuingTime(String issuingTime)
    {
        mIssuingTime = issuingTime;
    }

    public String getDateOfProcurement()
    {
        return mDateOfProcurement;
    }

    public void setDateOfProcurement(String dateOfProcurement)
    {
        mDateOfProcurement = dateOfProcurement;
    }

    public String getDateOfReplacement()
    {
        return mDateOfReplacement;
    }

    public void setDateOfReplacement(String dateOfReplacement)
    {
        mDateOfReplacement = dateOfReplacement;
    }

    public String getTimeOfReplacement()
    {
        return mTimeOfReplacement;
    }

    public void setTimeOfReplacement(String timeOfReplacement)
    {
        mTimeOfReplacement = timeOfReplacement;
    }

    public String getTimeOfProcurement()
    {
        return mTimeOfProcurement;
    }

    public void setTimeOfProcurement(String timeOfProcurement)
    {
        mTimeOfProcurement = timeOfProcurement;
    }
}
