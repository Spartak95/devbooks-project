package am.devbooks.service;

import am.devbooks.entity.Language;
import am.devbooks.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private LanguageRepository repository;

    @Autowired
    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    public List<Language> getAllLanguage() {
        return repository.findAll();
    }

    public Language getLanguageById(int id) {
        return repository.getOne(id);
    }

    public void createLanguage(Language language) {
        repository.save(language);
    }

    public void updateLanguage(int id) {
        Language languageById = getLanguageById(id);
        repository.save(languageById);
    }

    public void deleteLanguage(Language language) {
        repository.delete(language);
    }

    public void deleteLanguageById(Integer id) {
        repository.deleteById(id);
    }

    public Language getLanguageByName(String name) {
        return repository.findLanguageByName(name);
    }
}
