package util;

import model.Command;
import model.TransactionType;

public class CommandParser
{
    public static Command parse(String line)
    {
        if (line == null)
        {
            return null;
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3)
        {
            return null;
        }

        try
        {
            TransactionType type =
                    TransactionType.valueOf(parts[0].toUpperCase());

            long amount = Long.parseLong(parts[2]);

            if (amount <= 0)
            {
                return null;
            }

            return new Command(type, parts[1], amount);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }
}
