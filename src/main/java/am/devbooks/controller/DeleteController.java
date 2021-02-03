package am.devbooks.controller;

import am.devbooks.entity.Book;
import am.devbooks.service.AWSS3Service;
import am.devbooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DeleteController {

    private final BookService bookService;
    private final AWSS3Service awss3Service;

    @Autowired
    public DeleteController(BookService bookService, AWSS3Service awss3Service) {
        this.bookService = bookService;
        this.awss3Service = awss3Service;
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        String bookImage = book.getBookImage().substring(50);
        String bookContent = book.getBookContent().substring(50);
        awss3Service.deleteFile(bookImage);
        awss3Service.deleteFile(bookContent);
        bookService.deleteBookById(id);
        return "redirect:/";
    }
}
