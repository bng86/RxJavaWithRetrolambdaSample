package tw.andyang.rxjavawithretrolambdasample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tw.andyang.rxjavawithretrolambdasample.R;

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sample1Button = (Button) view.findViewById(R.id.sample1Button);
        sample1Button.setOnClickListener(v -> addFragment(Sample1Fragment.newInstance()));

        Button sample2Button = (Button) view.findViewById(R.id.sample2Button);
        sample2Button.setOnClickListener(v -> addFragment(Sample2Fragment.newInstance()));

        Button sample3Button = (Button) view.findViewById(R.id.sample3Button);
        sample3Button.setOnClickListener(v -> addFragment(Sample3Fragment.newInstance()));

        Button sample4Button = (Button) view.findViewById(R.id.sample4Button);
        sample4Button.setOnClickListener(v -> addFragment(Sample4Fragment.newInstance()));

    }

    private void addFragment(Fragment fragment) {
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }
}
