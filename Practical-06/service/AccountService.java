package service;

import model.Account;
import model.WithdrawRule;

public class AccountService {

    public static boolean withdraw(Account account, long amount, WithdrawRule rule) {
        if (rule.allow(account, amount)) {
            return account.withdraw(amount);
        }
        return false;
    }

    public static void printAccount(Account account) {
        System.out.println(account);
        System.out.println("Interest rate: " + account.interestRate() + "%");
        System.out.println("Yearly interest: " + account.yearlyInterest());
        System.out.println();
    }
}
