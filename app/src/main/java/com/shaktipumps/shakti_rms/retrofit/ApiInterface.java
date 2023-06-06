package com.shaktipumps.shakti_rms.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.model.Register.RegisterResponse;
import com.shaktipumps.shakti_rms.model.UploadModel.ProfileUpdateModel;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiInterface {

    /**
     *
     * @param remainingURL
     * @param jsonObject
     * @return
     */

    @POST
    Call<JsonElement> postData(@Url String remainingURL, @Body JsonObject jsonObject);


    @PUT
    Call<JsonElement> putData(@Url String remainingURL, @Body JsonObject jsonObject);


    @POST
    Call<JsonElement> postDataNew(@Url String remainingURL, @Body JSONObject jsonObject);
  //  Map<String, String> params

    /**
     *
     * @param remainingURL
     * @param map
     * @return
     */


    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map);

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL);

    @Multipart
    @POST("ExcelUpload")
    Call<ProfileUpdateModel> getProfileUpdateData(@Part("DeviceNO") RequestBody deviceno, @Part("type") RequestBody type, @Part MultipartBody.Part file);

    @Multipart
    @POST("ExcelUpload")
    Call<ProfileUpdateModel> getProfileUpdateDatanew(@Part("DeviceNO") RequestBody deviceno, @Part("type") RequestBody type, @Part("columnCount") RequestBody columnCount, @Part MultipartBody.Part file);

    @POST("UserLogin")
    Call<RegisterResponse> sendUserRegister(@Query("MParentId")String value, @Query("MUserId") String val, @Query("firstName") String firstName,
                                            @Query("lastName") String lastName, @Query("MUserName") String userName,
                                            @Query("MPassword") String password, @Query("MobileNo") String mobileNo,
                                            @Query("MAddress") String address, @Query("Status") String status);


}

