import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    int listList() default 1000000;

    boolean zip() default false;

    String fileNamePrefix() default "data";

    CacheType cacheType() default CacheType.FILE;

    Class<?>[] identityBy() default {};
}
