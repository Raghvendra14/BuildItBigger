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

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public EndpointsAsyncTask endpointsAsyncTask;
    @BindView(R.id.progress_bar)
    ProgressBar spinner;
    @BindView(R.id.joke_button)
    Button mButton;
    @BindString(R.string.no_joke_available) String noJokeString;
    private Unbinder unbinder;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, root);

        spinner.setVisibility(View.GONE);

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
                    Toast.makeText(getActivity(), noJokeString, Toast.LENGTH_LONG).show();
                }
            }
        });
        endpointsAsyncTask.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
