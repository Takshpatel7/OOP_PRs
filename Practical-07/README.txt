MINIBANK - PRACTICAL 1 TO 7 INTEGRATED PROJECT
Object Oriented Programming (CEUE203)

This project combines the MiniBank work from Practical 1 through Practical 7.

PRACTICAL 1
- MiniBank main class
- BankInfo record
- MenuOption enum
- Scanner input
- switch expression
- menu loop and EXIT

PRACTICAL 2
- Customer class
- Account class
- private fields / encapsulation
- constructors and constructor chaining
- static ID/account generation
- deposit and withdraw
- Account[] objects

PRACTICAL 3
- toString()
- equals()
- hashCode()
- Customer.Address static nested class
- clone()
- instanceof

PRACTICAL 4
- Validator class
- Pattern / regular expressions
- mobile, email, PAN and IFSC validation
- TransactionType enum
- Command record
- CommandParser
- StringBuilder statement formatter

PRACTICAL 5
- abstract Account
- SavingsAccount
- CurrentAccount
- FixedDepositAccount
- interestRate()
- canWithdraw()
- inheritance, super and runtime polymorphism
- Account[] with mixed subclasses

PRACTICAL 6
- Transactable interface
- InterestBearing interface with default method
- WithdrawRule functional interface
- anonymous class
- lambda expression
- Premium marker interface
- model/util/service packages
- runnable JAR and manifest

PRACTICAL 7
- @Override
- @FunctionalInterface
- custom @Id
- custom @Positive
- custom @MaxLength
- @Target and @Retention
- AnnotationValidator
- reflection:
  getDeclaredFields()
  isAnnotationPresent()
  getAnnotation()
  setAccessible(true)
  field.get(obj)

IMPORTANT:
The project intentionally keeps the code simple while integrating the concepts
up to Practical 7 into one MiniBank project.

JDK:
JDK 17 or newer

COMPILE:
javac MiniBank.java model/*.java model/annotation/*.java util/*.java service/*.java

RUN:
java MiniBank

BUILD RUNNABLE JAR:
jar cfm minibank.jar META-INF/MANIFEST.MF MiniBank.class model/*.class model/annotation/*.class util/*.class service/*.class

RUN JAR:
java -jar minibank.jar

EXPECTED IMPORTANT OUTPUT:
- Savings interest rate: 4.0
- Current interest rate: 0.0
- Fixed deposit interest rate: 7.0
- Valid mobile: true
- Invalid mobile: false
- Command type: DEPOSIT
- Reflection validation for balance -100:
  must be > 0

The menu at the end comes from Practical 1 and can be exited with option 5.
