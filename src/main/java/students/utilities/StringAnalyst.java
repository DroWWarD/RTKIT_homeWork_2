package students.utilities;

public class StringAnalyst {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
