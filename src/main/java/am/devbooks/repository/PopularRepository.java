package am.devbooks.repository;

import am.devbooks.entity.Popular;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularRepository extends JpaRepository<Popular, Integer>, PagingAndSortingRepository<Popular, Integer> {
    Page<Popular> findAll(Pageable pageable);
}
