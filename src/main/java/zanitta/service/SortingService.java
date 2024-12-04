package zanitta.service;

import org.springframework.stereotype.Service;
import zanitta.util.SortingUtil;

import java.util.List;

@Service
public class SortingService {

    public List<Integer> sort(List<Integer> numbers, String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "quicksort":
                return SortingUtil.quickSort(numbers);
            case "heapsort":
                return SortingUtil.heapSort(numbers);
            case "mergesort":
                return SortingUtil.mergeSort(numbers);
            case "radixsort":
                return SortingUtil.radixSort(numbers);
            case "bucketsort":
                return SortingUtil.bucketSort(numbers);
            default:
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
    }
}
