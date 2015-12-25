package tw.andyang.rxjavawithretrolambdasample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tw.andyang.rxjavawithretrolambdasample.R;

public class Sample3Fragment extends BaseFragment{

    private EditText nameEditText;

    public static Sample3Fragment newInstance() {

        Bundle args = new Bundle();

        Sample3Fragment fragment = new Sample3Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample3, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        nameEditText = (EditText) view.findViewById(R.id.nameEditText);

        nameTextChangeObservable()
                .debounce(300, TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nameTextView::setText);

    }


    private Observable<String> nameTextChangeObservable() {
        return Observable.create(subscriber -> nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                subscriber.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
    }
}
