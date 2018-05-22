package com.novak.volodymyr.lab1;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CalculationResultsFileWorker {
    private final static String fileName = "history.bin";
    private final static Gson gson = new Gson();

    private static String readData(Context context, String fileName) throws FileNotFoundException {
        String jsonHistory = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                jsonHistory = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return jsonHistory;
    }

    private static void writeData(Context context, String fileName, String data) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            Log.e("login activity", "File not found: " + e.toString());
        } finally {
            if (fos != null) {
                fos = null;
            }
        }
    }

    public static void writeCalculationData(Context context, CalculationResult result) throws FileNotFoundException {
        String calculationData = readData(context, fileName);

        ArrayList<CalculationResult> prevCalculationResults = calculationData == "" ?
                new ArrayList<CalculationResult>()
                : getCalculationResultsFromJson(calculationData);

        prevCalculationResults.add(result);
        writeData(context, fileName, gson.toJson(prevCalculationResults));
    }

    public static ArrayList<CalculationResult> readCalculationData(Context context) throws FileNotFoundException {
        String calculationData = readData(context, fileName);
        if (calculationData == null)
            return null;
        ArrayList<CalculationResult> calculationResults = getCalculationResultsFromJson(calculationData);
        return calculationResults;
    }

    private static ArrayList<CalculationResult> getCalculationResultsFromJson(String calculationData) {
        return gson.fromJson(calculationData,
                new TypeToken<List<CalculationResult>>() {
                }.getType());
    }
}
