package com.retrofit.model;

public class HttpResult<T> {
    public boolean success;
    public String msg;
    public T data;
}