package chorbova.velichka.restful.web.service.repository;

import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.model.inventory.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query(value = "SELECT f FROM Food f WHERE f.type = :type")
    List<Food> findByType(ItemType type);

}
