package zanitta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zanitta.service.SortingService;
import zanitta.util.ResponseData;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sorting")
public class SortingController {

    @Autowired
    private SortingService sortingService;

    private List<Integer> numbers = new ArrayList<>();

    @PostMapping("/{algorithm}")
    public ResponseData<List<Integer>> sortNumbers(@PathVariable String algorithm) {
        try {
            List<Integer> sortedNumbers = sortingService.sort(numbers, algorithm);

            // Adding HATEOAS links
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                    .sortNumbers(algorithm)).withSelfRel();
            Link addLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                    .addNumbers(0)).withRel("add-number");
            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                    .updateNumbers(new int[]{0, 0})).withRel("update-number");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                    .deleteNumbers(0)).withRel("delete-number");

            List<Link> links = List.of(selfLink, addLink, updateLink, deleteLink);

            return new ResponseData<>(200, "Sorting successful", sortedNumbers, links);
        } catch (IllegalArgumentException e) {
            return new ResponseData<>(400, "Invalid algorithm: " + e.getMessage(), null, null);
        } catch (Exception e) {
            return new ResponseData<>(500, "Something went wrong: " + e.getMessage(), null, null);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateNumbers(@RequestBody int[] arr) {
        if (arr.length < 2) {
            return ResponseEntity.badRequest().body("Request body must contain [index, element].");
        }

        int index = arr[0];
        int element = arr[1];

        if (index < 0 || index >= numbers.size()) {
            return ResponseEntity.badRequest().body("Index out of bounds.");
        }

        numbers.set(index, element);

        // Adding HATEOAS links
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .updateNumbers(arr)).withSelfRel();
        Link addLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .addNumbers(0)).withRel("add-number");
        Link sortLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .sortNumbers("algorithm-name")).withRel("sort-numbers");  // Replace "algorithm-name" with actual param
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .deleteNumbers(index)).withRel("delete-number");

        List<Link> links = List.of(selfLink, addLink, sortLink, deleteLink);
        return ResponseEntity.ok(new ResponseData<>(200, "Update successful", numbers, links));
    }

    @PostMapping("/add")
    public ResponseData<List<Integer>> addNumbers(@RequestBody int element) {
        System.out.println("The number: " + element);
        numbers.add(element);

        // Adding HATEOAS links
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .addNumbers(element)).withSelfRel();
        Link viewSorted = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .sortNumbers("algorithm-name")).withRel("sort-numbers");
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .deleteNumbers(0)).withRel("delete-number");
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .updateNumbers(new int[]{0, 0})).withRel("update-number");

        List<Link> links = List.of(selfLink, viewSorted, deleteLink, updateLink);
        return new ResponseData<>(201, "Add successful", numbers, links);
    }

    @PostMapping("/delete")
    public ResponseData<List<Integer>> deleteNumbers(@RequestBody int index) {
        if (index < 0 || index >= numbers.size()) {
            return new ResponseData<>(400, "Index out of bounds", null, null);
        }

        numbers.remove(index);

        // Adding HATEOAS links
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .deleteNumbers(index)).withSelfRel();
        Link addLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .addNumbers(0)).withRel("add-number");
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .updateNumbers(new int[]{0, 0})).withRel("update-number");
        Link sortLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class)
                .sortNumbers("algorithm-name")).withRel("sort-numbers");

        List<Link> links = List.of(selfLink, addLink, updateLink, sortLink);

        return new ResponseData<>(204, "Delete successful", numbers, links);
    }
}
