package com.softsquared.template.src.main.models;

import com.google.gson.annotations.SerializedName;

public class NoticeContentResponse {
    @SerializedName("isSuccess")
    boolean isSuccess;
    @SerializedName("code")
    int code;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    Result result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public class Result{
        @SerializedName("no")
        int no;
        @SerializedName("title")
        String title;
        @SerializedName("content")
        String content;
        @SerializedName("time")
        String time;
        @SerializedName("isUpdate")
        String isUpdate;

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(String isUpdate) {
            this.isUpdate = isUpdate;
        }
    }
}
