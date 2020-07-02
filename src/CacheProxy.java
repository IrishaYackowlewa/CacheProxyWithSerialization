import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class CacheProxy implements InvocationHandler{
    private final Object original;
    private int limit = 0;
    private boolean zip = true;
    private String fileName;
    private CacheType type;
    private HashMap setCacheValue;

    public CacheProxy(Object original){
        this.original = original;
        setCacheValue = new HashMap();
    }

    public CacheProxy(Object original, CacheType type, int limit){
        this.original = original;
        this.type = type;
        this.limit = limit;
        setCacheValue = new HashMap();
    }

    public CacheProxy(Object original, CacheType type, String fileName, int limit){
        this.original = original;
        this.limit = limit;
        this.zip = zip;
        this.fileName = fileName;
        File newFile = new File(this.fileName);
        this.type = type;
        setCacheValue = new HashMap();
    }

    public Object cache(String FILENAME) throws IOException, ClassNotFoundException {//принимает ссылку на сервис и возвращает кешированную версию этого сервиса.
        Serializer<ServiceImpl> serviceSerializer = new ServiceSerializer();
        Object outService = serviceSerializer.deserialize(FILENAME);
        System.out.println(outService);
        System.out.println("Метод cache() возвращает кешированный сервер");
        return outService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)){
            Cache annotation = method.getAnnotation(Cache.class);
            if (this.type == null)
                this.type = annotation.cacheType();
            if (this.type == CacheType.IN_MEMORY){//хранить в памяти
                if (this.limit == 0)
                    this.limit = annotation.listList();
                List<String> invokeMethod = (List<String>)method.invoke(original, args);
                if (invokeMethod.size() > this.limit)
                    invokeMethod = invokeMethod.subList(0,this.limit);
                if (!setCacheValue.containsKey(args)) {
                    setCacheValue.put(args, invokeMethod);
                    System.out.println("Сработало");
                }
                return setCacheValue.get(args);

            }
            else {//хранить в файле
                if (this.fileName == null)
                    this.fileName = annotation.fileNamePrefix();
                if (this.limit == 0)
                    this.limit = annotation.listList();
                List<String> invokeMethod = (List<String>)method.invoke(original, args);
                if (invokeMethod.size() > this.limit)
                    invokeMethod = invokeMethod.subList(0,this.limit);
                try {
                    return cache(this.fileName);
                } catch (IOException e) {
                    System.out.println("Не нашло файло и сработало новое кеширование");
                    Serializer<ServiceImpl> serviceSerializer = new ServiceSerializer();
                    serviceSerializer.serialize(invokeMethod, this.fileName);
                    return cache(this.fileName);
                }
            }
        }
        else{
            System.out.println("Метод не кеширован");
            return method.invoke(original,args);
        }

    }
}
