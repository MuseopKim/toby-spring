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

    public Integer calcSum(String filePath) throws IOException {
        BufferedReaderCallback sumCallback = new BufferedReaderCallback() {

            @Override
            public Integer doSomethingWithReader(BufferedReader bufferedReader) throws IOException {
                Integer sum = 0;
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }

                return sum;
            }
        };

        return fileReadTemplate(filePath, sumCallback);
    }
}
