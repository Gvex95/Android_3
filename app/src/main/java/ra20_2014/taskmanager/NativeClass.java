package ra20_2014.taskmanager;

/**
 * Created by Milan on 6/1/2017.
 */

public class NativeClass {
        public native float calculatePercent(float finished_tasks,float number_of_tasks);

        static {
            System.loadLibrary("CalculatePercante");
        }
}
