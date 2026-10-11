package model;

public interface InterestBearing {
    double interestRate();

    default double yearlyInterest() {
        return balanceForInterest() * interestRate() / 100.0;
    }

    // Implementing classes provide the current balance used by the default method.
    long balanceForInterest();
}
