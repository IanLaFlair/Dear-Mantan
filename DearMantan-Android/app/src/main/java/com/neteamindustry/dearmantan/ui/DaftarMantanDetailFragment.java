
package com.neteamindustry.dearmantan.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.neteamindustry.dearmantan.presenters.DaftarMantanDetailPresenter;
import com.neteamindustry.dearmantan.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.ShareBehavior;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.neteamindustry.dearmantan.ds.CurBarDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;

public class DaftarMantanDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<CurBarDSSchemaItem> implements ShareBehavior.ShareListener  {

    private CrudDatasource<CurBarDSSchemaItem> datasource;
    public static DaftarMantanDetailFragment newInstance(Bundle args){
        DaftarMantanDetailFragment fr = new DaftarMantanDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public DaftarMantanDetailFragment(){
        super();
    }

    @Override
    public Datasource<CurBarDSSchemaItem> getDatasource() {
        if (datasource != null) {
            return datasource;
    }
       datasource = CloudantDatasource.cloudantDatasource(
              CloudantDatastoresFactory.create("curhatan"),
              URI.create("https://d81f6ea6-57b5-4d0e-84a4-23e3e7956b59-bluemix:2d9bb3176c4caa6c1763d8c2b52d5aae967ea6ab8baf2793444669459e8b3845@d81f6ea6-57b5-4d0e-84a4-23e3e7956b59-bluemix.cloudant.com/curhatan"),
              CurBarDSSchemaItem.class,
              new SearchOptions()
              );
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // the presenter for this view
        setPresenter(new DaftarMantanDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button
        addBehavior(new ShareBehavior(getActivity(), this));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.daftarmantandetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final CurBarDSSchemaItem item, View view) {
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
    protected void onShow(CurBarDSSchemaItem item) {
        // set the title for this fragment
        getActivity().setTitle(null);
    }

    @Override
    public void navigateToEditForm() {
        Bundle args = new Bundle();

        args.putInt(Constants.ITEMPOS, 0);
        args.putParcelable(Constants.CONTENT, getItem());
        args.putInt(Constants.MODE, Constants.MODE_EDIT);

        Intent intent = new Intent(getActivity(), DaftarMantanFormFormActivity.class);
        intent.putExtras(args);
        startActivityForResult(intent, Constants.MODE_EDIT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_menu, menu);

        MenuItem item = menu.findItem(R.id.action_delete);
        ColorUtils.tintIcon(item, R.color.textBarColor, getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            ((DetailCrudPresenter<CurBarDSSchemaItem>) getPresenter()).deleteItem(getItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onShare() {
        CurBarDSSchemaItem item = getItem();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, (item.judul != null ? item.judul : "" ) + "\n" +
                    (item.curhatan != null ? item.curhatan : "" ));
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share)), 1);
    }
}
