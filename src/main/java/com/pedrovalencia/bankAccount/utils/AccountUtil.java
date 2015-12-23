package com.pedrovalencia.bankAccount.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Created by pedrovalencia
 * on 23/12/2015.
 */
public class AccountUtil {

    public static String maskAccount(String account) {
        if(account.length() < 5) {
            return "";
        }
        return "****"+account.substring(account.length()-2,account.length());
    }

    public static BigDecimal truncateAmount(Optional<BigDecimal> amount) {
        if(amount.isPresent()) {
            return amount.get().setScale(2, RoundingMode.DOWN);
        }
        return null;
    }
}
