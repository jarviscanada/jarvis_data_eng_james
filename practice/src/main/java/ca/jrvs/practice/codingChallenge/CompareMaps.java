package ca.jrvs.practice.codingChallenge;

import java.util.Iterator;
import java.util.Map;

/**
 * ticket link: https://www.notion.so/How-to-compare-two-maps-243bca80779541c59487b61d5a3e4f4f
 */
public class CompareMaps {

    /**
     *Big-O: O(n) time
     *       O(1) space
     * Justification: The implementation of .equls is O(n), and it only runs one time
     */
    public <K,V> boolean compareMaps1(Map<K,V> m1, Map<K,V> m2){
        return m1.equals(m2);
    }

    /**
     *Big-O: O(n) time
     *       O(1) space
     * Justification: The implementation is O(n), since it only iterate through the map once.
     */
    public <K,V> boolean compareMaps2(Map<K,V> m1, Map<K,V> m2){
        if (m1.size() != m2.size()) {
            return false;
        }

        Iterator<Map.Entry<K, V>> it = m1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> pair = it.next();
            if (!m2.containsKey(pair.getKey())) {
                return false;
            }

            if (m2.get(pair.getKey()) != pair.getValue()) {
                return false;
            }
        }
        return true;
    }
}
