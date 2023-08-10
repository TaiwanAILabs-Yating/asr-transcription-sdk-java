# Yating ASR Transcription SDK - Java version

## Task Flow

1. 發送 transcription task。

1. 取得 transcription 狀態，直到取得最終狀態。

1. 若最終狀態為 `completed`，取回最後辨識結果。

## Available Key

- Please contact Yating ASR

### Input

```JSON
{
    "audioUri": "http://www.example.com/audio.mp3",
    "modelConfig": {
        "model": "asr-zh-en-std",
        "customLm": ""
    },
    "featureConfig": {
        "punctuation": true,
        "speakerDiarization": true,
        "speakerCount": 0,
        "sentiment": true
    }
}
```

### Output

```JSON
{
    "id": 1,
    "uid": "15b36085-fa6d-4691-aced-ddbc34b9927e",
    "audioUri": "http://www.example.com/audio.mp3",
    "model": "asr-zh-en-std",
    "customLm": "",
    "isPunctuation": 1,
    "isSpeakerDiarization": 1,
    "speakerCount": 1,
    "isSentiment": 1,
    "status": "completed",
    "createdAt": "2023-08-09T10:39:23.807Z",
    "updatedAt": "2023-08-09T10:42:34.000Z",
    "sentences": [
        {
            "sentence": "我是雅婷語音。",
            "speakerId": "0",
            "start": 99,
            "end": 1298,
            "confidence": 1,
            "words": [
                {
                    "word": "我是",
                    "start": 99,
                    "end": 379,
                    "punctuator": ""
                },
                {
                    "word": "雅婷",
                    "start": 379,
                    "end": 499,
                    "punctuator": ""
                },
                {
                    "word": "語音",
                    "start": 499,
                    "end": 1298,
                    "punctuator": "。"
                }
            ],
            "sentiment": 0
        }
    ],
    "progress": {
        "value": 100
    }
}
```

## Available Variable

### Audio Uri

可使用的音檔網址。

### Model Config

| Name     | Data Type | Description                       |
| -------- | --------- | --------------------------------- |
| model    | String    | 語言模型，請參考下方 model 設定。 |
| customLm | String    | 客製化模型，若無則預設空白。      |

### Model

| Name          | Description                        |
| ------------- | ---------------------------------- |
| asr-zh-en-std | 中文為主，並可處理英文夾雜的情況。 |
| asr-zh-tw-std | 中文為主，並可處理台語夾雜的情況。 |
| asr-en-std    | 全英文情境使用。                   |

### Feature Config

| Name               | Data Type | Description                                                                              |
| ------------------ | --------- | ---------------------------------------------------------------------------------------- |
| punctuation        | Boolean   | 標點符號，預設為 true。                                                                  |
| speakerDiarization | Boolean   | 語者辨識，預設為 true。                                                                  |
| speakerCount       | Integer   | 語者數量，開啟語者辨識後，提供語者數量可讓語者辨識結果更為準確，未提供則讓模型自行辨識。 |
| sentiment          | Boolean   | 情緒辨識，預設為 true。                                                                  |
