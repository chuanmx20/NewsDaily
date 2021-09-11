package com.example.newsdaily;

import com.orm.SugarContext;
import com.orm.SugarRecord;

public class Collection extends SugarRecord {
    String detailUrl = "";
    public Collection() {}
    public Collection(String detailURL) {
        this.detailUrl = detailURL;
    }
    static boolean inCollection(String url) {
        for (Collection collection : Collection.listAll(Collection.class)) {
            if (collection.equals(url))
                return true;
        }
        return false;
    }

    public static void cleanCollect(String url) {
        for (Collection collection : Collection.listAll(Collection.class)) {
            if (collection.detailUrl.equals(url)) {
                collection.delete();
                return;
            }
        }
    }
}
