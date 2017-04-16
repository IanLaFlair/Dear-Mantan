
package com.neteamindustry.dearmantan.ui;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import com.neteamindustry.dearmantan.presenters.DaftarMantanFormFormPresenter;
import com.neteamindustry.dearmantan.R;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.ui.FormFragment;
import ibmmobileappbuilder.views.TextWatcherAdapter;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.neteamindustry.dearmantan.ds.CurBarDSSchemaItem;
import ibmmobileappbuilder.ds.CloudantDatasource;
import ibmmobileappbuilder.cloudant.factory.CloudantDatastoresFactory;
import java.net.URI;

public class DaftarMantanFormFragment extends FormFragment<CurBarDSSchemaItem> {

    private CrudDatasource<CurBarDSSchemaItem> datasource;

    public static DaftarMantanFormFragment newInstance(Bundle args){
        DaftarMantanFormFragment fr = new DaftarMantanFormFragment();
        fr.setArguments(args);

        return fr;
    }

    public DaftarMantanFormFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // the presenter for this view
        setPresenter(new DaftarMantanFormFormPresenter(
                (CrudDatasource) getDatasource(),
                this));

            }

    @Override
    protected CurBarDSSchemaItem newItem() {
        return new CurBarDSSchemaItem();
    }

    @Override
    protected int getLayout() {
        return R.layout.daftarmantanform_form;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final CurBarDSSchemaItem item, View view) {
        
        bindString(R.id.judul, item.judul, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.judul = s.toString();
            }
        });
        
        
        bindString(R.id.curhatan, item.curhatan, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.curhatan = s.toString();
            }
        });
        
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
}
