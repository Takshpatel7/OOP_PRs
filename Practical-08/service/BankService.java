package service;

import exception.BankException;
import model.Account;
import util.StatementFormatter;

public class BankService
{
    public static boolean transfer(
            Account from,
            Account to,
            long amount) throws BankException
    {
        from.transfer(to, amount);
        return true;
    }

    public static String buildStatement(Account account)
    {
        return StatementFormatter.buildStatement(account);
    }
}
