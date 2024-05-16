package org.example;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeAllocator {
        private static final Unsafe UNSAFE;

        static {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                UNSAFE = (Unsafe) theUnsafe.get(null);
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        public static UnsafeAllocator create() {
            return new UnsafeAllocator();
        }
    public Object newInstance(Class<?> clazz) {
        try {
            return UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
