import java.io.*;

public interface Serializer<T extends Serializable> {

    String FILENAME_SUFIX = "_serialization";

    /**
     * Сериализует объект класса {@link T}
     *  @param o объект типа {@link T} в файл
     * @param fileName имя файла
     */
    default void serialize(Object o, String fileName) throws IOException {
        try(
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            System.out.println(o.getClass().getSimpleName() + " serialization start");
            out.writeObject(o);
            System.out.println("Serialization finish");
        }
    }

    /**
     * Десериализует объект класса из файла
     *
     * @param fileName имя файла с байтовым состоянием объекта
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    default Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        try(
            FileInputStream fileInputStream = new  FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileInputStream)
        ) {
            System.out.println("Deserialization start");
            Object deserializedObject = (Object) in.readObject();
            System.out.println(deserializedObject.getClass().getSimpleName() + " deserialization finish");
            return deserializedObject;
        }
    }

}
