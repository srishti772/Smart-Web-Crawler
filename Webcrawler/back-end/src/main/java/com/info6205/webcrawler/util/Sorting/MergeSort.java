package com.info6205.webcrawler.util.Sorting;

import com.info6205.webcrawler.util.Set.UrlSetInterface;
import com.info6205.webcrawler.pojo.Url;

import java.util.Comparator;

public class MergeSort {

    public static UrlSetInterface mergeSort(UrlSetInterface visited, Comparator<Url> comparator) {
        if (visited.isEmpty() || visited.getCurrentSize() == 1) {
            return visited;
        }

        Url[] array = visited.toArray();
        mergeSort(array, comparator);

        // Update the sorted array back to the visited set
        visited.clear();
        for (int i = 0; i < array.length; i++) {
            visited.addUrl(array[i]); // Add the sorted entry
        }

        return visited;
    }

    private static void mergeSort(Url[] array, Comparator<Url> comparator) {
        if (array.length > 1) {
            int mid = array.length / 2;
            Url[] leftArray = new Url[mid];
            Url[] rightArray = new Url[array.length - mid];

            System.arraycopy(array, 0, leftArray, 0, mid);
            System.arraycopy(array, mid, rightArray, 0, array.length - mid);

            mergeSort(leftArray, comparator);
            mergeSort(rightArray, comparator);

            merge(array, leftArray, rightArray, comparator);
        }
    }

    private static void merge(Url[] array, Url[] leftArray, Url[] rightArray, Comparator<Url> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                array[mergedIndex++] = leftArray[leftIndex++];
            } else {
                array[mergedIndex++] = rightArray[rightIndex++];
            }
        }

        while (leftIndex < leftArray.length) {
            array[mergedIndex++] = leftArray[leftIndex++];
        }

        while (rightIndex < rightArray.length) {
            array[mergedIndex++] = rightArray[rightIndex++];
        }
    }
}
