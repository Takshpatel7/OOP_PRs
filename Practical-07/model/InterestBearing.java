package model;

public interface InterestBearing
{
    default double yearlyInterest()
    {
        return balanceForInterest() * interestRate() / 100.0;
    }

    double interestRate();

    double balanceForInterest();
}
