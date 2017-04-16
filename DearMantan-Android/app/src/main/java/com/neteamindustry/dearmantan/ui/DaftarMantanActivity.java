

package com.neteamindustry.dearmantan.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.neteamindustry.dearmantan.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * DaftarMantanActivity list activity
 */
public class DaftarMantanActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.daftarMantanActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return DaftarMantanFragment.class;
    }

}
