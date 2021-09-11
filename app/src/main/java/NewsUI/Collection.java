package NewsUI;

import com.orm.SugarRecord;

public class Collection extends SugarRecord {
    public String detailUrl = "";
    public Collection() {}
    public Collection(String detailURL) {
        this.detailUrl = detailURL;
    }
    public static boolean inCollection(String url) {
        for (Collection collection : Collection.listAll(Collection.class)) {
            if (collection.detailUrl.equals(url))
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
