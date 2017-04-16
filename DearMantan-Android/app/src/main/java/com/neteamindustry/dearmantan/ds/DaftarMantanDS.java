
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
 * "DaftarMantanDS" static data source (d06ce869-4809-427a-aeda-27d20296e30d)
 */
public class DaftarMantanDS implements Datasource<DaftarMantanDSSchemaItem>, Count,
            Pagination<DaftarMantanDSSchemaItem>, Distinct {

    private static final int PAGE_SIZE = 20;

    private SearchOptions searchOptions;

    public static DaftarMantanDS getInstance(SearchOptions searchOptions){
        return new DaftarMantanDS(searchOptions);
    }

    private DaftarMantanDS(SearchOptions searchOptions){
        this.searchOptions = searchOptions;
    }

    @Override
    public void getItems(Listener<List<DaftarMantanDSSchemaItem>> listener) {
        listener.onSuccess(DaftarMantanDSItems.ITEMS);
    }

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

	@Override
	public void getItem(String id, Listener<DaftarMantanDSSchemaItem> listener) {
		final int pos = Integer.parseInt(id);
		if(DaftarMantanDSItems.ITEMS.size() <= pos){
			listener.onSuccess(new DaftarMantanDSSchemaItem());
		} else {
			DaftarMantanDSSchemaItem dc;
			List<DaftarMantanDSSchemaItem> filteredItem = applySearchOptions(DaftarMantanDSItems.ITEMS);
			
			if(filteredItem != null && !filteredItem.isEmpty()) {
				dc = filteredItem.get(0);
			} else {
				dc = DaftarMantanDSItems.ITEMS.get(pos);
			}
			
			if( dc != null)
				listener.onSuccess(dc);
			else
				listener.onFailure(new IllegalArgumentException("DaftarMantanDSSchemaItem not found: " + pos));
		}
	}

    @Override public int getCount(){
        return DaftarMantanDSItems.ITEMS.size();
    }

    @Override
    public void getItems(int pagenum, Listener<List<DaftarMantanDSSchemaItem>> listener) {
        int first = pagenum * PAGE_SIZE;
        int last = first + PAGE_SIZE;
        ArrayList<DaftarMantanDSSchemaItem> result = new ArrayList<DaftarMantanDSSchemaItem>();
        List<DaftarMantanDSSchemaItem> filteredList = applySearchOptions(DaftarMantanDSItems.ITEMS);
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

    private List<DaftarMantanDSSchemaItem> applySearchOptions(List<DaftarMantanDSSchemaItem> result) {
        List<DaftarMantanDSSchemaItem> filteredList = result;

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

    private List<DaftarMantanDSSchemaItem> applySearch(List<DaftarMantanDSSchemaItem> items, String searchText) {
        List<DaftarMantanDSSchemaItem> filteredList = new ArrayList<>();

        for (DaftarMantanDSSchemaItem item : items) {
                        
            if (FilterUtils.searchInString(item.id, searchText) ||
            FilterUtils.searchInString(item.judul, searchText) ||
            FilterUtils.searchInString(item.curhatan, searchText))
            {
                filteredList.add(item);
            }
        }

        return filteredList;

    }

    private List<DaftarMantanDSSchemaItem> applyFilters(List<DaftarMantanDSSchemaItem> items, List<Filter> filters) {
        List<DaftarMantanDSSchemaItem> filteredList = new ArrayList<>();

        for (DaftarMantanDSSchemaItem item : items) {
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
        List<DaftarMantanDSSchemaItem> filteredList = applySearchOptions(DaftarMantanDSItems.ITEMS);
        listener.onSuccess(mapItems(filteredList, columnName));
    }

    private List<String> mapItems(List<DaftarMantanDSSchemaItem> items, String columnName){
        // return only unique values
        ArrayList<String> res = new ArrayList();
        for (DaftarMantanDSSchemaItem item: items){
            String mapped = mapItem(item, columnName);
            if(mapped != null && !res.contains(mapped))
                res.add(mapped);
        }

        return res;
    }

    private String mapItem(DaftarMantanDSSchemaItem item, String columnName){
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

