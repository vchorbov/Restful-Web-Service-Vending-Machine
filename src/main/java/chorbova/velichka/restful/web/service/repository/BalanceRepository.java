package chorbova.velichka.restful.web.service.repository;

import chorbova.velichka.restful.web.service.model.balance.Balance;
import chorbova.velichka.restful.web.service.model.balance.CoinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query(value = "SELECT b FROM Balance b WHERE b.amount = :coinType")
    List<Balance> findByType(@Param("coinType") CoinType coinType);
}
