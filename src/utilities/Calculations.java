package utilities;

/**
 * Created by Rtgher on 17/03/2016.
 */
public class Calculations {
    public static boolean between(double i, double min, double max){
        if(i>=min)
            if(i<=max)return true;
        return false;
    }
    public static double cointoss(double heads, double tails){
        // this method immitates the flipping of a coin.
        //returns either the heads or tails values randomly.
        if(Math.random()<0.5)return heads;
        else return tails;
    }
}
