package am.devbooks.repository;

import am.devbooks.entity.Novelty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoveltyRepository extends JpaRepository<Novelty, Integer>, PagingAndSortingRepository<Novelty, Integer> {
    Page<Novelty> findAll(Pageable pageable);
}
