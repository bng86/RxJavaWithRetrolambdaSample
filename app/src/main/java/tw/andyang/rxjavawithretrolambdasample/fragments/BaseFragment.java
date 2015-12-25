package tw.andyang.rxjavawithretrolambdasample.fragments;

import android.util.Log;

import com.trello.rxlifecycle.components.support.RxFragment;

public class BaseFragment extends RxFragment{

    protected void showErrorLog(Throwable throwable) {
        Log.e("Error", throwable.getLocalizedMessage());
    }

    protected boolean isEmptyOrNull(String input) {
        return input == null || input.equals("");
    }
}
