package yating_asr;

import java.util.List;

public class App {
    public static void main(String[] args) {
        String asrApiUrl = "ASR_ENDPOINT";
        String asrApiKey = "PUT_YOUR_API_KEY_HERE";

        String audioUri = "http://www.example.com/audio.mp3";
        String asrModel = AsrModel.ZHEN;
        Boolean speakerDiarization = true;
        Integer speakerCount = 0;
        Boolean sentiment = true;

        AsrClient client = new AsrClient(asrApiUrl, asrApiKey);

        try {
            List<Sentence> sentenceList = client.Transcription(audioUri, asrModel, speakerDiarization, speakerCount,
                    sentiment);

            sentenceList.forEach((s) -> System.out.println(s.getSentence()));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
