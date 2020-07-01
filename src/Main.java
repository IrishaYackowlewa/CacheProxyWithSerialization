import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        Service service = new ServiceImpl();
        Service serviceProxy = (Service) Proxy.newProxyInstance(
                Service.class.getClassLoader(),
                new Class<?>[] { Service.class },
                new CacheProxy(service)//тут надо в конструктор еще параметров добавить
        );

    }
}
