public class PasswordChecker {

    public static int checkRules(String pw) {
        int count=0;

        if(pw.length()>=8) {
            System.out.println("Length >= 8       : Passed");
            count++;
        } else
            System.out.println("Length >= 8       : Failed");

        if(pw.matches(".*[A-Z].*")) {
            System.out.println("Uppercase         : Passed");
            count++;
        } else
            System.out.println("Uppercase         : Failed");

        if(pw.matches(".*[0-9].*")) {
            System.out.println("Digit             : Passed");
            count++;
        } else
            System.out.println("Digit             : Failed");

        if(pw.matches(".*[^a-zA-Z0-9].*")) {
            System.out.println("Special character : Passed");
            count++;
        } else
            System.out.println("Special character : Failed");

        return count;
    }

    public static String strength(String pw) {
        int count=checkRules(pw);

        if(count<=1)
            return "Weak";
        else if(count<=3)
            return "Medium";
        else
            return "Strong";
    }
}