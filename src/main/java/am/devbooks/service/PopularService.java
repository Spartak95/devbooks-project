package am.devbooks.service;

import am.devbooks.entity.Book;
import am.devbooks.entity.Popular;
import am.devbooks.repository.PopularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularService {
    private PopularRepository repository;

    @Autowired
    public PopularService(PopularRepository repository) {
        this.repository = repository;
    }

    public Popular getPopularById(int id) {
        return repository.getOne(id);
    }

    public void savePopular(Popular popular) {
        repository.save(popular);
    }

    public void updatePopular(int id) {
        Popular popularById = getPopularById(id);
        repository.save(popularById);
    }

    public Page<Popular> getAllPopular() {
        Sort sort = Sort.by("visit").descending();
        Pageable pageable = PageRequest.of(0, 8, sort);
        return repository.findAll(pageable);
    }

    public void deletePopular(Popular popular) {
        repository.delete(popular);
    }

    public void deletePopularById(Integer id) {
        repository.deleteById(id);
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        for (Popular p : getAllPopular()) {
            books.add(p.getBook());
        }
        return books;
    }
}
