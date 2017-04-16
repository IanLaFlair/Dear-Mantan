

package com.neteamindustry.dearmantan.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.neteamindustry.dearmantan.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * TipsTrikActivity list activity
 */
public class TipsTrikActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        setTitle(getString(R.string.tipsTrikActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return TipsTrikFragment.class;
    }

}
