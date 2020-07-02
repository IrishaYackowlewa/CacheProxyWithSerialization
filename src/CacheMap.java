import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CacheMap implements Serializable {

    private static final long serialVersionUID = -1086423171549213495L;

    public HashMap<Key,List<String>> cache;

    public CacheMap() {
    }

    public CacheMap(HashMap<Key, List<String>> cache) {
        this.cache = cache;
    }

    public HashMap<Key, List<String>> getCache() {
        return cache;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeLong(Calendar.getInstance().getTimeInMillis());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        long writeTime = in.readLong();
    }
}
