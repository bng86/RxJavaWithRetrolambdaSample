package tw.andyang.rxjavawithretrolambdasample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rx.Observable;
import tw.andyang.rxjavawithretrolambdasample.R;

public class Sample4Fragment extends BaseFragment {

    private Button completeButton;
    private EditText nameEditText, phoneEditText;

    public static Sample4Fragment newInstance() {

        Bundle args = new Bundle();

        Sample4Fragment fragment = new Sample4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample4, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        phoneEditText = (EditText) view.findViewById(R.id.phoneEditText);
        completeButton = (Button) view.findViewById(R.id.completeButton);

        Observable.combineLatest(textChangeObservable(nameEditText), textChangeObservable(phoneEditText),
                viewClickObservable(completeButton), this::verify)
                .compose(bindToLifecycle())
                .subscribe(this::setComplete);
    }

    private void setComplete(boolean isValid) {
        if (isValid) {
            completeButton.setText(getString(R.string.next));
        } else {
            completeButton.setText(getString(R.string.invalid));
        }
    }

    private boolean verify(String name, String phone, View button) {
        boolean nameValid = !isEmptyOrNull(name) && name.length() >= 3;

        if (!nameValid) nameEditText.setError("Name Invalid");

        boolean phoneValid = !isEmptyOrNull(phone) && phone.length() == 10;

        if (!phoneValid) phoneEditText.setError("Phone Invalid");

        boolean isButtonClick = button != null;

        return nameValid && phoneValid && isButtonClick;
    }

    private Observable<String> textChangeObservable(EditText editText) {
        return Observable.create(subscriber -> editText.addTextChangedListener(new TextWatcher() {
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

    private Observable<View> viewClickObservable(View view) {
        return Observable.create(subscriber -> view.setOnClickListener(subscriber::onNext));
    }

}
