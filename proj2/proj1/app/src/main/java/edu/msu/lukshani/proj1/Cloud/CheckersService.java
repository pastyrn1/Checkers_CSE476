package edu.msu.lukshani.proj1.Cloud;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static edu.msu.lukshani.proj1.Cloud.Cloud.CREATE_PATH;

public interface CheckersService {
    @FormUrlEncoded
    @POST(CREATE_PATH)
    Call<CreateResult> createUser(@Field("xml") String xmlData);
}
