import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class MyTests {

    @Run
    public void testAddition() {
        System.out.println("testAddition executed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("testSubtraction executed");
    }

    public void normalMethod() {
        System.out.println("normalMethod executed");
    }

    @Run
    public void testMessage() {
        System.out.println("testMessage executed");
    }
}

public class MiniTestRunner {

    public static void main(String[] args) {
        MyTests tests = new MyTests();
        int count = 0;

        for (Method method : MyTests.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class)
                    && method.getParameterCount() == 0) {
                try {
                    method.invoke(tests);
                    count++;
                } catch (Exception e) {
                    System.out.println(
                            "Failed: " + method.getName());
                }
            }
        }

        System.out.println("Tests ran: " + count);
    }
}