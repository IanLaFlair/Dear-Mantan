
package com.neteamindustry.dearmantan.ds;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class DaftarMantanDSSchemaItem implements Parcelable, IdentifiableBean {

    @SerializedName("id") public String id;
    @SerializedName("judul") public String judul;
    @SerializedName("curhatan") public String curhatan;

    @Override
    public String getIdentifiableId() {
      return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(judul);
        dest.writeString(curhatan);
    }

    public static final Creator<DaftarMantanDSSchemaItem> CREATOR = new Creator<DaftarMantanDSSchemaItem>() {
        @Override
        public DaftarMantanDSSchemaItem createFromParcel(Parcel in) {
            DaftarMantanDSSchemaItem item = new DaftarMantanDSSchemaItem();

            item.id = in.readString();
            item.judul = in.readString();
            item.curhatan = in.readString();
            return item;
        }

        @Override
        public DaftarMantanDSSchemaItem[] newArray(int size) {
            return new DaftarMantanDSSchemaItem[size];
        }
    };

}

