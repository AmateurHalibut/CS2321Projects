package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FractionalKnapsackTest {

    int[][] items;

    @Before
    public void setUp() throws Exception {
        items = new int[][]{};

    }

    @Test
    public void testMaximumValue() {
        items = new int[][]{{4, 12}, {8, 32}, {2, 40}, {6, 30}, {1, 50}};

        assertEquals(124, (int) FractionalKnapsack.MaximumValue(items, 10));

    }

    @Test
    public void testMaximumValue2() {
        items = new int[][]{{4, 12}};

        assertEquals(12, (int) FractionalKnapsack.MaximumValue(items, 10));

    }

    @Test
    public void testMaximumValue3() {
        assertEquals(0, (int) FractionalKnapsack.MaximumValue(items, 10));

    }

    @Test
    public void testMaximumValue4() {
        items = new int[][]{{4, 12}, {8, 32}, {2, 40}, {6, 30}, {1, 50}, {3, 24}};

        // 1 x 50 + 2 x 20 + 3 x 8 + 5 x 4 = 134
        assertEquals(134, (int) FractionalKnapsack.MaximumValue(items, 10));

    }

    @Test
    public void testMaximumValue5() {
        items = new int[][]{{4, 12}};

        assertEquals(3, (int) FractionalKnapsack.MaximumValue(items, 1));

    }

    @Test
    public void testMaximumValue6() {
        items = new int[][]{{4, 12}, {8, 32}, {2, 40}, {6, 30}, {1, 50}, {3, 24}};

        // 1 x 50 + 2 x 20 + 3 x 8 + 6 x 5 + 8 x 4 = 176
        assertEquals(176, (int) FractionalKnapsack.MaximumValue(items, 20));

    }

    @Test
    public void testMaximumValue7() {
        items = new int[][]{{4, 12}};

        assertEquals(0, (int) FractionalKnapsack.MaximumValue(items, 0));

    }

}
