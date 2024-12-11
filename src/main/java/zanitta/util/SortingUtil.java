package zanitta.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingUtil {

    public static List<Integer> quickSort(List<Integer> numbers) {
        if (numbers.size() <= 1) {
            return numbers;
        }
        int pivot = numbers.get(numbers.size() / 2);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();

        for (int num : numbers) {
            if (num < pivot) {
                left.add(num);
            } else if (num > pivot) {
                right.add(num);
            } else {
                equal.add(num);
            }
        }

        List<Integer> sorted = quickSort(left);
        sorted.addAll(equal);
        sorted.addAll(quickSort(right));
        return sorted;
    }

    public static List<Integer> heapSort(List<Integer> numbers) {
        // Build max heap
        for (int i = numbers.size() / 2 - 1; i >= 0; i--) {
            heapify(numbers, numbers.size(), i);
        }
        // Extract elements from the heap
        for (int i = numbers.size() - 1; i > 0; i--) {
            Collections.swap(numbers, 0, i);
            heapify(numbers, i, 0);
        }
        return numbers;
    }

    private static void heapify(List<Integer> numbers, int size, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size && numbers.get(left) > numbers.get(largest)) {
            largest = left;
        }
        if (right < size && numbers.get(right) > numbers.get(largest)) {
            largest = right;
        }
        if (largest != root) {
            Collections.swap(numbers, root, largest);
            heapify(numbers, size, largest);
        }
    }

    public static List<Integer> mergeSort(List<Integer> numbers) {
        if (numbers.size() <= 1) {
            return numbers;
        }
        int mid = numbers.size() / 2;
        List<Integer> left = mergeSort(numbers.subList(0, mid));
        List<Integer> right = mergeSort(numbers.subList(mid, numbers.size()));
        return merge(left, right);
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        result.addAll(left.subList(i, left.size()));
        result.addAll(right.subList(j, right.size()));
        return result;
    }

    public static List<Integer> radixSort(List<Integer> numbers) {
        int max = numbers.stream().max(Integer::compare).orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(numbers, exp);
        }
        return numbers;
    }

    private static void countingSort(List<Integer> numbers, int exp) {
        int n = numbers.size();
        int[] output = new int[n];
        int[] count = new int[10];

        for (int num : numbers) {
            count[(num / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            output[--count[(numbers.get(i) / exp) % 10]] = numbers.get(i);
        }
        for (int i = 0; i < n; i++) {
            numbers.set(i, output[i]);
        }
    }

    public static List<Integer> bucketSort(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            return numbers;
        }
        int max = numbers.stream().max(Integer::compare).orElse(0);
        int min = numbers.stream().min(Integer::compare).orElse(0);
        int bucketCount = (max - min) / numbers.size() + 1;
        List<List<Integer>> buckets = new ArrayList<>();

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int num : numbers) {
            buckets.get((num - min) / numbers.size()).add(num);
        }
        numbers.clear();
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            numbers.addAll(bucket);
        }
        return numbers;
    }
}
