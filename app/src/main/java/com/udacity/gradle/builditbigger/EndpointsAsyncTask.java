package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.dell.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by DELL on 26-11-2016.
 */

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    public AsyncTaskResponse asyncTaskResponse = null;
    public boolean isSuccess;

    public EndpointsAsyncTask(AsyncTaskResponse delegate) {
        this.asyncTaskResponse = delegate;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://master-aegis-151021.appspot.com/_ah/api/");
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    // options for running against local devappserver
//                    // - 10.0.2.2 is localhost's IP address in Android emulator
//                    // - turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
            // end options for devappserver

            myApiService = builder.build();
        }
        
        try {
            isSuccess = true;
            return myApiService.showJoke().execute().getJoke();
        } catch (IOException e) {
            isSuccess = false;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        asyncTaskResponse.onResponse(isSuccess, result);
    }

    public interface AsyncTaskResponse {
        void onResponse(boolean isSuccess, String result);
    }
}