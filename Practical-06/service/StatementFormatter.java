package service;

import model.Account;

public final class StatementFormatter {

    private StatementFormatter() {
    }

    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();

        sb.append("----- MiniBank Statement -----\n");
        sb.append("Account Number: ")
                .append(account.getAccountNumber()).append('\n');
        sb.append("Owner: ")
                .append(account.getOwnerName()).append('\n');
        sb.append("Balance: ")
                .append(account.getBalance()).append('\n');
        sb.append("Interest Rate: ")
                .append(account.interestRate()).append("%\n");
        sb.append("Active: ")
                .append(account.isActive()).append('\n');

        return sb.toString();
    }
}
