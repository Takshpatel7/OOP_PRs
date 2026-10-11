public class Chatfilter {

    public static String[] splitLine(String line) {

        String[] parts=line.split(" ", 3);

        if (parts.length<3) {
            return null;
        }

        return parts;
    }
}