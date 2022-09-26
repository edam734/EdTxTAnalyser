package lab.eduardo.edtxtanalyser.old;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Eduardo
 *
 */
public class SubtitleMap {

    private Map<String, Integer> map;
    private int scopeResult = 10; // by default

    /**
     * @param map
     */
    public SubtitleMap() {
        this.map = new TreeMap<>();
    }

    /**
     * @param scopeResult
     */
    public SubtitleMap(int scopeResult) {
        this();
        this.scopeResult = scopeResult;
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Integer put(String key) {
        return map.put(key, 1);
    }

    public Integer get(String key) {
        return map.get(key);
    }

    public Integer update(String key) {
        return map.replace(key, map.get(key) + 1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        Map<String, Integer> sortedMap = sortByValues(map);

        Iterator<Entry<String, Integer>> iterator = sortedMap.entrySet().iterator();
        int count = 0;
        while (iterator.hasNext() && count < scopeResult) {
            Map.Entry<String, Integer> entry = iterator.next();
            result.append(entry.getKey() + " => " + entry.getValue());
            result.append(System.getProperty("line.separator"));
            count++;
        }

        return "[" + result.toString() + "]";
    }

    // Method for sorting the TreeMap based on values (reversed)
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));
                if (compare == 0)
                    return 1;
                else
                    return compare;
            }
        };

        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator.reversed());
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
