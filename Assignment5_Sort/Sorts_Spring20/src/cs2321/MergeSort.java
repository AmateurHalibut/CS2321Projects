package cs2321;

import java.util.Arrays;

public class MergeSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform a divide and conquer merge sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n lg n)")
	public void sort(E[] array) {

		if(array.length == 1){
			return;
		}

		int mid = array.length / 2;

		// Split the array in half
		E[] left = Arrays.copyOfRange(array, 0, mid);
		E[] right = Arrays.copyOfRange(array, mid, array.length);

		// Recursively call sort to divide the array
		sort(left);
		sort(right);

		// merge the sorted arrays
		merge(left, right, array);

	}

	/**
	 * Recursively merges the divided arrays in sorted order
	 * @param array1 left array
	 * @param array2 right array
	 * @param sortedArray target array
	 */
	private void merge(E[] array1, E[] array2, E[] sortedArray){
		int i = 0;
		int j = 0;
		int k = 0;

		// Loop through the arrays inserting the lesser value at the index into the target array
		while(i < array1.length && j < array2.length){
			if(array1[i].compareTo(array2[j]) < 1){
				sortedArray[k] = array1[i];
				i++;
			} else{
				sortedArray[k] = array2[j];
				j++;
			}
			k++;
		}

		// Fill remaining from left array
		while(i < array1.length){
			sortedArray[k] = array1[i];
			i++;
			k++;
		}

		// Fill remaining from right array
		while(j < array2.length){
			sortedArray[k] = array2[j];
			j++;
			k++;
		}
	}
}

