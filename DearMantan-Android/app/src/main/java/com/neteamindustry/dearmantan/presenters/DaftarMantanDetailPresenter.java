
package com.neteamindustry.dearmantan.presenters;

import com.neteamindustry.dearmantan.R;
import com.neteamindustry.dearmantan.ds.CurBarDSSchemaItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;

public class DaftarMantanDetailPresenter extends BasePresenter implements DetailCrudPresenter<CurBarDSSchemaItem>,
      Datasource.Listener<CurBarDSSchemaItem> {

    private final CrudDatasource<CurBarDSSchemaItem> datasource;
    private final DetailView view;

    public DaftarMantanDetailPresenter(CrudDatasource<CurBarDSSchemaItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(CurBarDSSchemaItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(CurBarDSSchemaItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(CurBarDSSchemaItem item) {
                view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
    }
}
