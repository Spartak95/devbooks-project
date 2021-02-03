package am.devbooks.service;

import am.devbooks.entity.Book;
import am.devbooks.entity.Novelty;
import am.devbooks.repository.NoveltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoveltyService {
    private NoveltyRepository repository;

    @Autowired
    public NoveltyService(NoveltyRepository repository) {
        this.repository = repository;
    }

    public Novelty getNoveltyById(int id) {
        return repository.getOne(id);
    }

    public void createNovelty(Novelty novelty) {
        repository.save(novelty);
    }

    public void updateNovelty(int id) {
        Novelty noveltyById = getNoveltyById(id);
        repository.save(noveltyById);
    }

    public Page<Novelty> getAllNovelty() {
        Sort sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(0, 8, sort);
        return repository.findAll(pageable);
    }

    public void deleteNovelty(Novelty novelty) {
        repository.delete(novelty);
    }

    public void deleteNoveltyById(Integer id) {
        repository.deleteById(id);
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        for (Novelty n : getAllNovelty()) {
            books.add(n.getBook());
        }
        return books;
    }
}
