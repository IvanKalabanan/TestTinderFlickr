package ivan.example.com.domain.repository.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import ivan.example.com.domain.repository.APICalls;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPICommunicator {
    public static final String TAG = "RestAPICommunicator";
    public static final String BASE_URL = "https://api.flickr.com/";

    private static RestAPICommunicator instance;

    private Retrofit retrofit;

    // use for cancel all calls
    private static Dispatcher dispatcher;

    public RestAPICommunicator() {
        init();
    }

    public static RestAPICommunicator getInstance() {
        if (instance == null) {
            instance = new RestAPICommunicator();
        }
        return instance;
    }

    private void init() {


        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // set your desired log level
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // for cancel all calls
        dispatcher = new Dispatcher();
        //

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(
                        new OkHttpClient.Builder()
                                .dispatcher(dispatcher)
                                .addInterceptor(loggingInterceptor)
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(10, TimeUnit.SECONDS)
                                .build()
                )
                .build();
    }

    public APICalls getCalls() {
        return retrofit.create(APICalls.class);
    }

    public static void cancelAllCalls(){
        dispatcher.cancelAll();
    }

}
