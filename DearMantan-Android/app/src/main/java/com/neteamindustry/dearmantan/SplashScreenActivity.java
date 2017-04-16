package com.neteamindustry.dearmantan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.neteamindustry.dearmantan.ui.DearMantanActivity;

import static java.lang.Thread.sleep;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            sleep(3000);
            Intent intent = new Intent(this, DearMantanActivity.class);
            startActivity(intent);
            finish();
        }catch (InterruptedException e){
            e.printStackTrace();;
        }

    }


}
