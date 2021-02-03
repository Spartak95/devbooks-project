package am.devbooks.repository;

import am.devbooks.entity.Book;
import am.devbooks.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {
    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) " +
            "FROM Book b INNER JOIN b.author a WHERE b.bookName " +
            "LIKE %?1% or a.fio LIKE %?1%")
    List<Book> findAll(String keyword);

    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) " +
            "FROM Book b WHERE b.language = :language")
    Page<Book> findAll(Pageable pageable, Language language);

    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) " +
            "FROM Book b INNER JOIN b.author a WHERE b.bookName " +
            "LIKE %?1% or a.fio LIKE %?1%")
    Page<Book> findAll(Pageable pageable);

    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) " +
            "FROM Book b INNER JOIN b.author a WHERE b.bookName " +
            "LIKE %?1% or a.fio LIKE %?1%")
    Page<Book> findAll(Pageable pageable, String keyword);

    @Query("SELECT DISTINCT b.bookLang FROM Book b")
    List<String> findAllBookByLanguage();

    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) FROM Book b INNER JOIN b.author a" +
            " WHERE a.fio = :author AND b.bookLang = :lang")
    Page<Book> filterAuthorAndLanguage(String author, String lang, Pageable pageable);

    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) FROM Book b INNER JOIN b.author a" +
            " WHERE a.fio = :author")
    Page<Book> filterAuthor(String author, Pageable pageable);

    @Query("SELECT new Book(b.id, b.bookName, b.bookImage, b.author) FROM Book b INNER JOIN b.author a" +
            " WHERE b.bookLang = :lang")
    Page<Book> filterLanguage(String lang, Pageable pageable);
}
