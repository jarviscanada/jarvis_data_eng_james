package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompareMapsIntTest {

    private Map<Integer, String> map1;
    private Map<Integer, String> map2;
    private Map<Integer, String> map3;

    @Before
    public void setup() {
        map1 = new HashMap<>();

        map1.put(1, "A");
        map1.put(2, "B");
        map1.put(3, "C");

        //Same as map1
        map2 = new HashMap<>();

        map2.put(3, "C");
        map2.put(1, "A");
        map2.put(2, "B");

        //Different from map1
        map3 = new HashMap<>();

        map3.put(1, "A");
        map3.put(2, "B");
        map3.put(3, "C");
        map3.put(3, "D");
    }

    @Test
    public void compareMaps1() {
        CompareMaps compareMaps = new CompareMaps();
        assertEquals(compareMaps.compareMaps1(map1, map2), true);
        assertEquals(compareMaps.compareMaps1(map1, map3), false);
    }

    @Test
    public void compareMaps2() {
        CompareMaps compareMaps = new CompareMaps();
        assertEquals(compareMaps.compareMaps2(map1, map2), true);
        assertEquals(compareMaps.compareMaps2(map1, map3), false);
    }
}

