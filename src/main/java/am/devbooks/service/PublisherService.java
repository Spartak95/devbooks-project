package am.devbooks.service;

import am.devbooks.entity.Publisher;
import am.devbooks.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private PublisherRepository repository;

    @Autowired
    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }

    public List<Publisher> getAllPublisher() {
        return repository.findAll();
    }

    public Publisher getPublisherById(int id) {
        return repository.getOne(id);
    }

    public void createPublisher(Publisher publisher) {
        repository.save(publisher);
    }

    public void updatePublisher(int id) {
        Publisher publisherById = getPublisherById(id);
        repository.save(publisherById);
    }

    public void deletePublisher(Publisher publisher) {
        repository.delete(publisher);
    }

    public void deletePublisherById(Integer id) {
        repository.deleteById(id);
    }
}
