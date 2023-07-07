package com.leon.hamrah_abfa.infrastructure;

import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAbfaService {
    @POST("kontoriNew/V1/MobileAccount/PreLogin")
    Call<PreLoginViewModel> preLogin(@Body PreLoginViewModel preLogin);


    @POST("kontoriNew/V1/MobileAccount/VerifyCode")
    Call<PreLoginViewModel> verifyCode(@Body PreLoginViewModel preLogin);
}

