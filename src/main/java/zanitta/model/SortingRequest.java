package zanitta.model;

import java.util.List;

public class SortingRequest {
    private List<Integer> numbers;
    private String algorithm;

    // Getters and Setters
    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
