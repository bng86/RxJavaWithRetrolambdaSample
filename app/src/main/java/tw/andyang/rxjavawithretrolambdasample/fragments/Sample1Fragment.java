package tw.andyang.rxjavawithretrolambdasample.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tw.andyang.rxjavawithretrolambdasample.R;
import tw.andyang.rxjavawithretrolambdasample.api.NetworkService;
import tw.andyang.rxjavawithretrolambdasample.model.Pet;

public class Sample1Fragment extends BaseFragment {

    private NetworkService.Api api = NetworkService.getInstance().api;

    public static Sample1Fragment newInstance() {

        Bundle args = new Bundle();

        Sample1Fragment fragment = new Sample1Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);

        ProgressDialog dialog = ProgressDialog.show(getActivity(), "Get pets data", "Loading");

        api.getPets()
                .compose(bindToLifecycle())
                .map(petsResponse -> petsResponse.getResult().getResults())
                .retry(this::networkRetry)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo(dialog::dismiss)
                .subscribe(adapter::setPets, this::showErrorLog, this::onComplete);

        Observable.create(subscriber -> {
            subscriber.onNext("");
            subscriber.onCompleted();
            subscriber.onError(null);
        });

    }

    private void onComplete(){

    }

    private boolean networkRetry(int times, Throwable throwable) {
        RetrofitError retrofitError = (RetrofitError) throwable;
        return retrofitError.getKind() == RetrofitError.Kind.NETWORK || times < 3;
    }

    private class MyAdapter extends BaseAdapter {

        private Context context;
        private List<Pet> pets = new ArrayList<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        public void setPets(List<Pet> pets) {
            this.pets = pets;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return pets.size();
        }

        @Override
        public Object getItem(int position) {
            return pets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
                holder.name = (TextView) view.findViewById(R.id.nameTextView);
                holder.type = (TextView) view.findViewById(R.id.typeTextView);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.name.setText(pets.get(position).getName());
            holder.type.setText(pets.get(position).getType());

            return view;
        }

        private class ViewHolder {
            TextView name, type;
        }
    }

    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
