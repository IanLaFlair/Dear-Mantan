package com.neteamindustry.dearmantan.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.neteamindustry.dearmantan.R;
import ibmmobileappbuilder.behaviors.SearchBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ui.ListGridFragment;
import ibmmobileappbuilder.util.ViewHolder;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.neteamindustry.dearmantan.ds.CurhatanBarengDSSchemaItem;
import com.neteamindustry.dearmantan.ds.CurhatanBarengDS;
import android.content.Intent;
import ibmmobileappbuilder.util.Constants;
import static ibmmobileappbuilder.util.NavigationUtils.generateIntentToAddOrUpdateItem;

/**
 * "TipsTrikFragment" listing
 */
public class TipsTrikFragment extends ListGridFragment<CurhatanBarengDSSchemaItem>  {

    private Datasource<CurhatanBarengDSSchemaItem> datasource;


    public static TipsTrikFragment newInstance(Bundle args) {
        TipsTrikFragment fr = new TipsTrikFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBehavior(new SearchBehavior(this));
        
    }

    protected SearchOptions getSearchOptions() {
        SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
        return searchOptionsBuilder.build();
    }


    /**
    * Layout for the list itselft
    */
    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    /**
    * Layout for each element in the list
    */
    @Override
    protected int getItemLayout() {
        return R.layout.tipstrik_item;
    }

    @Override
    protected Datasource<CurhatanBarengDSSchemaItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        datasource = CurhatanBarengDS.getInstance(getSearchOptions());
        return datasource;
    }

    @Override
    protected void bindView(CurhatanBarengDSSchemaItem item, View view, int position) {
        
        TextView title = ViewHolder.get(view, R.id.title);
        
        if (item.judul != null){
            title.setText(item.judul);
            
        }
    }


    @Override
    public void showDetail(CurhatanBarengDSSchemaItem item, int position) {
        // If we have forms, then we have to refresh when an item has been edited
        // Also with this we support list without details
        Bundle args = new Bundle();
        args.putInt(Constants.ITEMPOS, position);
        args.putParcelable(Constants.CONTENT, item);
        Intent intent = new Intent(getActivity(), TipsTrikDetailActivity.class);
        intent.putExtras(args);

        if (!getResources().getBoolean(R.bool.tabletLayout)) {
            startActivityForResult(intent, Constants.DETAIL);
        } else {
            startActivity(intent);
        }
    }

}
