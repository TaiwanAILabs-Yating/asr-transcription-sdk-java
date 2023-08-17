package yating_asr.asr;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Sentence {
    String sentence;
    Double confidence;
    Integer start;
    Integer end;
    String speakerId;
    Boolean sentiment;
    List<Word> wordList = new ArrayList<Word>();

    public Sentence(JSONObject jsonObject) {
        sentence = (String) jsonObject.get("sentence");
        confidence = (Double) jsonObject.get("confidence");
        start = ((Long) jsonObject.get("start")).intValue();
        end = ((Long) jsonObject.get("end")).intValue();
        speakerId = (String) jsonObject.get("speakerId");
        sentiment = (((Long) jsonObject.get("sentiment")).intValue() == 1);

        // add words
        JSONArray words = (JSONArray) jsonObject.get("words");
        Iterator<?> iterator = words.iterator();
        while (iterator.hasNext()) {
            wordList.add(new Word((JSONObject) iterator.next()));
        }
    }

    public String getSentence() {
        return sentence;
    }

    public String getSpeakerId() {
        return speakerId;
    }

    public Boolean getSentiment() {
        return sentiment;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public String getSentenceByWords() {
        String sentence = "";

        Iterator<Word> iterator = wordList.iterator();
        while (iterator.hasNext()) {
            sentence += iterator.next().getWord();
        }

        return sentence;
    }
}
