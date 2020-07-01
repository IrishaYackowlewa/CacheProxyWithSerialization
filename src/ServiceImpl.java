import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ServiceImpl implements Service, Serializable {
    private static final long serialVersionUID = -1086423171549213495L;

    @Override
    public List<String> run(String item, double value) {
        String[] arrStr = item.split("[\\W\\d_]+");
        List<String> list = new ArrayList();
        for (String elem: arrStr) {
            if (elem.length() > value)
                list.add(elem);
        }
        return list;
    }

    @Override
    public List<String> parse(String item) {
        return Arrays.asList(item.split(" "));
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeLong(Calendar.getInstance().getTimeInMillis());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        long writeTime = in.readLong();
//        System.out.println(writeTime);
    }
}
