package com.example.my_project.controller;

import com.example.my_project.model.BookModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private List<BookModel> books = new ArrayList<>(
            Arrays.asList(
                    new BookModel("1", "Book 1", 100),
                    new BookModel("2", "Book 2", 200),
                    new BookModel("3", "Book 3", 300)
            )
    );

    @GetMapping
    public List<BookModel> getAllBook() {
        return books;
    }

    @GetMapping("/{id}")
    public BookModel getBookById(@PathVariable String id) {
        return books.stream().filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public BookModel addBook(@RequestBody BookModel book) {
        books.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public BookModel updateBook(@PathVariable String id, @RequestBody BookModel updateBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                books.set(i, updateBook);
                return updateBook;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}
