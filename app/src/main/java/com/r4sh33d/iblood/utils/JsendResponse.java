package com.r4sh33d.iblood.utils;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

public class JsendResponse {

    public static final String ERROR_MESSAGE = "An unexpected network error occurred.";
    private JsonElement successBody;
    private JSONObject errorBody;

    public JsendResponse (JsonElement successBody, ResponseBody errorBody) {
        this.successBody = successBody;
        if (errorBody != null) {
            try {
                this.errorBody = new JSONObject(errorBody.string());
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isSuccess() {
        return successBody != null && !successBody.isJsonNull() && !successBody.getAsJsonObject().isJsonNull();
    }

    public JsonElement getData() {
        return successBody;
    }

    public JsonElement getDataAsObject() {
        return successBody.getAsJsonObject();
    }

    public JsonElement getDataAsArray() {
        return successBody.getAsJsonArray();
    }

    public String getErrorMessage() {
        if (errorBody == null) {
            return ERROR_MESSAGE;
        }
        try {
            return errorBody.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //TODO comeback and rewrite this in a clean manner
        try {
            return errorBody.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String getCode() {
        if (errorBody == null) {
            return "-1";
        }
        try {
            return errorBody.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "-1";
    }

}
