package am.devbooks.entity;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_image")
    private String bookImage;

    @Column(name = "book_content")
    private String bookContent;

    @Column(name = "book_year")
    private int bookYear;

    @Column(name = "book_page")
    private int bookPage;

    @Column(name = "book_lang")
    private String bookLang;

    @ManyToOne
    @JoinColumn(name = "prog_lang_id")
    private Language language;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Author author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Publisher publisher;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "pop_id")
    private Popular popular;

    @OneToOne(mappedBy = "book", targetEntity = Novelty.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Novelty novelty;

    public Book() {
    }

    public Book(int id, String bookName, String bookImage, Author author) {
        this.id = id;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.author = author;
    }

    public Book(int id, String bookName, String bookImage, String bookContent, int bookYear, int bookPage,
                String bookLang, Language language, Author author, Publisher publisher, Popular popular) {
        this.id = id;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookContent = bookContent;
        this.bookYear = bookYear;
        this.bookPage = bookPage;
        this.bookLang = bookLang;
        this.language = language;
        this.author = author;
        this.publisher = publisher;
        this.popular = popular;
    }

    public Book(String bookName, String bookImage, String bookContent, int bookYear, int bookPage,
                String bookLang, Language language, Author author, Publisher publisher, Popular popular) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookContent = bookContent;
        this.bookYear = bookYear;
        this.bookPage = bookPage;
        this.bookLang = bookLang;
        this.language = language;
        this.author = author;
        this.publisher = publisher;
        this.popular = popular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public int getBookPage() {
        return bookPage;
    }

    public void setBookPage(int bookPage) {
        this.bookPage = bookPage;
    }

    public String getBookLang() {
        return bookLang;
    }

    public void setBookLang(String bookLang) {
        this.bookLang = bookLang;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Novelty getNovelty() {
        return novelty;
    }

    public void setNovelty(Novelty novelty) {
        this.novelty = novelty;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Popular getPopular() {
        return popular;
    }

    public void setPopular(Popular popular) {
        this.popular = popular;
    }
}
