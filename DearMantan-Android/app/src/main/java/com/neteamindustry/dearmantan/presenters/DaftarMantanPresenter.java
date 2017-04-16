
package com.neteamindustry.dearmantan.presenters;

import com.neteamindustry.dearmantan.R;
import com.neteamindustry.dearmantan.ds.CurBarDSSchemaItem;

import java.util.List;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.ListCrudPresenter;
import ibmmobileappbuilder.mvp.view.CrudListView;

public class DaftarMantanPresenter extends BasePresenter implements ListCrudPresenter<CurBarDSSchemaItem>,
      Datasource.Listener<CurBarDSSchemaItem>{

    private final CrudDatasource<CurBarDSSchemaItem> crudDatasource;
    private final CrudListView<CurBarDSSchemaItem> view;

    public DaftarMantanPresenter(CrudDatasource<CurBarDSSchemaItem> crudDatasource,
                                         CrudListView<CurBarDSSchemaItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(CurBarDSSchemaItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<CurBarDSSchemaItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(CurBarDSSchemaItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(CurBarDSSchemaItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(CurBarDSSchemaItem item) {
                view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
    }

}
