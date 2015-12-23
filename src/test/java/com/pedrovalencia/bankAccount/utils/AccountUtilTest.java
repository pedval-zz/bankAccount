package com.pedrovalencia.bankAccount.utils;

import junit.framework.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public class AccountUtilTest {


    @Test
    public void testAccountMasking() throws Exception {
        Assert.assertEquals(AccountUtil.maskAccount("01001"), "****01");
    }

    @Test
    public void testAccountMaskingShortLength() throws Exception {
        Assert.assertEquals(AccountUtil.maskAccount("010"), "");
    }

    @Test
    public void testAmountTruncate() {
        Optional<BigDecimal> amount = Optional.of(new BigDecimal(123.456789));
        Assert.assertEquals(AccountUtil.truncateAmount(amount).compareTo(new BigDecimal(123.45).setScale(2, RoundingMode.DOWN)),0);
    }
}
