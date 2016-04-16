package net.avenwu.yoyogithub.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * TODO
 * Retrofit 2.0.0-beta4, 2.0.2 will failed to select the proper converter
 * Created by aven on 4/15/16.
 */
public class GitHubConverterFactory extends Converter.Factory {
    GsonConverterFactory gsonConverterFactory;
    SimpleXmlConverterFactory simpleXmlConverterFactory;

    public static GitHubConverterFactory create() {
        return new GitHubConverterFactory();
    }

    public GitHubConverterFactory() {
        gsonConverterFactory = GsonConverterFactory.create();
        simpleXmlConverterFactory = SimpleXmlConverterFactory.create();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (xmlBean != null && xmlBean.contains(type)) {
            return simpleXmlConverterFactory.responseBodyConverter(type, annotations, retrofit);
        } else {
            return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (xmlBean != null && xmlBean.contains(type)) {
            return simpleXmlConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
        } else {
            return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
        }
    }

    List<Class> jsonBean;
    List<Class> xmlBean;

    public GitHubConverterFactory jsonBean(Class... clz) {
        jsonBean = Arrays.asList(clz);
        return this;
    }

    public GitHubConverterFactory xmlBean(Class... clz) {
        xmlBean = Arrays.asList(clz);
        return this;
    }
}
