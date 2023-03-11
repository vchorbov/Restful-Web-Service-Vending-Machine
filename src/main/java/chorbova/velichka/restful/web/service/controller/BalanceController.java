package chorbova.velichka.restful.web.service.controller;

import chorbova.velichka.restful.web.service.exceptions.NotSupportedCoinTypeException;
import chorbova.velichka.restful.web.service.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private static final String RETURNED_TOTAL = "returned total";
    private static final String ADDED_TOTAL = "added total";

    @Autowired
    private BalanceService balanceService;

    @PutMapping("/{coinType}")
    public ResponseEntity addCoin(@PathVariable String coinType) {

        try {
            balanceService.addCoin(coinType);
        } catch (NotSupportedCoinTypeException ex) {
            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(balanceService.getAllCoinsBalance(), ADDED_TOTAL,
                balanceService.calculateBalance(), HttpStatus.OK);

    }

    @DeleteMapping("/")
    public ResponseEntity resetCoinBalance() {
        // calculate the balance before the operation of deletion is performed
        BigDecimal deletedBalance = balanceService.calculateBalance();

        balanceService.deleteCoinBalance();
        return successResponse(balanceService.getAllCoinsBalance(), RETURNED_TOTAL,
                deletedBalance, HttpStatus.OK);

    }

    private ResponseEntity successResponse(Object value, String balanceMessage, BigDecimal balance, HttpStatus status) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("current balance", value);
        response.put(balanceMessage, balance);
        response.put("status", status.value());
        response.put("message", status.getReasonPhrase());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    private ResponseEntity exceptionResponse(String errorMessage, HttpStatus status) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("error", errorMessage);
        response.put("status", status.value());
        response.put("message", status.getReasonPhrase());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}

