package kyobong.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryEntityRepository extends JpaRepository<BookCategoryEntity, Long> {

}