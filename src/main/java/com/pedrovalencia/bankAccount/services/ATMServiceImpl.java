package com.pedrovalencia.bankAccount.services;

import com.pedrovalencia.bankAccount.utils.AccountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public class ATMServiceImpl implements ATMService {

    Logger logger = LoggerFactory.getLogger(ATMServiceImpl.class);

    private HashMap<Integer, Integer> notes;
    private AccountService accountService;

    public ATMServiceImpl() {
        notes = new HashMap<>();
        accountService = new AccountServiceImpl();
    }

    @Override
    public void replenish(int notesOfFive,
                          int notesOfTen,
                          int notesOfTwenty,
                          int notesOfFifty) {
        //Example to check when no available notes
        notes.put(5, notesOfFive);
        notes.put(10, notesOfTen);
        notes.put(20, notesOfTwenty);
        notes.put(50, notesOfFifty);

        logger.debug("Replenish completed.");
    }

    @Override
    public String checkBalance(String accountNumber) {
        Optional<BigDecimal> balance = accountService.checkBalance(accountNumber);
        if(balance.isPresent()) {
            logger.info("Balance for account {} is {}", AccountUtil.maskAccount(accountNumber),balance);
            return "Your balance is " + AccountUtil.truncateAmount(balance) + " GBP.";
        } else {
            logger.info("Account {} not found", AccountUtil.maskAccount(accountNumber));
            return "Account not found";
        }

    }

    @Override
    public HashMap<Integer, Integer> withdraw(String account, BigDecimal amount) {
        HashMap<Integer,Integer> disbursement = initializeDisbursement();
        //Check if account exists and if amount to disburse is multiple of 5
        if(!accountService.checkBalance(account).isPresent() ||
                accountService.checkBalance(account).get().compareTo(amount) < 0 ||
                !amount.divideAndRemainder(new BigDecimal(5))[1].equals( BigDecimal.ZERO)) {
            logger.info("No withdraw available for account {}",AccountUtil.maskAccount(account));
            return disbursement;
        }

        disbursement = processCashRequest(amount);

        //Remove the money from account
        if(disbursed(disbursement)) {
            accountService.withdrawAmount(account, amount);
            logger.info("New balance for account {} is {}", AccountUtil.maskAccount(account),
                    accountService.checkBalance(account));
        }

        return disbursement;


    }

    private boolean disbursed(HashMap<Integer, Integer> disbursement) {
        return disbursement.entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue() != 0);
    }

    private HashMap<Integer, Integer> processCashRequest(BigDecimal amount) {
        HashMap<Integer,Integer> disbursement = initializeDisbursement();

        do{
            if(amount.compareTo(new BigDecimal(50)) >= 0 &&
                    this.notes.get(50) > 0) {
                processCashRequestWithNote(50, disbursement);
                amount = amount.subtract(new BigDecimal(50));
            } else if(amount.compareTo(new BigDecimal(20)) >= 0 &&
                    this.notes.get(20) > 0) {
                processCashRequestWithNote(20, disbursement);
                amount = amount.subtract(new BigDecimal(20));

            } else if(amount.compareTo(new BigDecimal(10)) >= 0 &&
                    this.notes.get(10) > 0) {
                processCashRequestWithNote(10, disbursement);
                amount = amount.subtract(new BigDecimal(10));

            } else if(amount.compareTo(new BigDecimal(5)) >= 0 &&
                    this.notes.get(5) > 0) {
                processCashRequestWithNote(5, disbursement);
                amount = amount.subtract(new BigDecimal(5));

            } else if(amount.compareTo(BigDecimal.ZERO) > 0) {
                //This case is when no more notes and amount is still > 0
                disbursement = initializeDisbursement();
                break;
            }
        } while (amount.compareTo(BigDecimal.ZERO) > 0);


        return disbursement;

    }

    private void processCashRequestWithNote(Integer noteValue,
                                            HashMap<Integer, Integer> disbursement) {
        disbursement.put(noteValue, disbursement.get(noteValue)+1);
        this.notes.put(noteValue, this.notes.get(noteValue)-1);

    }

    private HashMap<Integer, Integer> initializeDisbursement() {
        HashMap<Integer, Integer> disbursement = new HashMap<>();
        disbursement.put(5,0);
        disbursement.put(10,0);
        disbursement.put(20,0);
        disbursement.put(50,0);

        return disbursement;

    }

}
