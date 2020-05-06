package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TaskSchedulingTest {

    int[][] tasks;

    @Before
    public void setUp() throws Exception {

        tasks = new int[][]{};
    }

    @Test
    public void testNumOfMachines() {
        tasks = new int[][]{{1, 4}, {1, 3}, {2, 5}, {3, 7}, {4, 7}, {6, 9}, {7, 8}};

        assertEquals(3, TaskScheduling.NumOfMachines(tasks));
    }

    @Test
    public void testNumOfMachines2() {
        tasks = new int[][]{{1, 4}, {4, 7}, {7, 8}, {8, 10}};

        assertEquals(1, TaskScheduling.NumOfMachines(tasks));
    }

    @Test
    public void testNumOfMachines3() {
        tasks = new int[][]{{1, 2}, {1, 2}, {7, 8}, {1, 2}, {2, 3}, {7, 8}, {2, 3}, {2, 3},  {7, 8}, {7, 8}};

        assertEquals(4, TaskScheduling.NumOfMachines(tasks));
    }


    @Test
    public void testNumOfMachines4() {
        tasks = new int[][]{{10, 15}, {15, 20}, {1, 4}, {6, 8}};

        assertEquals(1, TaskScheduling.NumOfMachines(tasks));
    }

    @Test
    public void testNumOfMachines5() {
        tasks = new int[][]{{100, 105}, {10, 50}, {1, 20}, {19, 101}};

        assertEquals(3, TaskScheduling.NumOfMachines(tasks));
    }

    @Test
    public void testNumOfMachines6() {

        assertEquals(0, TaskScheduling.NumOfMachines(tasks));
    }
}
