
package com.neteamindustry.dearmantan.ds;

import ibmmobileappbuilder.mvp.model.MutableIdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class CurBarDSSchemaItem implements Parcelable, MutableIdentifiableBean {

    private transient String cloudantIdentifiableId;

    @SerializedName("Judul") public String judul;
    @SerializedName("Curhatan") public String curhatan;

    @Override
    public void setIdentifiableId(String id) {
        this.cloudantIdentifiableId = id;
    }

    @Override
    public String getIdentifiableId() {
        return cloudantIdentifiableId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cloudantIdentifiableId);
        dest.writeString(judul);
        dest.writeString(curhatan);
    }

    public static final Creator<CurBarDSSchemaItem> CREATOR = new Creator<CurBarDSSchemaItem>() {
        @Override
        public CurBarDSSchemaItem createFromParcel(Parcel in) {
            CurBarDSSchemaItem item = new CurBarDSSchemaItem();
            item.cloudantIdentifiableId = in.readString();

            item.judul = in.readString();
            item.curhatan = in.readString();
            return item;
        }

        @Override
        public CurBarDSSchemaItem[] newArray(int size) {
            return new CurBarDSSchemaItem[size];
        }
    };
}

