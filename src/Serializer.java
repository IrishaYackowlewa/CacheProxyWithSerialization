import java.io.*;

public interface Serializer<T extends Serializable> {

    String FILENAME_SUFIX = "_serialization";

    /**
     * Сериализует объект класса {@link T}
     *  @param o объект типа {@link T} в файл
     * @param fileName имя файла
     */
    default void serialize(T o, String fileName) throws IOException {
        try(
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            out.writeObject(o);
        }
    }

    /**
     * Десериализует объект класса из файла
     *
     * @param fileName имя файла с байтовым состоянием объекта
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    default T deserialize(String fileName) throws IOException, ClassNotFoundException {
        try(
            FileInputStream fileInputStream = new  FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileInputStream)
        ) {
            T deserializedObject = (T) in.readObject();
            return deserializedObject;
        }
    }

}
