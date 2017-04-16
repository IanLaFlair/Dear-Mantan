
package com.neteamindustry.dearmantan.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import com.neteamindustry.dearmantan.R;
import ibmmobileappbuilder.behaviors.ShareBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.neteamindustry.dearmantan.ds.CurhatanBarengDSSchemaItem;
import com.neteamindustry.dearmantan.ds.CurhatanBarengDS;

public class TipsTrikDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<CurhatanBarengDSSchemaItem> implements ShareBehavior.ShareListener  {

    private Datasource<CurhatanBarengDSSchemaItem> datasource;
    public static TipsTrikDetailFragment newInstance(Bundle args){
        TipsTrikDetailFragment fr = new TipsTrikDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public TipsTrikDetailFragment(){
        super();
    }

    @Override
    public Datasource<CurhatanBarengDSSchemaItem> getDatasource() {
        if (datasource != null) {
            return datasource;
    }
       datasource = CurhatanBarengDS.getInstance(new SearchOptions());
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        addBehavior(new ShareBehavior(getActivity(), this));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.tipstrikdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final CurhatanBarengDSSchemaItem item, View view) {
        if (item.judul != null){
            
            TextView view0 = (TextView) view.findViewById(R.id.view0);
            view0.setText(item.judul);
            
        }
        if (item.curhatan != null){
            
            TextView view1 = (TextView) view.findViewById(R.id.view1);
            view1.setText(item.curhatan);
            
        }
    }

    @Override
    protected void onShow(CurhatanBarengDSSchemaItem item) {
        // set the title for this fragment
        getActivity().setTitle(null);
    }
    @Override
    public void onShare() {
        CurhatanBarengDSSchemaItem item = getItem();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, (item.judul != null ? item.judul : "" ) + "\n" +
                    (item.curhatan != null ? item.curhatan : "" ));
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share)), 1);
    }
}
