

package com.neteamindustry.dearmantan.ui;

import android.os.Bundle;

import com.neteamindustry.dearmantan.R;
import com.neteamindustry.dearmantan.AboutActiivity;

import java.util.ArrayList;
import java.util.List;

import ibmmobileappbuilder.MenuItem;

import ibmmobileappbuilder.actions.StartActivityAction;
import ibmmobileappbuilder.util.Constants;

/**
 * DearMantanFragment menu fragment.
 */
public class DearMantanFragment extends ibmmobileappbuilder.ui.MenuFragment {
    /**
     * Default constructor
     */
    public DearMantanFragment(){
        super();
    }

    // Factory method
    public static DearMantanFragment newInstance(Bundle args) {
        DearMantanFragment fragment = new DearMantanFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Menu Fragment interface
    @Override
    public List<MenuItem> getMenuItems() {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        items.add(new MenuItem()
            .setLabel("Coba Balikan")
            .setIcon(R.drawable.png_cobabalikan2101)
            .setAction(new StartActivityAction(CobaBalikanActivity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
            .setLabel("Tips Trik")
            .setIcon(R.drawable.png_tipstricks2252)
            .setAction(new StartActivityAction(TipsTrikActivity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
            .setLabel("CurBar")
            .setIcon(R.drawable.curbar2)
            .setAction(new StartActivityAction(DaftarMantanActivity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
                .setLabel("About")
                .setIcon(R.drawable.about2)
                .setAction(new StartActivityAction(AboutActiivity.class,Constants.DETAIL))
        );
        return items;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_grid;
    }

    @Override
    public int getItemLayout() {
        return R.layout.dearmantan_item;
    }
}
