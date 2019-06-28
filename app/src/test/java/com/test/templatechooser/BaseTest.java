package com.test.templatechooser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class BaseTest {

    public <T> T createObjectFromFile(String filename, Class<T> tClass) throws IOException {
        return new Gson().fromJson(readFromFile(filename), tClass);
    }

    private String readFromFile(String filename) throws IOException {
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
        if (inputStream == null) {
            return null;
        }

        int i;
        final byte[] bytes = new byte[4096];
        final StringBuilder builder = new StringBuilder();

        while ((i = inputStream.read(bytes)) != -1) {
            builder.append(new String(bytes, 0, i));
        }

        return builder.toString();
    }

}
