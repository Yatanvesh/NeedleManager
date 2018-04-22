package com.themaster.android.needlemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class NeedleSplash extends AppCompatActivity
{
    private static int SPLASH_TIME=1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        TextInput textInput=new TextInput(getApplicationContext());
        for (int i=0;i<NeedleStore.Capacity;i++)
            textInput.BindInput(NeedleStore.get().getNeedles().get(i));


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i =new Intent(NeedleSplash.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME);
    }

    @Override
    public void invalidateOptionsMenu()
    {
        super.invalidateOptionsMenu();
    }
}
