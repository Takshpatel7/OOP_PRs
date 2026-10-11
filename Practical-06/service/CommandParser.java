package service;

import model.Command;
import model.TransactionType;

public final class CommandParser {

    private CommandParser() {
    }

    public static Command parse(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Command must contain: TYPE ACCOUNT_NUMBER AMOUNT");
        }

        TransactionType type =
                TransactionType.valueOf(parts[0].toUpperCase());

        long amount;
        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount");
        }

        return new Command(type, parts[1], amount);
    }
}
