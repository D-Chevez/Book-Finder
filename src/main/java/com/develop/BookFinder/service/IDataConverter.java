package com.develop.BookFinder.service;

public interface IDataConverter {
    <T> T getData (String json, Class<T> clase);
}
