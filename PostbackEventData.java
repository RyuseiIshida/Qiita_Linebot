package com.github.ryuseiishida.testbot;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.List;

public class PostbackEventData {
    private String target;
    private List<String> dataList = null;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public String toJson() {
        Moshi moshi = new Moshi.Builder()
                .build();
        JsonAdapter<PostbackEventData> jsonAdapter = moshi.adapter(PostbackEventData.class);

        return jsonAdapter.toJson(this);
    }

    public static PostbackEventData fromJson(String json) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .build();
        JsonAdapter<PostbackEventData> jsonAdapter = moshi.adapter(PostbackEventData.class);

        return jsonAdapter.fromJson(json);
    }
}