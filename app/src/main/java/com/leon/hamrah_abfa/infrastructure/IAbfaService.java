package com.leon.hamrah_abfa.infrastructure;

import com.leon.hamrah_abfa.di.view_model.Bills;
import com.leon.hamrah_abfa.di.view_model.LastBillViewModel;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;
import com.leon.hamrah_abfa.fragments.ui.cards.BillCardViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAbfaService {
    @POST("kontoriNew/V1/MobileAccount/PreLogin")
    Call<PreLoginViewModel> preLogin(@Body PreLoginViewModel preLogin);

    @POST("kontoriNew/V1/MobileAccount/VerifyCode")
    Call<PreLoginViewModel> verifyCode(@Body PreLoginViewModel preLogin);

    @GET("KontoriNew/V1/MobileBill/Get")
    Call<Bills> getBills();

    @POST("kontoriNew/V1/MobileBill/Add")
    Call<BillCardViewModel> addBill(@Body BillCardViewModel bill);

    @GET("/KontoriNew/V1/MobileBill/GetLast/{id}")
    Call<LastBillViewModel> getLast(@Path("id") String id);
}

