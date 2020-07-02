import java.util.List;

public interface Service {

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = ServiceSerializer.FILENAME_SUFIX, zip = true)
    List<String> run(String item, double value);

    @Cache(cacheType = CacheType.IN_MEMORY, listList = 1000)
    List<String> parse(String item);

}
