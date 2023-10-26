package students.utilities;

public class isIntegerTest {
    public static boolean isInteger(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
