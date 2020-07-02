import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();
        Service serviceProxy = (Service) Proxy.newProxyInstance(
                Service.class.getClassLoader(),
                new Class<?>[] { Service.class },
                new CacheProxy(service)//тут надо в конструктор еще параметров добавить
        );

        for (String str: serviceProxy.run("Lily lo ro fontan kron", 2)){
            System.out.println(str);
        };

        for (String str: serviceProxy.run("Lily lo ro fontan kron", 2)){
            System.out.println(str);
        };

        for (String str: serviceProxy.run("Lily lo ro fontan kron vlb,gf", 3)){
            System.out.println(str);
        };
    }
}
