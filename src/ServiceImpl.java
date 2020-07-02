import java.io.*;
import java.util.*;

public class ServiceImpl implements Service, Serializable {

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
}
