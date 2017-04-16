

package com.neteamindustry.dearmantan.ds;
import com.neteamindustry.dearmantan.R;
import java.util.ArrayList;
import java.util.List;
import ibmmobileappbuilder.util.StringUtils;

// DaftarMantanDSSchemaItem static data
public class DaftarMantanDSItems{

    public static List<DaftarMantanDSSchemaItem> ITEMS = new ArrayList<DaftarMantanDSSchemaItem>();
    public static void addItem(DaftarMantanDSSchemaItem item) {
        ITEMS.add(item);
    }
}

