package com.example.database;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Backgroundworker  extends AsyncTask <String,Void,String>{
    Context context;
    AlertDialog alertDialog;
    Backgroundworker(Context cxt){
        context=cxt;
    }
    @Override
    protected String doInBackground(String... voids) {
        String type=voids[0];
        String login_url="http://192.168.16.103/login.php";
        if(type.equals("login")){
            try {
                String user_name=voids[1];
                String password=voids[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpsURLConnection)url.openConnection();
                httpURLConnection.getRequestMethod("POST");
                httpURLConnection.getDoOutput(true);
                httpURLConnection.getDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("user_name","UTF-8")+"="+ URLEncoder.encode(user_name,"UTF-8")+"&&"+
                URLEncoder.encode("password","UTF-8")+"=" + URLEncoder.encode(password,"UTF-8");
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        OutputStream.close();
        InputStream inputStream=httpURLConnection.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
        String result =" ";
        String line = " ";
        while ((line=bufferedReader.readLine())!=null){
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;

    } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("loginStatus");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
