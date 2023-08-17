package yating_asr.constants;

import java.util.Arrays;
import java.util.List;

public class TaskStatus {
    public static final String PENDING = "pending";
    public static final String ONGOING = "ongoing";
    public static final String COMPLETED = "completed";
    public static final String EXPIRED = "expired";
    public static final String ERROR = "error";

    static List<String> getList() {
        String[] valueList = new String[] { PENDING, ONGOING, COMPLETED, EXPIRED, ERROR };
        return Arrays.asList(valueList);
    }

    public static boolean validate(String value) {
        List<String> list = getList();
        return list.contains(value);
    }

    public static boolean isFinish(String value) {
        switch (value) {
            case COMPLETED:
            case EXPIRED:
            case ERROR:
                return true;
            default:
                return false;
        }
    }
}
