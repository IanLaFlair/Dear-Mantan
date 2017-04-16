
package com.neteamindustry.dearmantan.ds;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class CurhatanBarengDSSchemaItem implements Parcelable, IdentifiableBean {

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

    public static final Creator<CurhatanBarengDSSchemaItem> CREATOR = new Creator<CurhatanBarengDSSchemaItem>() {
        @Override
        public CurhatanBarengDSSchemaItem createFromParcel(Parcel in) {
            CurhatanBarengDSSchemaItem item = new CurhatanBarengDSSchemaItem();

            item.id = in.readString();
            item.judul = in.readString();
            item.curhatan = in.readString();
            return item;
        }

        @Override
        public CurhatanBarengDSSchemaItem[] newArray(int size) {
            return new CurhatanBarengDSSchemaItem[size];
        }
    };

}

