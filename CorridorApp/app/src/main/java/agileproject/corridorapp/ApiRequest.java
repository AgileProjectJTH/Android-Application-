package agileproject.corridorapp;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kim on 2016-04-28.
 */
public class ApiRequest {
    /**
     * @param callback
     * @param method "GET, POST, DELETE and so on."
     * @param path  "Path to the controller you want to reach."
     * @param message "If youre sending data put it here"
     *                Used like:
    ApiRequest.SendRequest(new ApiRequest.Callback() {
    @Override
    public void Response(String result) {
    //Here you put what shall happen after the data has been received.
    }
    }, methodstring, pathstring, messagestring);
     */
    static public void SendRequest(String method, String path, String message, String token, Callback callback){
        Sending s = new Sending(callback, method, path, message, "application/json", token);
        s.execute();
    }
    static public void LoginRequest(String message, Callback callback){
        Sending s = new Sending(callback,"POST", "token", message, "application/x-www-form-urlencoded", null);
        s.execute();
    }

    interface Callback{
        abstract void Response(String result, int code);
    }
    static class Sending extends AsyncTask<Void,Void,String> {
        Callback cb = null;
        String result = null;
        int code = 0;
        String method = null;
        String path = null;
        String message = null;
        String type = null;
        String token = null;
        public Sending(Callback CB, String m, String p, String msg, String t, String tkn)
        {
            cb = CB;
            method = m;
            path = p;
            message = msg;
            type = t;
            token = tkn;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://193.10.30.155/CorridorAPI/"+path);
                HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                urlcon.setRequestMethod(method);
                urlcon.setRequestProperty("Content-type", type);
                if(token != null)
                    urlcon.setRequestProperty("Authorization", token);
                if(message != null)
                {
                    urlcon.setDoOutput(true);
                    DataOutputStream os = new DataOutputStream(urlcon.getOutputStream());
                    os.writeBytes(message);
                    os.flush();
                    os.close();
                }
                Log.d("response-code: ", ""+urlcon.getResponseCode() +" "+ urlcon.getResponseMessage());
                code = urlcon.getResponseCode();
                BufferedReader r = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
                result = r.readLine();
                Log.d("Got: ", result);
                r.close();
                urlcon.disconnect();
            }catch(Exception e){Log.d("Error", e.getMessage());}
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            cb.Response(result,code);
            super.onPostExecute(s);
        }
    }
}
