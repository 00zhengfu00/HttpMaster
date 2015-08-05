package jsontojava.util;


/**
 * @author Jack Tony
 * @date 2015/8/5
 */
public class CheckArg {

    public static void isNotEmpty(String argument, String name) {
        isNotZeroLength(argument, name);
        if(argument != null && argument.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void isNotZeroLength(String argument, String name) {
        isNotNull(argument, name);
        if(argument.length() <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void isNotNull(Object argument, String name) {
        if(argument == null) {
            throw new IllegalArgumentException();
        }
    }
}
