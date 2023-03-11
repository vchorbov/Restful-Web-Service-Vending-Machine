package chorbova.velichka.restful.web.service.repository;

import chorbova.velichka.restful.web.service.model.balance.Balance;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
