package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Language;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AddController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public AddController(LanguageService languageService, BookService bookService, AuthorService authorService) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/add")
    public String createNewBook(Model model) {
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);
        return "add_book";
    }

    @PostMapping("/add")
    public String addNewBook(@RequestParam("bookName") String bookName, @RequestParam("bookAuthor") String bookAuthor,
                             @RequestParam("bookLangProg") String bookLangProg, @RequestParam("bookPub") String bookPub,
                             @RequestParam("bookLang") String bookLang, @RequestParam("bookYear") int bookYear,
                             @RequestParam("bookPage") int bookPage, @RequestParam("bookPDF") MultipartFile bookPDF,
                             @RequestParam("bookImage") MultipartFile bookImage) {

        bookService.createBook(bookName, bookLangProg, bookAuthor, bookPub, bookImage, bookPDF, bookYear, bookPage, bookLang);
        return "redirect:/";
    }
}
