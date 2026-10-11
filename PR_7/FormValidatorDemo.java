import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    @MaxLength(20)
    String username;

    @NotBlank
    @MaxLength(50)
    String email;

    @MaxLength(10)
    String phone;

    SignupForm(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }
}

class FormChecker {
    public static List<String> validate(Object object) {
        List<String> errors = new ArrayList<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);

                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null ||
                        value.toString().trim().isEmpty()) {
                        errors.add(field.getName() + " must not be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    int max = field.getAnnotation(MaxLength.class).value();

                    if (value != null &&
                        value.toString().length() > max) {
                        errors.add(field.getName() +
                                " must have maximum length " + max);
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Cannot access field: " + field.getName());
            }
        }

        return errors;
    }
}

public class FormValidatorDemo {
    public static void main(String[] args) {
        SignupForm form =
                new SignupForm(
                        "",
                        "verylongemailaddress@example.com",
                        "12345678901"
                );

        List<String> errors = FormChecker.validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}