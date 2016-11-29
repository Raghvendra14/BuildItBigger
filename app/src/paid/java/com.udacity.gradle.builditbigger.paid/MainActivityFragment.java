package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.jokedisplaylib.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public EndpointsAsyncTask endpointsAsyncTask;
    private ProgressBar spinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        spinner = (ProgressBar) root.findViewById(R.id.progress_bar);
        spinner.setVisibility(View.GONE);
        Button mButton = (Button) root.findViewById(R.id.joke_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginFetchJoke();
            }
        });
        return root;
    }

    private void beginFetchJoke() {
        spinner.setVisibility(View.VISIBLE);
        endpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncTaskResponse() {
            @Override
            public void onResponse(boolean isSuccess, String result) {
                spinner.setVisibility(View.GONE);
                if (isSuccess) {
                    Intent intent =  new Intent(getActivity(), JokeDisplayActivity.class);
                    intent.putExtra(JokeDisplayActivity.JOKE_KEY, result);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.no_joke_available, Toast.LENGTH_LONG).show();
                }
            }
        });
        endpointsAsyncTask.execute();
    }
}
