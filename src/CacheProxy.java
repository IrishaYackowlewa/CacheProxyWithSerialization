import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CacheProxy implements InvocationHandler{
    private final Object original;
    private String rootForder;
    private int limit;
    private boolean zip;
    private String fileName;
    private CacheType type;
    private Class<?>[] identityBy;
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

    public CacheProxy(Object original, CacheType type, String rootForder, String fileName, int limit, boolean zip, Class<?>[] identityBy){
        this.original = original;
        this.rootForder = rootForder;
        this.limit = limit;
        this.zip = zip;
        this.fileName = fileName;
        this.type = type;
        this.identityBy = identityBy;
        setCacheValue = new HashMap();
    }

    public ServiceImpl cache(ServiceImpl service) throws IOException, ClassNotFoundException {//принимает ссылку на сервис и возвращает кешированную версию этого сервиса.
        Serializer<ServiceImpl> serviceSerializer = new ServiceSerializer();
        serviceSerializer.serialize(service, ServiceSerializer.FILENAME);
        ServiceImpl outService = serviceSerializer.deserialize(ServiceSerializer.FILENAME);
        System.out.println(outService);
        System.out.println("Метод cache() возвращает кешированный сервер");
        return outService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)){
            double arg = (double) args[0];
            if (!setCacheValue.containsKey(arg)) {
                setCacheValue.put(arg, (double) method.invoke(original, arg));
                System.out.println("Сработало");
            }
            return setCacheValue.get(arg);
        }
        else{
            System.out.println("Метод не кеширован");
            return method.invoke(original,args);
        }
    }
}
