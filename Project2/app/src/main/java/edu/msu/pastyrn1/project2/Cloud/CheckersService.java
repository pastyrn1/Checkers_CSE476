package edu.msu.pastyrn1.project2.Cloud;

import edu.msu.pastyrn1.project2.Cloud.Models.CreateResult;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.POST;

import static edu.msu.pastyrn1.project2.Cloud.Cloud.CREATE_PATH;

public interface CheckersService {
    @FormUrlEncoded
    @POST(CREATE_PATH)
    Call<CreateResult> createUser(
            @Field("xml") String xmlData
    );
}
