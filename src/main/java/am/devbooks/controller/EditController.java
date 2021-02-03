package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Book;
import am.devbooks.entity.Language;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class EditController {

    private final BookService bookService;
    private final LanguageService languageService;
    private final AuthorService authorService;

    @Autowired
    public EditController(BookService bookService, LanguageService languageService, AuthorService authorService) {
        this.bookService = bookService;
        this.languageService = languageService;
        this.authorService = authorService;
    }

    @GetMapping("/edit/{id}")
    public String updateBookInfo(@PathVariable("id") int id, Model model) {
        Book bookById = bookService.getBookById(id);
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("books", bookById);
        model.addAttribute("allLanguage", allLanguage);
        return "edit_book";
    }

    @PostMapping("/edit")
    public String updateBook(@RequestParam("bookName") String bookName, @RequestParam("bookAuthor") String bookAuthor,
                             @RequestParam("bookLangProg") String bookLangProg, @RequestParam("bookPub") String bookPub,
                             @RequestParam("bookLang") String bookLang, @RequestParam("bookYear") int bookYear,
                             @RequestParam("bookPage") int bookPage, @RequestParam("bookPDF") MultipartFile bookPDF,
                             @RequestParam("bookImage") MultipartFile bookImage, @RequestParam("bookId") int id) {

        bookService.editBook(id, bookName, bookLangProg, bookAuthor, bookPub, bookImage, bookPDF, bookYear, bookPage, bookLang);
        return "redirect:/";
    }
}
