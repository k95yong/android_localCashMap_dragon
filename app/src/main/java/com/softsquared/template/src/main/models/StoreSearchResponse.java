package com.softsquared.template.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreSearchResponse {
    @SerializedName("isSuccess")
    public boolean isSuccess;
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public ArrayList<SearchResult> storeSearchResult;
    @SerializedName("result2")
    public ArrayList<SearchResult> storeSearchResult2;

    public ArrayList<SearchResult> getStoreSearchResult2() {
        return storeSearchResult2;
    }

    public void setStoreSearchResult2(ArrayList<SearchResult> storeSearhResult2) {
        this.storeSearchResult2 = storeSearchResult2;
    }

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

    public ArrayList<SearchResult> getStoreSearchResult() {
        return storeSearchResult;
    }

    public void setStoreSearchResult(ArrayList<SearchResult> storeSearchResult) {
        this.storeSearchResult = storeSearchResult;
    }
//
//    public class result implements Serializable {
//        @SerializedName("no")
//        int no;
//        @SerializedName("mutual_name")
//        String mutual_name;
//        @SerializedName("address")
//        String address;
//        @SerializedName("phone_number")
//        String phone_number;
//        @SerializedName("postal_code")
//        int postal_code;
//        @SerializedName("latitude")
//        double latitude;
//        @SerializedName("longitude")
//        double longitude;
//        @SerializedName("distance")
//        double distance;
//
//        public double getDistance() {
//            return distance;
//        }
//
//        public void setDistance(double distance) {
//            this.distance = distance;
//        }
//
//        public int getNo() {
//            return no;
//        }
//
//        public void setNo(int no) {
//            this.no = no;
//        }
//
//        public String getMutual_name() {
//            return mutual_name;
//        }
//
//        public void setMutual_name(String mutual_name) {
//            this.mutual_name = mutual_name;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getPhone_number() {
//            return phone_number;
//        }
//
//        public void setPhone_number(String phone_number) {
//            this.phone_number = phone_number;
//        }
//
//        public int getPostal_code() {
//            return postal_code;
//        }
//
//        public void setPostal_code(int postal_code) {
//            this.postal_code = postal_code;
//        }
//
//        public double getLatitude() {
//            return latitude;
//        }
//
//        public void setLatitude(double latitude) {
//            this.latitude = latitude;
//        }
//
//        public double getLongitude() {
//            return longitude;
//        }
//
//        public void setLongitude(double longitude) {
//            this.longitude = longitude;
//        }
//
//    }
}
