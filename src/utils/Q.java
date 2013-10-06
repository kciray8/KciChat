package utils;

/**
 * Quick & simple call some functions
 */
public class Q {
    private static long timer;

    /**
     * For debug! Not use in real progect!
     * @param obj
     */
    public static void out(Object obj){
        System.out.println(obj);
    }
    public static void fire(){
        System.out.println("fire!!!");
    }

    public static void measureBegin(){
        timer = System.currentTimeMillis();
    }
    public static void measureEnd(){
        long diff = System.currentTimeMillis() - timer;
        System.out.println("Time elapsed = " + diff);
    }
}
