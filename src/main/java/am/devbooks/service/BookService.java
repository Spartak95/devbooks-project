package am.devbooks.service;

import am.devbooks.entity.*;
import am.devbooks.repository.BookRepository;
import am.devbooks.repository.NoveltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;
    private final NoveltyRepository noveltyRepository;
    private final LanguageService languageService;
    private final AWSS3Service awss3Service;

    @Autowired
    public BookService(BookRepository repository, NoveltyRepository noveltyRepository,
                       LanguageService languageService, AWSS3Service awss3Service) {
        this.repository = repository;
        this.noveltyRepository = noveltyRepository;
        this.languageService = languageService;
        this.awss3Service = awss3Service;
    }

    public Page<Book> getAllBook(String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);
        if(keyword != null) {
            return repository.findAll(pageable, keyword);
        }
        return repository.findAll(pageable);
    }

    public List<Book> getAllBook() {
        return repository.findAll();
    }

    public Page<Book> getAllBook(int pageNumber, Language language) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);
        return repository.findAll(pageable, language);
    }

    public Book getBookById(int id) {
        return repository.getOne(id);
    }

    public void createBook(String bookName, String lang, String author, String pub,
                           MultipartFile bookImage, MultipartFile bookContent, int year,
                           int page, String bookLang) {
        Author a = new Author(author);
        Publisher publisher = new Publisher(pub);
        Language language = languageService.getLanguageByName(lang);
        Popular popular = new Popular();
        String image = awss3Service.uploadImage(bookImage, true);
        String content = awss3Service.uploadPDF(bookContent, true);

        Book book = new Book(bookName, image, content, year, page, bookLang, language, a, publisher, popular);

        repository.save(book);
        noveltyRepository.save(new Novelty(new Date(), book));
    }

    public void editBook(int id, String bookName, String lang, String author, String pub,
                         MultipartFile bookImage, MultipartFile bookContent, int year,
                         int page, String bookLang) {

        Book book = repository.getOne(id);
        Author a = book.getAuthor();
        a.setFio(author);
        Publisher p = book.getPublisher();
        p.setPublisherName(pub);
        Language l = languageService.getLanguageByName(lang);

        if (!bookImage.isEmpty()) {
            String image = awss3Service.uploadImage(bookImage, true);
            book.setBookImage(image);
        }

        if (!bookContent.isEmpty()) {
            String content = awss3Service.uploadPDF(bookContent, true);
            book.setBookContent(content);
        }

        book.setId(id);
        book.setBookName(bookName);
        book.setLanguage(l);
        book.setAuthor(a);
        book.setPublisher(p);
        book.setBookYear(year);
        book.setBookPage(page);
        book.setBookLang(bookLang);

        repository.save(book);
    }

    public void updateBook(int id) {
        Book bookById = getBookById(id);
        repository.save(bookById);
    }

    public void deleteBook(Book book) {
        repository.delete(book);
    }

    public void deleteBookById(Integer id) {
        repository.deleteById(id);
    }

    public List<String> getBookLang() {
        return repository.findAllBookByLanguage();
    }

    public Page<Book> getFilter(String author, String lang, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);

        if (author != null & lang != null) {
            return repository.filterAuthorAndLanguage(author, lang, pageable);
        } else if (author != null) {
            return repository.filterAuthor(author, pageable);
        } else {
            return repository.filterLanguage(lang, pageable);
        }
    }
}
