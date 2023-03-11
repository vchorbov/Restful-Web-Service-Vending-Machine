package chorbova.velichka.restful.web.service.repository;

import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {
    @Query(value = "SELECT b FROM Beverage b WHERE b.type = :type")
    List<Beverage> findByType(@Param("type") ItemType type);
}
