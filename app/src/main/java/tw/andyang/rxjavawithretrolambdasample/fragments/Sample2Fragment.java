package tw.andyang.rxjavawithretrolambdasample.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tw.andyang.rxjavawithretrolambdasample.R;

public class Sample2Fragment extends BaseFragment {

    private Subscription subscription;
    private TextView timeTextView;

    public static Sample2Fragment newInstance() {

        Bundle args = new Bundle();

        Sample2Fragment fragment = new Sample2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeTextView = (TextView) view.findViewById(R.id.timeTextView);

        Button startButton = (Button) view.findViewById(R.id.startButton);
        Button endButton = (Button) view.findViewById(R.id.endButton);

        startButton.setOnClickListener(v ->
                subscription = Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.computation())
                        .compose(bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .finallyDo(this::logFinallyDo)
                        .subscribe(this::onNext, this::showErrorLog, this::logComplete));

        endButton.setOnClickListener(v -> {
            subscription.unsubscribe();
            timeTextView.setText("time stop");
        });

    }

    private void onNext(long time) {
        timeTextView.setText(String.format("time : %d", time));
    }

    private void logFinallyDo() {
        Log.d("Sample2Fragment", "finallyDo");
    }

    private void logComplete() {
        Log.d("Sample2Fragment", "onComplete");
    }
}
