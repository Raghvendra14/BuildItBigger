package com.udacity.gradle.builditbigger.free;

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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
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
    private InterstitialAd interstitialAd;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.progress_bar)
    ProgressBar spinner;
    @BindView(R.id.joke_button)
    Button mButton;
    @BindString(R.string.no_joke_available) String noJokeString;
    @BindString(R.string.interstitial_unit_id) String interstitialId;
    private Unbinder unbinder;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, root);
        spinner.setVisibility(View.GONE);

        interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId(interstitialId);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                beginFetchJoke();
            }
        });
        requestNewInterstitial();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    beginFetchJoke();
                }
            }
        });
        return root;
    }

    private void requestNewInterstitial() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
        mAdView.loadAd(adRequest);
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
