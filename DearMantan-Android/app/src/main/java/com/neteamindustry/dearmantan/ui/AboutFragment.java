package com.neteamindustry.dearmantan.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.neteamindustry.dearmantan.R;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ui.ListGridFragment;
import ibmmobileappbuilder.util.image.ImageLoader;
import ibmmobileappbuilder.util.image.PicassoImageLoader;
import ibmmobileappbuilder.util.ViewHolder;
import static ibmmobileappbuilder.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.neteamindustry.dearmantan.ds.Item;
import com.neteamindustry.dearmantan.ds.EmptyDatasource;
import static ibmmobileappbuilder.util.NavigationUtils.generateIntentToAddOrUpdateItem;

/**
 * "CobaBalikanFragment" listing
 */
public class AboutFragment extends ListGridFragment<Item>  {

    private Datasource<Item> datasource;


    public static AboutFragment newInstance(Bundle args) {
        AboutFragment fr = new AboutFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_about;
    }

    @Override
    protected Datasource<Item> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        datasource = EmptyDatasource.getInstance(getSearchOptions());
        return datasource;
    }

    @Override
    protected void bindView(Item item, View view, int position) {

        ImageLoader imageLoader = new PicassoImageLoader(view.getContext());
        ImageView image = ViewHolder.get(view, R.id.image);
        imageLoader.load(imageLoaderRequest()
                .withResourceToLoad(R.drawable.png_defaultmenuicon)
                .withTargetView(image)
                .fit()
                .build()
        );


    }


    @Override
    public void showDetail(Item item, int position) {
    }

}
