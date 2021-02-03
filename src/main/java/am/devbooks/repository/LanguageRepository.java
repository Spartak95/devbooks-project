package am.devbooks.repository;

import am.devbooks.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    @Query("SELECT l FROM Language l WHERE l.langName = :langName")
    Language findLanguageByName(String langName);
}
