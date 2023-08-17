package yating_asr.constants;

import java.util.Arrays;
import java.util.List;

public class AsrModel {
    public static final String ZHEN = "asr-zh-en-std";
    public static final String ZHTW = "asr-zh-tw-std";
    public static final String EN = "asr-en-std";

    static List<String> getList() {
        String[] valueList = new String[] { ZHEN, ZHTW, EN };
        return Arrays.asList(valueList);
    }

    public static boolean validate(String value) {
        List<String> list = getList();
        return list.contains(value);
    }
}
