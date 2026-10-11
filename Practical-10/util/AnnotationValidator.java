package util;

import model.annotation.MaxLength;
import model.annotation.Positive;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationValidator
{
    public static String[] validate(Object obj)
    {
        ArrayList<String> errors = new ArrayList<>();

        if (obj == null)
        {
            errors.add("Object cannot be null");
            return errors.toArray(new String[0]);
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            field.setAccessible(true);

            try
            {
                if (field.isAnnotationPresent(Positive.class))
                {
                    Positive positive =
                            field.getAnnotation(Positive.class);

                    Object value = field.get(obj);

                    if (value instanceof Number)
                    {
                        long number =
                                ((Number) value).longValue();

                        if (number <= 0)
                        {
                            errors.add(positive.message());
                        }
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class))
                {
                    MaxLength maxLength =
                            field.getAnnotation(MaxLength.class);

                    Object value = field.get(obj);

                    if (value instanceof String)
                    {
                        String text = (String) value;

                        if (text.length() > maxLength.value())
                        {
                            errors.add(
                                    field.getName()
                                    + " length must be <= "
                                    + maxLength.value()
                            );
                        }
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                errors.add("Cannot access field: "
                        + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}
