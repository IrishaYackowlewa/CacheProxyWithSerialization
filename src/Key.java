import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

public class Key implements Serializable {
    private final String nameMethod;
    private final Object[] args;

    public Key(String nameMetod, Object[] args) {
        this.nameMethod = nameMetod;
        this.args = args;
    }

    public String getNameMethod() {
        return this.nameMethod;
    }

    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Key cacheMap = (Key) obj;
        if (!this.nameMethod.equals(cacheMap.nameMethod))
            return false;
        if (this.args.length != cacheMap.args.length)
            return false;
        for (int i = 0; i < this.args.length; i++)
            if (!this.args[i].equals(cacheMap.args[i]))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nameMethod == null) ? 0 : nameMethod.hashCode());
        for (int i = 0; i < this.args.length; i++)
            result = prime * result + ((args[i] == null) ? 0 : args[i].hashCode());
        return result;
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
