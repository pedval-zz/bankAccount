package com.pedrovalencia.bankAccount.services;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public class ATMServiceTest {

    private ATMService atmService;

    @Before
    public void setUp() throws Exception {
        atmService = new ATMServiceImpl();
    }

    @Test
    public void checkBalanceExistingAccount() throws Exception {
        Assert.assertEquals(atmService.checkBalance("01001"),"Your balance is 2738.59 GBP.");
    }

    @Test
    public void checkBalanceNonExistingAccount() throws Exception {
        Assert.assertEquals(atmService.checkBalance("01004"),"Account not found");
    }

    @Test
    public void withdraw85PoundsFromExistingAccount() throws Exception {
        atmService.replenish(1,1,1,1);
        HashMap<Integer,Integer> withdraw = atmService.withdraw("01001", new BigDecimal(85));
        Assert.assertEquals(withdraw.get(5),new Integer(1));
        Assert.assertEquals(withdraw.get(10),new Integer(1));
        Assert.assertEquals(withdraw.get(20),new Integer(1));
        Assert.assertEquals(withdraw.get(50),new Integer(1));

        Assert.assertEquals(atmService.checkBalance("01001"), "Your balance is 2653.59 GBP.");

    }

    @Test
    public void withdraw90PoundsFromExistingAccount() throws Exception {
        atmService.replenish(2,1,1,1);
        HashMap<Integer,Integer> withdraw = atmService.withdraw("01001", new BigDecimal(90));
        Assert.assertEquals(withdraw.get(5),new Integer(2));
        Assert.assertEquals(withdraw.get(10),new Integer(1));
        Assert.assertEquals(withdraw.get(20),new Integer(1));
        Assert.assertEquals(withdraw.get(50),new Integer(1));

        Assert.assertEquals(atmService.checkBalance("01001"), "Your balance is 2648.59 GBP.");

    }

    @Test
    public void withdraw100PoundsFromExistingAccountNotEnoughNotes() throws Exception {
        atmService.replenish(2,1,1,1);
        HashMap<Integer,Integer> withdraw = atmService.withdraw("01001", new BigDecimal(100));
        Assert.assertEquals(withdraw.get(5),new Integer(0));
        Assert.assertEquals(withdraw.get(10),new Integer(0));
        Assert.assertEquals(withdraw.get(20),new Integer(0));
        Assert.assertEquals(withdraw.get(50),new Integer(0));

        Assert.assertEquals(atmService.checkBalance("01001"), "Your balance is 2738.59 GBP.");

    }

    @Test
    public void withdraw50PoundsFromNonExistingAccount() throws Exception {
        atmService.replenish(0,0,0,1);
        HashMap<Integer,Integer> withdraw = atmService.withdraw("01004", new BigDecimal(100));
        Assert.assertEquals(withdraw.get(5),new Integer(0));
        Assert.assertEquals(withdraw.get(10),new Integer(0));
        Assert.assertEquals(withdraw.get(20),new Integer(0));
        Assert.assertEquals(withdraw.get(50),new Integer(0));

    }

    @Test
    public void withdraw50PoundsFromExistingAccountNonEnoughFunds() throws Exception {
        atmService.replenish(0,0,0,1);
        HashMap<Integer,Integer> withdraw = atmService.withdraw("01002", new BigDecimal(50));
        Assert.assertEquals(withdraw.get(5),new Integer(0));
        Assert.assertEquals(withdraw.get(10),new Integer(0));
        Assert.assertEquals(withdraw.get(20),new Integer(0));
        Assert.assertEquals(withdraw.get(50),new Integer(0));

        Assert.assertEquals(atmService.checkBalance("01002"), "Your balance is 23.00 GBP.");

    }

    @Test
    public void withdraw20PoundsFromExistingAccountNonEnoughFundsInSecondtry() throws Exception {
        atmService.replenish(1,1,1,1);
        HashMap<Integer,Integer> withdraw = atmService.withdraw("01002", new BigDecimal(20));
        Assert.assertEquals(withdraw.get(5),new Integer(0));
        Assert.assertEquals(withdraw.get(10),new Integer(0));
        Assert.assertEquals(withdraw.get(20),new Integer(1));
        Assert.assertEquals(withdraw.get(50),new Integer(0));

        Assert.assertEquals(atmService.checkBalance("01002"), "Your balance is 3.00 GBP.");

        HashMap<Integer,Integer> withdraw2 = atmService.withdraw("01002", new BigDecimal(20));
        Assert.assertEquals(withdraw2.get(5),new Integer(0));
        Assert.assertEquals(withdraw2.get(10),new Integer(0));
        Assert.assertEquals(withdraw2.get(20),new Integer(0));
        Assert.assertEquals(withdraw2.get(50),new Integer(0));

    }
}
