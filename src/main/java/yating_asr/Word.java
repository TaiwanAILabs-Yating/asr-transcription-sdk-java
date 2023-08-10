package yating_asr;

import org.json.simple.JSONObject;

public class Word {
    String word;
    String punctuator;
    Integer start;
    Integer end;

    public Word(JSONObject jsonObject) {
        word = (String) jsonObject.get("word");
        punctuator = (String) jsonObject.get("punctuator");
        start = ((Long) jsonObject.get("start")).intValue();
        end = ((Long) jsonObject.get("end")).intValue();
    }

    public String getWord() {
        return word;
    }
}
