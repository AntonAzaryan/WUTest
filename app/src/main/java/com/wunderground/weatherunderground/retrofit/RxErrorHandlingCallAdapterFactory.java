package com.wunderground.weatherunderground.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper<>(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper<R, T> implements CallAdapter<R, T> {

        private final Retrofit retrofit;
        private final CallAdapter<R, T> wrapped;

        RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, T> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public T adapt(Call<R> call) {
            T adaptedObj = wrapped.adapt(call);

            if (adaptedObj instanceof Single) {
                Single single = (Single) adaptedObj;
                return (T) single.onErrorResumeNext(new Function<Throwable, Single>() {
                    @Override
                    public Single apply(Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            }

            if (adaptedObj instanceof Observable) {
                Observable observable = (Observable) adaptedObj;
                return (T) observable.onErrorResumeNext(new Function<Throwable, Observable>() {
                    @Override
                    public Observable apply(Throwable throwable) throws Exception {
                        return Observable.error(asRetrofitException(throwable));
                    }
                });
            }

            if (adaptedObj instanceof Flowable) {
                Flowable flowable = (Flowable) adaptedObj;
                return (T) flowable.onErrorResumeNext(new Function<Throwable, Flowable>() {
                    @Override
                    public Flowable apply(Throwable throwable) throws Exception {
                        return Flowable.error(asRetrofitException(throwable));
                    }
                });
            }

            if (adaptedObj instanceof Completable) {
                Completable completable = (Completable) adaptedObj;
                return (T) completable.onErrorResumeNext(new Function<Throwable, Completable>() {
                    @Override
                    public Completable apply(Throwable throwable) throws Exception {
                        return Completable.error(asRetrofitException(throwable));
                    }
                });
            }

            if (adaptedObj instanceof Maybe) {
                Maybe maybe = (Maybe) adaptedObj;
                return (T) maybe.onErrorResumeNext(new Function<Throwable, Maybe>() {
                    @Override
                    public Maybe apply(Throwable throwable) throws Exception {
                        return Maybe.error(asRetrofitException(throwable));
                    }
                });
            }
            return adaptedObj;
        }

        RetrofitException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            }

            // A network error happened
            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.unexpectedError(throwable);
        }
    }
}
