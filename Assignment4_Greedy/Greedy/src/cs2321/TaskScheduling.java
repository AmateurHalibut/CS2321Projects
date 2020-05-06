package cs2321;

/**
 * Implementation of the Task Scheduling problem
 * <p>
 * Course: CS2321 Section ALL
 * Assignment: #4
 *
 * @author Alex Hromada
 */

public class TaskScheduling {
    /**
     * Goal: Perform all the tasks using a minimum number of machines.
     *
     * @param tasks tasks[i][0] is start time for task i
     *              tasks[i][1] is end time for task i
     * @return The minimum number of machines
     */
    public static int NumOfMachines(int[][] tasks) {

        HeapPQ<Integer, Integer> pq = new HeapPQ<>();

        tasks = PQSort(tasks);      // Sort tasks by start time, is redundant since a PQ could be used instead of a
                                    // whole PQ sort

        int m = 0;
        for (int i = 0; i < tasks.length; i++) {
            if (pq.isEmpty()) {                             // If pq is empty, start up first machine
                pq.insert(tasks[i][1], 1);
                m++;
            } else if (tasks[i][0] >= pq.min().getKey()) {  // Check if the start time is greater than or equal to the min end time
                pq.replaceKey(pq.min(), tasks[i][1]);
            } else {
                m++;
                pq.insert(tasks[i][1], m);                  // If no machines available, start a new one
            }
        }
        return m;
    }

    /**
     * PQ sort to sort tasks by start time
     *
     * @param array
     * @return sorted array
     */
    private static int[][] PQSort(int[][] array) {
        HeapPQ<Integer, int[]> sort = new HeapPQ<>();

        for (int i = 0; i < array.length; i++) {
            sort.insert(array[i][0], array[i]);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = sort.removeMin().getValue();
        }

        return array;
    }
}
