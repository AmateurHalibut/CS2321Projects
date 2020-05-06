package cs2321;

public class InPlaceHeapSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place heap sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n lg n)")
	public void sort(K[] array) {

	    int heapSize = array.length;

	    // Create a max heap order
	    for(int i = (array.length / 2) - 1; i >= 0; i--){
	        maxHeapify(array, i, heapSize);
        }


	    for(int i = array.length - 1; i > 0; i--){
	        swap(array, 0, i);
	        heapSize--;
	        maxHeapify(array, 0, heapSize);
        }

	}

	private void maxHeapify(K[] arr, int i, int maxIndex){
        // Base Case
        if(hasLeft(arr, i, maxIndex) == false) return;

        // Recursive Step
        // i has children
        int s = left(i);

        // If i has a right child, compare left and right children
        if(hasRight(arr, i, maxIndex)){
            int r = right(i);
            if(arr[s].compareTo(arr[r]) < 0){
                s = r;
            }
        }

        if(arr[s].compareTo(arr[i]) > 0){
            swap(arr,s,i);
            maxHeapify(arr, s, maxIndex);
        }
	}

    /**
     * Returns the left child of the current index
     * @param i current index
     * @return index of the left child
     */
    private int left(int i){
        return (i * 2) + 1;
    }

    /**
     * Returns the right child of the current index
     * @param i current index
     * @return index of the right child
     */
    private int right(int i){
        return (i * 2) + 2;
    }

    /**
     * Checks if the current index has a left child
     * @param i current index
     * @return true if has a left child, false otherwise
     */
    private boolean hasLeft(K[] arr, int i, int maxIndex){
        if (left(i) < maxIndex){
            return true;
        }
        return false;
    }

    /**
     * Checks if the current index has a right child
     * @param i current index
     * @return true if has a right child, false otherwise
     */
    private boolean hasRight(K[] arr, int i, int maxIndex){
        if(right(i) < maxIndex){
            return true;
        }
        return false;
    }

    /**
     * Swaps 2 elements of an array
     * @param array the array
     * @param i first index
     * @param j second index
     */
    private void swap(K[] array, int i, int j){
        K temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
