package com.pedrovalencia.bankAccount.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public class AccountServiceImpl implements AccountService {

    private HashMap<String, BigDecimal> accounts;

    public AccountServiceImpl() {
        this.accounts = new HashMap<>();

        this.accounts.put("01001", new BigDecimal(2738.59));
        this.accounts.put("01002", new BigDecimal(23.00));
        this.accounts.put("01003", new BigDecimal(0.00));

    }


    public Optional<BigDecimal> checkBalance(String accountNumber) {
        return this.accounts
                .entrySet()
                .stream()
                .filter(elem -> accountNumber.equals(elem.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();

    }

    /**
     * his method updates the account if accountNumber matches. It produce side effects,
     * that is not good for functional programming approach, and in case of multiple threads access to
     * this method at same time, we could have conflicts. In that case we should make this method
     * synchronized or use a thread safe structure (Collection.synchronizedMap(new HashMap(...))
     * @param accountNumber
     * @param amount
     * @return
     */
    public boolean withdrawAmount(String accountNumber, BigDecimal amount) {
        Optional<BigDecimal> balance = checkBalance(accountNumber);

        //We can't do in a functional way because we're modifying member class (side-effect)
        if(balance.isPresent() && hasEnoughMoney(balance, amount)) {
            this.accounts.put(accountNumber,this.accounts.get(accountNumber).subtract(amount));
            return true;
        }

        return false;
    }

    private boolean hasEnoughMoney(Optional<BigDecimal> balance, BigDecimal amount) {
        return balance.get().compareTo(amount) >= 0;
    }

}
