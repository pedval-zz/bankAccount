package com.pedrovalencia.bankAccount.services;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public class AccountServiceTest {

    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountServiceImpl();
    }

    @Test
    public void checkBalanceExistingAccount() throws Exception {
        Optional<BigDecimal> balance = accountService.checkBalance("01001");

        Assert.assertTrue("Account<01001> does not exist", balance.isPresent());
        Assert.assertEquals("Balance <"+balance.get()+"> does not match", balance.get(), new BigDecimal(2738.59));
    }

    @Test
    public void checkBalanceNotExistingAccount() throws Exception {
        Optional<BigDecimal> balance = accountService.checkBalance("01004");

        Assert.assertFalse("Account<01004> exists", balance.isPresent());
    }

    @Test
    public void withdrawWithEnoughMoney() throws Exception {
        Assert.assertTrue(accountService.withdrawAmount("01001",new BigDecimal(50)));
    }

    @Test
    public void withdrawWithNoEnoughMoney() throws Exception {
        Assert.assertFalse(accountService.withdrawAmount("01003",new BigDecimal(5)));
    }

    @Test
    public void withdrawWithNonExistingAccount() throws Exception {
        Assert.assertFalse(accountService.withdrawAmount("01004",new BigDecimal(5)));
    }

}
