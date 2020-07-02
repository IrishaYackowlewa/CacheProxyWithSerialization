import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class CacheProxy implements InvocationHandler{
    private final Object original;
    private CacheMap setCacheValueFile;
    private CacheMap setCacheValueMemory;

    public CacheProxy(Object original){
        this.original = original;
    }

    public CacheMap cache(String FILENAME) throws IOException, ClassNotFoundException {//принимает ссылку на сервис и возвращает кешированную версию этого сервиса.
        Serializer<CacheMap> serviceSerializer = new ServiceSerializer();
        CacheMap outService = (CacheMap) serviceSerializer.deserialize(FILENAME);
        return outService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)){
            Cache annotation = method.getAnnotation(Cache.class);
            List<String> invokeMethod = (List<String>)method.invoke(original, args);
            if (invokeMethod.size() > annotation.listList())
                invokeMethod = invokeMethod.subList(0,annotation.listList());
            Key key = new Key(method.getName(),args);
            if (annotation.cacheType() == CacheType.IN_MEMORY){//хранить в памяти
                if (setCacheValueMemory == null){
                    HashMap<Key, List<String>> hash = new HashMap<>();
                    hash.put(key,invokeMethod);
                    setCacheValueMemory = new CacheMap(hash);
                    System.out.println("Сработало кеширование в память");
                }
                if (!setCacheValueMemory.getCache().containsKey(key)) {
                    setCacheValueMemory.getCache().put(key, invokeMethod);
                    System.out.println("Сработало кеширование в память");
                }
                return setCacheValueMemory.getCache().get(key);
            }
            else {//хранить в файле
                if (setCacheValueFile == null) {
                    try {
                        setCacheValueFile = cache("_serialization");
                    } catch (IOException e) {
                        System.out.println("Нет кеша");
                        HashMap<Key, List<String>> hash = new HashMap<>();
                        hash.put(key,invokeMethod);
                        setCacheValueFile = new CacheMap(hash);
                        Serializer<CacheMap> serviceSerializer = new ServiceSerializer();
                        serviceSerializer.serialize(setCacheValueFile, annotation.fileNamePrefix());
                    } catch (ClassNotFoundException e) {
                    }
                }
                if (!setCacheValueFile.getCache().containsKey(key)) {
                    setCacheValueFile.getCache().put(key, invokeMethod);
                    System.out.println("Метод кеширован " + setCacheValueFile.hashCode() );
                    Serializer<CacheMap> serviceSerializer = new ServiceSerializer();
                    serviceSerializer.serialize(setCacheValueFile, annotation.fileNamePrefix());
                }
                return setCacheValueFile.getCache().get(key);
            }
        }
        else {
            System.out.println("Метод не кеширован");
            return method.invoke(original,args);
        }
    }
}
