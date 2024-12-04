package zanitta.util;

import org.springframework.hateoas.Link;

import java.util.List;

public class ResponseData<T> {

    private int statusCode;
    private String message;
    private T data;
    private List<Link> links;

    // Constructor
    public ResponseData(int statusCode, String message, T data, List<Link> links) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.links = links;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    // Static factory methods for common responses
    public static <T> ResponseData<T> success(String message, T data, List<Link> links) {
        return new ResponseData<>(200, message, data, links);
    }

    public static <T> ResponseData<T> error(String message, T data, List<Link> links) {
        return new ResponseData<>(500, message, data, links);
    }

    public static <T> ResponseData<T> badRequest(String message, T data, List<Link> links) {
        return new ResponseData<>(400, message, data, links);
    }
}
