package service;

import model.Account;
import util.StatementFormatter;

public class BankService
{
    public static boolean transfer(
            Account from,
            Account to,
            long amount)
    {
        if (from.withdraw(amount))
        {
            to.deposit(amount);
            return true;
        }

        return false;
    }

    public static String buildStatement(Account account)
    {
        return StatementFormatter.buildStatement(account);
    }
}
