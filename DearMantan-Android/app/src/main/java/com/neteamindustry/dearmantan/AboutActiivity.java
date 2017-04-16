

package com.neteamindustry.dearmantan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.neteamindustry.dearmantan.R;
import com.neteamindustry.dearmantan.ui.AboutFragment;
import com.neteamindustry.dearmantan.ui.DearMantanActivity;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * CobaBalikanActivity list activity
 */
public class AboutActiivity extends AppCompatActivity {

    private Button butonhome;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_about);



        butonhome = (Button)findViewById(R.id.homebtn);

        butonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActiivity.this, DearMantanActivity.class);
                startActivity(intent);
            }
        });


    }



}
