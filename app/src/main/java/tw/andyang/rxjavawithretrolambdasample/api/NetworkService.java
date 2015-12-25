package tw.andyang.rxjavawithretrolambdasample.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;
import tw.andyang.rxjavawithretrolambdasample.response.PetsResponse;

public class NetworkService {

    public static final String END_POINT = "http://data.taipei/opendata/datalist";
    private volatile static NetworkService instance;
    public final Api api;

    private NetworkService() {

        Gson gson = new GsonBuilder().create();


        OkHttpClient client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        api = restAdapter.create(Api.class);

    }

    public static NetworkService getInstance() {

        if (instance == null) {
            synchronized (NetworkService.class) {
                if (instance == null) {
                    instance = new NetworkService();
                }
            }
        }

        return instance;
    }

    public interface Api {

        @Headers({
                "Accept: application/json",
                "Content-Type: application/json"
        })
        @GET("/apiAccess/?scope=resourceAquire&rid=f4a75ba9-7721-4363-884d-c3820b0b917c")
        Observable<PetsResponse> getPets();

    }
}
