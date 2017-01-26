package dido.auntaccount.dido.auntaccount.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheMap<K, V> extends LinkedHashMap<K, V> {

    private int initialCapacity;

    public CacheMap(int initialCapacity) {
        super(initialCapacity);
        this.initialCapacity = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > initialCapacity;
    }

}
