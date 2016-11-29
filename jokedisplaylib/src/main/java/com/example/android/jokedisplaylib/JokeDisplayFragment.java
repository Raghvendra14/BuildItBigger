package com.example.android.jokedisplaylib;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JokeDisplayFragment extends Fragment {

    public JokeDisplayFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_joke_display, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(JokeDisplayActivity.JOKE_KEY)) {
            String joke = intent.getStringExtra(JokeDisplayActivity.JOKE_KEY);
            TextView jokeTextView = (TextView) rootView.findViewById(R.id.joke_textview);
            if (joke != null && joke.length() != 0) {
                jokeTextView.setText(joke);
            }
        }
        return rootView;
    }

}
