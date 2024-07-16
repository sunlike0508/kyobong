package kyobong.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByIdIn(List<Long> categoryIDList);

    Optional<CategoryEntity> findCategoryEntityByName(String name);
}