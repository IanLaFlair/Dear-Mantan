
package com.neteamindustry.dearmantan.ds;

import ibmmobileappbuilder.ds.Count;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.Distinct;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.util.FilterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * "CurhatanBarengDS" static data source (40f1c8f0-8a0b-4386-be77-69c192e5a77c)
 */
public class CurhatanBarengDS implements Datasource<CurhatanBarengDSSchemaItem>, Count,
            Pagination<CurhatanBarengDSSchemaItem>, Distinct {

    private static final int PAGE_SIZE = 20;

    private SearchOptions searchOptions;

    public static CurhatanBarengDS getInstance(SearchOptions searchOptions){
        return new CurhatanBarengDS(searchOptions);
    }

    private CurhatanBarengDS(SearchOptions searchOptions){
        this.searchOptions = searchOptions;
    }

    @Override
    public void getItems(Listener<List<CurhatanBarengDSSchemaItem>> listener) {
        listener.onSuccess(CurhatanBarengDSItems.ITEMS);
    }

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

	@Override
	public void getItem(String id, Listener<CurhatanBarengDSSchemaItem> listener) {
		final int pos = Integer.parseInt(id);
		if(CurhatanBarengDSItems.ITEMS.size() <= pos){
			listener.onSuccess(new CurhatanBarengDSSchemaItem());
		} else {
			CurhatanBarengDSSchemaItem dc;
			List<CurhatanBarengDSSchemaItem> filteredItem = applySearchOptions(CurhatanBarengDSItems.ITEMS);
			
			if(filteredItem != null && !filteredItem.isEmpty()) {
				dc = filteredItem.get(0);
			} else {
				dc = CurhatanBarengDSItems.ITEMS.get(pos);
			}
			
			if( dc != null)
				listener.onSuccess(dc);
			else
				listener.onFailure(new IllegalArgumentException("CurhatanBarengDSSchemaItem not found: " + pos));
		}
	}

    @Override public int getCount(){
        return CurhatanBarengDSItems.ITEMS.size();
    }

    @Override
    public void getItems(int pagenum, Listener<List<CurhatanBarengDSSchemaItem>> listener) {
        int first = pagenum * PAGE_SIZE;
        int last = first + PAGE_SIZE;
        ArrayList<CurhatanBarengDSSchemaItem> result = new ArrayList<CurhatanBarengDSSchemaItem>();
        List<CurhatanBarengDSSchemaItem> filteredList = applySearchOptions(CurhatanBarengDSItems.ITEMS);
        if(first < filteredList.size())
            for (int i = first; (i < last) && (i < filteredList.size()); i++)
                result.add(filteredList.get(i));

        listener.onSuccess(result);
    }

    @Override
    public void onSearchTextChanged(String s){
        searchOptions.setSearchText(s);
    }

    @Override
    public void addFilter(Filter filter){
        searchOptions.addFilter(filter);
    }

    @Override
    public void clearFilters() {
        searchOptions.setFilters(null);
    }

    private List<CurhatanBarengDSSchemaItem> applySearchOptions(List<CurhatanBarengDSSchemaItem> result) {
        List<CurhatanBarengDSSchemaItem> filteredList = result;

        //Searching options
        String searchText = searchOptions.getSearchText();

        if(searchOptions.getFixedFilters() != null)
            filteredList = applyFilters(filteredList, searchOptions.getFixedFilters());

        if(searchOptions.getFilters() != null)
            filteredList = applyFilters(filteredList, searchOptions.getFilters());

        if (searchText != null && !"".equals(searchText))
            filteredList = applySearch(filteredList, searchText);

        //Sorting options
        Comparator comparator = searchOptions.getSortComparator();
        if (comparator != null) {
            if (searchOptions.isSortAscending()) {
                Collections.sort(filteredList, comparator);
            } else {
                Collections.sort(filteredList, Collections.reverseOrder(comparator));
            }
        }

        return filteredList;
    }

    private List<CurhatanBarengDSSchemaItem> applySearch(List<CurhatanBarengDSSchemaItem> items, String searchText) {
        List<CurhatanBarengDSSchemaItem> filteredList = new ArrayList<>();

        for (CurhatanBarengDSSchemaItem item : items) {
                        
            if (FilterUtils.searchInString(item.id, searchText) ||
            FilterUtils.searchInString(item.judul, searchText) ||
            FilterUtils.searchInString(item.curhatan, searchText))
            {
                filteredList.add(item);
            }
        }

        return filteredList;

    }

    private List<CurhatanBarengDSSchemaItem> applyFilters(List<CurhatanBarengDSSchemaItem> items, List<Filter> filters) {
        List<CurhatanBarengDSSchemaItem> filteredList = new ArrayList<>();

        for (CurhatanBarengDSSchemaItem item : items) {
            if (
                FilterUtils.applyFilters("id", item.id, filters) &&
                FilterUtils.applyFilters("judul", item.judul, filters) &&
                FilterUtils.applyFilters("curhatan", item.curhatan, filters)
                ){

                filteredList.add(item);
            }
        }

        return filteredList;
    }

    // Distinct interface

    @Override
    public void getUniqueValuesFor(String columnName, Listener<List<String>> listener) {
        List<CurhatanBarengDSSchemaItem> filteredList = applySearchOptions(CurhatanBarengDSItems.ITEMS);
        listener.onSuccess(mapItems(filteredList, columnName));
    }

    private List<String> mapItems(List<CurhatanBarengDSSchemaItem> items, String columnName){
        // return only unique values
        ArrayList<String> res = new ArrayList();
        for (CurhatanBarengDSSchemaItem item: items){
            String mapped = mapItem(item, columnName);
            if(mapped != null && !res.contains(mapped))
                res.add(mapped);
        }

        return res;
    }

    private String mapItem(CurhatanBarengDSSchemaItem item, String columnName){
        // get fields
        switch (columnName){
                        
            case "id":
                return item.id;
            
            case "judul":
                return item.judul;
            
            case "curhatan":
                return item.curhatan;
            default:
               return null;
        }
    }
}

