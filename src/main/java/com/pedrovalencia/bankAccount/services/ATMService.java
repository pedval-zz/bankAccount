package com.pedrovalencia.bankAccount.services;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by pedrovalencia on 23/12/2015.
 */
public interface ATMService {

    public void replenish(int notesOfFive,
                          int notesOfTen,
                          int notesOfTwenty,
                          int notesOfFifty);
    public String checkBalance(String account);
    public HashMap<Integer, Integer> withdraw(String account,
                                              BigDecimal amount);
}
