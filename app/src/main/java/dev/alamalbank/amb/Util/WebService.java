package dev.alamalbank.amb.Util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebService {
    public static final int HTTP_TIMEOUT = 60 * 1000; // milliseconds
    private static HttpClient mHttpClient;

    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    public static String executeHttp(Context context,String template ,String  handler,ArrayList<NameValuePair> postParameters) throws Exception {
        BufferedReader inputStreamReader = null;
        String result = null;
        String scheme = Utils.getColumnValue(context, "select ws_scheme from home");
        String host = Utils.getColumnValue(context, "select ws_host from home");
        String port = Utils.getColumnValue(context, "select ws_port from home");
        String path = Utils.getColumnValue(context, "select ws_path from home") + template;
        String url =null;
        URI uri = null;

        try {
            HttpResponse response = null;
            if (handler.equalsIgnoreCase("GET")) {
                uri = URIUtils.createURI(scheme, host, Integer.parseInt(port), path,
                        URLEncodedUtils.format(postParameters, "UTF-8"), null); //windows-1256
                Log.e(Utils.APP_TAG,"URI: "+uri.toString());
                 response = getHttpClient().execute(new HttpGet(uri));
            }else if (handler.equalsIgnoreCase("POST")){
                url = scheme + "://" + host + ":" + port + path + "/";
                HttpPost request = new HttpPost(url);
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, "UTF-8");//"windows-1256");
                request.setEntity(formEntity);
                Log.e(Utils.APP_TAG,"UTL : "+url);
                response = getHttpClient().execute(request);
            }

            inputStreamReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 318088);
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = inputStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            result = stringBuilder.toString();
            Log.e(Utils.APP_TAG, "Result : "+result);
            if (result != null) {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray items = jsonObj.getJSONArray("items");
                if ((template.equalsIgnoreCase("run")) && (items.length() == 1)) {
                    JSONObject c = items.getJSONObject(0);
                    result = c.getString("result");
                }

            }
        } catch (final JSONException e) {
            Log.e(Utils.APP_TAG, "Json parsing error: " + e.getMessage());
            throw new Exception("1-" + "Json parsing error: " + e.getMessage());
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            throw new Exception("2-" + "Web: " + e.getMessage());
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    Log.e(Utils.APP_TAG, e.toString());
                    throw new IOException("3-" + "Web: " + e.getMessage());//result ="0" ;// e.getMessage().toString();
                }
            }
        }
        return result;
    }
    /*
    public static String executeHttpPost(Context context, ArrayList<NameValuePair> postParameters, String template) throws Exception {
    public static String executeHttp    (Context context,String template ,String  handler,ArrayList<NameValuePair> postParameters)
        BufferedReader inputStreamReader = null;
        String result = null;
        String scheme = Utils.getColumnValue(context, "select ws_scheme from home");
        String host = Utils.getColumnValue(context, "select ws_host from home");
        String port = Utils.getColumnValue(context, "select ws_port from home");
        String path = Utils.getColumnValue(context, "select ws_path from home") + template;
        String url = scheme + "://" + host + ":" + port + path + "/";//"http://82.114.179.249:8080/ords/pyesws/pyes/run/";

        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, "UTF-8");//"windows-1256");
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);

            inputStreamReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 318088);
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = inputStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            result = stringBuilder.toString();
            if (result != null) {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray items = jsonObj.getJSONArray("items");
                if ((template.equalsIgnoreCase("run")) && (items.length() == 1)) {
                    JSONObject c = items.getJSONObject(0);
                    result = c.getString("result");
                }

            }
        } catch (final JSONException e) {
            Log.e(Utils.APP_TAG, "Json parsing error: " + e.getMessage());
            throw new Exception("1-" + "Json parsing error: " + e.getMessage());
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            throw new Exception("2-" + "Web: " + e.getMessage());
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    Log.e(Utils.APP_TAG, e.toString());
                    throw new IOException("3-" + "Web: " + e.getMessage());//result ="0" ;// e.getMessage().toString();
                }
            }
        }
        return result;
    }*/
    /*public static String executeHttpPostold(Context context, ArrayList<NameValuePair> postParameters, String template) throws Exception {

        BufferedReader inputStreamReader = null;
        String result = null;
        String scheme = Utils.getColumnValue(context, "select ws_scheme from home");
        String host = Utils.getColumnValue(context, "select ws_host from home");
        String port = Utils.getColumnValue(context, "select ws_port from home");
        String path = Utils.getColumnValue(context, "select ws_path from home") + template;
        try {
            HttpClient client = getHttpClient();

            URI uri = URIUtils.createURI(scheme, host, Integer.parseInt(port), path,
                    URLEncodedUtils.format(postParameters, "UTF-8"), null);
            HttpResponse response = getHttpClient().execute(new HttpPost(uri));
            //windows-1256
            inputStreamReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 318088);
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = inputStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            result = stringBuilder.toString();
            if (result != null) {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray items = jsonObj.getJSONArray("items");
                if ((template.equalsIgnoreCase("run")) && (items.length() == 1)) {
                    JSONObject c = items.getJSONObject(0);
                    result = c.getString("result");
                }

            }
        } catch (final JSONException e) {

            Log.e(Utils.APP_TAG, "Json parsing error: " + e.getMessage());
            throw new Exception("1-" + "Json parsing error: " + e.getMessage());
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            throw new Exception("2-" + "Web: " + e.getMessage());
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    Log.e(Utils.APP_TAG, e.toString());
                    throw new IOException("3-" + "Web: " + e.getMessage());//result ="0" ;// e.getMessage().toString();
                }
            }
        }
        return result;
    }*/
    /*  public static String executeHttpPostold2(String url, ArrayList<NameValuePair> postParameters) throws Exception {
        BufferedReader inputStreamReader = null;
        String result = null;

        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, "UTF-8");//"windows-1256");
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            inputStreamReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 318088);
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = inputStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            inputStreamReader.close();
            result = stringBuilder.toString();

        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            throw new Exception("1-" + "Web " + e.getMessage());
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    Log.e(Utils.APP_TAG, e.toString());
                    throw new IOException("3-" + "Web " + e.getMessage());//result ="0" ;// e.getMessage().toString();
                }
            }
        }
        return result;
    }*/
}