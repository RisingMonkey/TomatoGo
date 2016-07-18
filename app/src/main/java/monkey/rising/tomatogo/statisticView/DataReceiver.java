package monkey.rising.tomatogo.statisticView;


/**
 * Created by Administrator on 2016/7/18.
 */
public class DataReceiver {

    public static int[] dayStatistics(int day) {
        int result[] = {0, 3, 5, 1,4,3,8,0, 3, 5, 1,4,3,8,0, 3, 5, 1,4,3,8,0, 3, 5, 1,4,3,8,};
        return result;
    }

    public static int[] monthStatistics(int month){
        int result[] = {5,9,11,2,3,8,5,9,11,2,3,8,5,9,11,2,3,8,5,9,11,2,3,8,5,9,11,2,3,8,5,9,11,2,3,8,};
        return result;
    }
}
