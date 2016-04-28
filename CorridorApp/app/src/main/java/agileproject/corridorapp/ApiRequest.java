package agileproject.corridorapp;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    static public void SendRequest(Callback callback, String method, String path, String message){
        Sending jokes = new Sending(callback, method, path, message);
        jokes.execute();
    }

    interface Callback{
        abstract void Response(String result);
    }
    static class Sending extends AsyncTask<Void,Void,String> {
        Callback cb = null;
        String result = null;
        String method = null;
        String path = null;
        String message = null;
        public Sending(Callback CB, String m, String p, String msg)
        {
            cb = CB;
            method = m;
            path = p;
            message = msg;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(path);
                HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                urlcon.setRequestMethod(method);
                if(message != null)
                {
                    OutputStream os = urlcon.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.flush();
                    writer.close();
                    os.close();
                }
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
            cb.Response(result);
            super.onPostExecute(s);
        }
    }
}
