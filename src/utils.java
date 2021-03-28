import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class utils {
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(obj);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

    public static Map<String, String> getMapStringStringObjectToMap(Object obj) throws IllegalAccessException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Map<String, String> cast = (Map<String, String>) (obj);
        System.out.println(cast.get("0"));

        return cast;
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        Object something = "something";
//        String theType = "java.lang.String";
//        Class<?> theClass = Class.forName(theType);
//        Object obj = theClass.cast(something);
//        泛型：
        Object something = 123;
        String theType = "java.lang.Number";
        Class<? extends Number> theClass = Class.forName(theType).asSubclass(Number.class);
        Number obj = theClass.cast(something);
        System.out.println(obj);

    }
}
