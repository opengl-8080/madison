package madison.util;

import java.util.concurrent.ConcurrentHashMap;

public class ObjectContainer {
    private static final ObjectContainer instance = new ObjectContainer();
    
    public static ObjectContainer getInstance() {
        return instance;
    }
    
    private ObjectContainer() {}
    
    private final ConcurrentHashMap<Class<?>, ObjectProvider<?>> pool = new ConcurrentHashMap<>();

    public <T> void register(Class<T> type, T object) {
        register(type, (ObjectProvider<T>) () -> object);
    }
    
    public <T> void register(Class<T> type, ObjectProvider<T> provider) {
        pool.put(type, provider);
    }

    public <T> T get(Class<T> type) {
        return type.cast(pool.get(type).provide());
    }
}
