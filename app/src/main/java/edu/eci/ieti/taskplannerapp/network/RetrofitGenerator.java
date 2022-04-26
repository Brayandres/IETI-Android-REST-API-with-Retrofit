package edu.eci.ieti.taskplannerapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import edu.eci.ieti.taskplannerapp.network.interceptor.AuthInterceptor;
import edu.eci.ieti.taskplannerapp.network.storage.Storage;

public class RetrofitGenerator {

    private static Retrofit retrofitInstance;

    public Retrofit getRetrofitInstance(Storage storage) {
        if (retrofitInstance == null) {
            retrofitInstance = createRetrofitInstance(storage);
        }
        return retrofitInstance;
    }

    static private Retrofit createRetrofitInstance(Storage storage) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl("https://tasks-planner-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())
                );

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new AuthInterceptor(storage))
                .writeTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        return builder.client(okHttpClient).build();
    }
}