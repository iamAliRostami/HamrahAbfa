package com.leon.hamrah_abfa.infrastructure;

import com.leon.hamrah_abfa.di.view_model.PreLoginViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAbfaService {

    @POST("kontoriNew/V1/MobileAccount/PreLogin")
    Call<PreLoginViewModel> login(@Body PreLoginViewModel loginViewModel);
}

