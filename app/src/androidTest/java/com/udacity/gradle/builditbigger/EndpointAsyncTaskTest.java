package com.udacity.gradle.builditbigger;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by DELL on 29-11-2016.
 */

public class EndpointAsyncTaskTest {
    EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncTaskResponse() {
        @Override
        public void onResponse(boolean isSuccess, String result) {
            System.out.println("onResponse() called");
        }
    });

    @Test
    public void AsyncTaskTest() throws Exception {

        String result = asyncTask.execute().get();
        assertNotNull(result);
        assertTrue("The string is empty!", result.length() > 0);
    }

    @org.junit.Test
    public void testDoInBackground() throws Exception {
    }

    @org.junit.Test
    public void testOnPostExecute() throws Exception {
    }
}
