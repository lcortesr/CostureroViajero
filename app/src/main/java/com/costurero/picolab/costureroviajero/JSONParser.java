package com.costurero.picolab.costureroviajero;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONParser {
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONArray jArr = null;
    StringBuilder sbParams;
    String paramsString;

    final String basicAuth = "Basic " + Base64.encodeToString("root:admin2016".getBytes(), Base64.NO_WRAP);

    public JSONArray makeHttpRequest(String url, String method, HashMap<String, String> params) {
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", basicAuth);
                //conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                //conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
                //conn.setRequestProperty("uploaded_file", fileName);
                conn.setConnectTimeout(15000);
                conn.connect();
                paramsString = sbParams.toString();
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET")){
            // request method is GET
            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", basicAuth);
                conn.setConnectTimeout(15000);
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("JSON Parser", "resultado: " + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();
        // try parse the string to a JSON object
        try {
            jArr = new JSONArray(result.toString());
        } catch (JSONException e) {
            try {
                JSONObject jObj=new JSONObject((result.toString()));
                jArr = new JSONArray();
                jArr.put(jObj);
            } catch (JSONException e2) {
                Log.e("JSON Parser", "Error parsing data " + e2.toString());
            }
        }
        // return JSON Object
        return jArr;
    }
    public JSONArray makeHttpRequest(String url, String method, JSONObject params) {
        JSONArray arreglo=new JSONArray();
        arreglo.put(params);
        int i = 0;
        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Authorization", basicAuth);
                //conn.setRequestProperty("Content-Type", "application/json");

                String twoHyphens = "--";
                String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
                String lineEnd = "\r\n";

                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("Accept", "*/*");
                conn.setInstanceFollowRedirects(false);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();
                String p="path";
                String filepath=params.getString(p);
                if(filepath!=null) {
                    String[] q = filepath.split("/");
                    int idx = q.length - 1;
                    int bytesRead, bytesAvailable, bufferSize;
                    byte[] buffer;
                    int maxBufferSize = 1 * 1024 * 1024;

                    wr = new DataOutputStream(conn.getOutputStream ());
                    wr.writeBytes(twoHyphens + boundary + lineEnd);

                    String filefield="path";
                    String fileMimeType="path/jpg";

                    File file = new File(filepath);
                    FileInputStream fileInputStream = new FileInputStream(file);

                    wr.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
                    wr.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        wr.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    wr.writeBytes(lineEnd);

                    // Upload POST Data
                    int keysCount=params.length();
                    for(int k=0;k<keysCount;k++) {
                        String key = params.keys().next();
                        String value = params.getString(key);
                        if(!key.equals("path")) {
                            wr.writeBytes(twoHyphens + boundary + lineEnd);
                            wr.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                            wr.writeBytes("Content-Type: text/plain" + lineEnd);
                            wr.writeBytes(lineEnd);
                            wr.writeBytes(value);
                            wr.writeBytes(lineEnd);
                        }
                        params.remove(key);
                    }

                    wr.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    wr.flush();
                    wr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET")){
            // request method is GET
            if (params.toString().length() != 0) {
                url += "?" + params.toString();
            }
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", basicAuth);
                conn.setConnectTimeout(15000);
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            //Receive the response from the server
            InputStream in, error;
            int status;
            //in = new BufferedInputStream(conn.getInputStream());
            error = new BufferedInputStream(conn.getErrorStream());
            //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(error));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("JSON Parser", "resultado: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();
        // try parse the string to a JSON object
        try {
            jArr = new JSONArray(result.toString());
        } catch (JSONException e) {
            try {
                JSONObject jObj=new JSONObject((result.toString()));
                jArr = new JSONArray();
                jArr.put(jObj);
            } catch (JSONException e2) {
                Log.e("JSON Parser", "Error parsing data " + e2.toString());
            }
        }
        // return JSON Array
        return jArr;
    }
}