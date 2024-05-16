package org.example;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class CopyUtils {

    public static Object deepCopy(Object original) {
        try {
            return deepCopyInternal(original, new IdentityHashMap<>());
        } catch (Exception e) {
            throw new RuntimeException("Deep copy failed", e);
        }
    }

    private static Object deepCopyInternal(Object original, Map<Object, Object> copies) throws Exception {
        if (original == null) {
            return null;
        }

        // Handle immutable types (primitives, wrappers, strings, etc.)
        if (isImmutable(original)) {
            return original;
        }

        // Handle already copied objects (to manage cycles)
        if (copies.containsKey(original)) {
            return copies.get(original);
        }

        Class<?> clazz = original.getClass();

        // Handle arrays
        if (clazz.isArray()) {
            int length = Array.getLength(original);
            Object newArray = Array.newInstance(clazz.getComponentType(), length);
            copies.put(original, newArray);
            for (int i = 0; i < length; i++) {
                Array.set(newArray, i, deepCopyInternal(Array.get(original, i), copies));
            }
            return newArray;
        }

        // Handle collections
        if (original instanceof Collection) {
            Collection<?> originalCollection = (Collection<?>) original;
            Collection<Object> newCollection = originalCollection instanceof List ? new ArrayList<>()
                    : originalCollection instanceof Set ? new HashSet<>()
                    : new LinkedList<>();
            copies.put(original, newCollection);
            for (Object item : originalCollection) {
                newCollection.add(deepCopyInternal(item, copies));
            }
            return newCollection;
        }

        // Handle maps
        if (original instanceof Map) {
            Map<?, ?> originalMap = (Map<?, ?>) original;
            Map<Object, Object> newMap = new HashMap<>();
            copies.put(original, newMap);
            for (Map.Entry<?, ?> entry : originalMap.entrySet()) {
                Object newKey = deepCopyInternal(entry.getKey(), copies);
                Object newValue = deepCopyInternal(entry.getValue(), copies);
                newMap.put(newKey, newValue);
            }
            return newMap;
        }

        // Create a new instance of the object
        Object newObject = createInstance(clazz);
        copies.put(original, newObject);

        // Copy fields
        for (Field field : getAllFields(clazz)) {
            field.setAccessible(true);
            Object fieldValue = field.get(original);
            field.set(newObject, deepCopyInternal(fieldValue, copies));
        }

        return newObject;
    }

    private static boolean isImmutable(Object obj) {
        return obj instanceof Number || obj instanceof Character || obj instanceof Boolean || obj instanceof String;
    }

    private static Object createInstance(Class<?> clazz) throws Exception {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            // Fallback for classes without a no-arg constructor
            return UnsafeAllocator.create().newInstance(clazz);
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
