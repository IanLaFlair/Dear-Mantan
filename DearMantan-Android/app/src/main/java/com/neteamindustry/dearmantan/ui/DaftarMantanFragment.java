package com.neteamindustry.dearmantan.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.neteamindustry.dearmantan.presenters.DaftarMantanPresenter;
import com.neteamindustry.dearmantan.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.SearchBehavior;
import ibmmobileappbuilder.behaviors.SelectionBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ui.ListGridFragment;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.ViewHolder;
import java.util.List;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.neteamindustry.dearmantan.ds.CurBarDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;
import ibmmobileappbuilder.mvp.view.CrudListView;
import ibmmobileappbuilder.ds.CrudDatasource;
import android.content.Intent;
import ibmmobileappbuilder.util.Constants;
import static ibmmobileappbuilder.util.NavigationUtils.generateIntentToAddOrUpdateItem;

/**
 * "DaftarMantanFragment" listing
 */
public class DaftarMantanFragment extends ListGridFragment<CurBarDSSchemaItem> implements CrudListView<CurBarDSSchemaItem> {

    private CrudDatasource<CurBarDSSchemaItem> datasource;

    // "Add" button
    private FabBehaviour fabBehavior;

    public static DaftarMantanFragment newInstance(Bundle args) {
        DaftarMantanFragment fr = new DaftarMantanFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new DaftarMantanPresenter(
            (CrudDatasource) getDatasource(),
            this
        ));
        addBehavior(new SearchBehavior(this));
        // Multiple selection
        SelectionBehavior<CurBarDSSchemaItem> selectionBehavior = new SelectionBehavior<>(
            this,
            R.string.remove_items,
            R.drawable.ic_delete_alpha);

        selectionBehavior.setCallback(new SelectionBehavior.Callback<CurBarDSSchemaItem>() {
            @Override
            public void onSelected(List<CurBarDSSchemaItem> selectedItems) {
                getPresenter().deleteItems(selectedItems);
            }
        });
        addBehavior(selectionBehavior);

        // FAB button
        fabBehavior = new FabBehaviour(this, R.drawable.ic_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().addForm();
            }
        });
        addBehavior(fabBehavior);
        
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
        return R.layout.daftarmantan_item;
    }

    @Override
    protected Datasource<CurBarDSSchemaItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
       datasource = CloudantDatasource.cloudantDatasource(
              CloudantDatastoresFactory.create("curhatan"),
              URI.create("https://d81f6ea6-57b5-4d0e-84a4-23e3e7956b59-bluemix:2d9bb3176c4caa6c1763d8c2b52d5aae967ea6ab8baf2793444669459e8b3845@d81f6ea6-57b5-4d0e-84a4-23e3e7956b59-bluemix.cloudant.com/curhatan"),
              CurBarDSSchemaItem.class,
              getSearchOptions()
              );
        return datasource;
    }

    @Override
    protected void bindView(CurBarDSSchemaItem item, View view, int position) {
        
        TextView title = ViewHolder.get(view, R.id.title);
        
        if (item.judul != null){
            title.setText(item.judul);
            
        }
        
        TextView subtitle = ViewHolder.get(view, R.id.subtitle);
        
        if (item.curhatan != null){
            subtitle.setText(item.curhatan);
            
        }
    }

    @Override
    protected void itemClicked(final CurBarDSSchemaItem item, final int position) {
        fabBehavior.hide(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getPresenter().detail(item, position);
            }
        });
    }

    @Override
    public void showDetail(CurBarDSSchemaItem item, int position) {
        // If we have forms, then we have to refresh when an item has been edited
        // Also with this we support list without details
        Bundle args = new Bundle();
        args.putInt(Constants.ITEMPOS, position);
        args.putParcelable(Constants.CONTENT, item);
        Intent intent = new Intent(getActivity(), DaftarMantanDetailActivity.class);
        intent.putExtras(args);

        if (!getResources().getBoolean(R.bool.tabletLayout)) {
            startActivityForResult(intent, Constants.DETAIL);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void showAdd() {
        startActivityForResult(generateIntentToAddOrUpdateItem(null,
                        0,
                        getActivity(),
                        DaftarMantanFormFormActivity.class
                ), Constants.MODE_CREATE
        );
    }

    @Override
    public void showEdit(CurBarDSSchemaItem item, int position) {
        startActivityForResult(
                generateIntentToAddOrUpdateItem(item,
                        position,
                        getActivity(),
                        DaftarMantanFormFormActivity.class
                ), Constants.MODE_EDIT
        );
    }
}
