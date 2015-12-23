package com.pedrovalencia.bankAccount.services;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public interface AccountService {

    public Optional<BigDecimal> checkBalance(String accountNumber);
    public boolean withdrawAmount(String accountNumber, BigDecimal amount);

}
