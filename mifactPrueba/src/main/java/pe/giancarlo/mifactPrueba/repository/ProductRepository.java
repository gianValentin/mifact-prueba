package pe.giancarlo.mifactPrueba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pe.giancarlo.mifactPrueba.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("""
            SELECT p FROM Product p
            WHERE (:text IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :text, '%')))
            """)
    Page<Product> buscar(@Param("text") String text, Pageable pageable);
}
