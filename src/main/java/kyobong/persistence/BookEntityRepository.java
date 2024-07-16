package kyobong.persistence;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {


    List<BookEntity> findAllByBookCategoryListIsIn(Set<BookCategoryEntity> bookCategoryEntityList);
}