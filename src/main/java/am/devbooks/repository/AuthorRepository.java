package am.devbooks.repository;

import am.devbooks.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
     @Query("SELECT DISTINCT new Author(a.fio) FROM Author a")
     List<Author> findAll();
}
