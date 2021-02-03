package am.devbooks.service;

import am.devbooks.entity.Author;
import am.devbooks.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private AuthorRepository repository;

    @Autowired
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAllAuthor() {
        return repository.findAll();
    }

    public Author getAuthorById(int id) {
        return repository.getOne(id);
    }

    public void createAuthor(Author author) {
        repository.save(author);
    }

    public void updateAuthor(int id) {
        Author authorById = getAuthorById(id);
        repository.save(authorById);
    }

    public void deleteAuthor(Author author) {
        repository.delete(author);
    }

    public void deleteAuthorById(Integer id) {
        repository.deleteById(id);
    }
}
