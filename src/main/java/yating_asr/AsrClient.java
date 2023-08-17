package yating_asr;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import yating_asr.asr.Sentence;
import yating_asr.constants.AsrModel;
import yating_asr.constants.TaskStatus;

public class AsrClient {
    static String asrApiUrl;
    static String asrApiKey;

    public AsrClient(String url, String key) {
        asrApiUrl = url;
        asrApiKey = key;
    }

    public List<Sentence> Transcription(String audioUri, String asrModel, Boolean speakerDiarization,
            Integer speakerCount,
            Boolean sentiment) throws Exception {

        List<Sentence> sentenceList = new ArrayList<Sentence>();

        try {
            // parameter validation
            validation(asrModel);

            // mapping http request body
            String bodyString = bodyGenerator(audioUri, asrModel, speakerDiarization, speakerCount, sentiment);

            // send http post request
            JSONObject responseBody = sendHttpRequest(bodyString);

            // get transcription uid
            String transcriptionId = (String) responseBody.get("uid");
            String status = (String) responseBody.get("status");

            System.out.println("[" + transcriptionId + "] Send task success");

            // get http result
            while (!TaskStatus.isFinish(status)) {
                Thread.sleep(1000);
                JSONObject statusResult = getHttpResult(transcriptionId);
                status = (String) statusResult.get("status");
                System.out.println("[" + transcriptionId + "] Get task status success, status: " + status);
            }

            if (!status.equals(TaskStatus.COMPLETED)) {
                throw new Exception("Transcription not completed, status: " + status);
            }

            // save to file
            JSONObject transcriptionResult = getHttpResult(transcriptionId);
            JSONArray sentences = (JSONArray) transcriptionResult.get("sentences");

            Iterator<?> iterator = sentences.iterator();
            while (iterator.hasNext()) {
                sentenceList.add(new Sentence((JSONObject) iterator.next()));
            }

            System.out.println("[" + transcriptionId + "] Get transcription success");

            return sentenceList;
        } catch (Exception e) {
            throw e;
        }
    }

    private static void validation(String asrModel) throws Exception {
        if (!AsrModel.validate(asrModel)) {
            throw new Exception("asrModel: " + asrModel + " is not allowed.");
        }
    }

    private static String bodyGenerator(String audioUri, String asrModel, Boolean speakerDiarization,
            Integer speakerCount, Boolean sentiment) {

        JSONObject modelObject = new JSONObject();
        modelObject.put("model", asrModel);
        modelObject.put("customLm", "");

        JSONObject featureObject = new JSONObject();
        featureObject.put("punctuation", true);
        featureObject.put("speakerDiarization", speakerDiarization);
        featureObject.put("speakerCount", speakerCount);
        featureObject.put("sentiment", sentiment);

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("audioUri", audioUri);
        bodyObject.put("modelConfig", modelObject);
        bodyObject.put("featureConfig", featureObject);

        return bodyObject.toString();
    }

    private static JSONObject sendHttpRequest(String bodyString) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(asrApiUrl))
                .header("Content-Type", "application/json")
                .header("key", asrApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("http request fallure");
        }

        JSONParser parser = new JSONParser();
        JSONArray resultArray = (JSONArray) parser.parse(response.body());

        return (JSONObject) resultArray.get(0);
    }

    private static JSONObject getHttpResult(String transcriptionId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(asrApiUrl + "/" + transcriptionId))
                .header("Content-Type", "application/json")
                .header("key", asrApiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("http request fallure");
        }

        JSONParser parser = new JSONParser();

        return (JSONObject) parser.parse(response.body());
    }
}
