package com.example.tobyspring.calculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            int result = callback.doSomethingWithReader(bufferedReader);

            return result;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            throw exception;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    public Integer lineReadTemplate(String filePath, LineCallback lineCallback, int initValue) throws IOException {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            Integer result = initValue;
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result = lineCallback.doSomethingWithLine(line, result);
            }

            return result;
        } catch (IOException exception) {
            throw exception;
        } finally {

        }
    }

    public Integer calcSum(String filePath) throws IOException {
        LineCallback sumCallback = new LineCallback() {

            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.parseInt(line);
            }
        };

        return lineReadTemplate(filePath, sumCallback, 0);
    }

    public Integer calMultiply(String filePath) throws IOException {
        LineCallback multiplyCallback = new LineCallback() {

            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.parseInt(line);
            }
        };

        return lineReadTemplate(filePath, multiplyCallback, 1);
    }
}
