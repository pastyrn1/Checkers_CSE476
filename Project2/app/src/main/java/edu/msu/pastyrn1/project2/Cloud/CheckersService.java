package edu.msu.pastyrn1.project2.Cloud;

import edu.msu.pastyrn1.project2.Cloud.Models.SimpleResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Field;

import static edu.msu.pastyrn1.project2.Cloud.Cloud.CREATE_PATH;
import static edu.msu.pastyrn1.project2.Cloud.Cloud.LOAD_PATH;
import static edu.msu.pastyrn1.project2.Cloud.Cloud.SET_PATH;
import static edu.msu.pastyrn1.project2.Cloud.Cloud.UPDATE_PATH;

public interface CheckersService {
    @GET(LOAD_PATH)
    Call<SimpleResult> loginUser(
            @Query("user") String username,
            @Query("pw") String password
    );

    @FormUrlEncoded
    @POST(CREATE_PATH)
    Call<SimpleResult> createUser(@Field("xml") String xmlData);

    @GET(SET_PATH)
    Call<SimpleResult> setBoard();

    @FormUrlEncoded
    @POST(UPDATE_PATH)
    Call<SimpleResult> updatePiece(@Field("xml") String xmlData);
}