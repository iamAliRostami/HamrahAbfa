package com.leon.hamrah_abfa.infrastructure;

import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.cards.BillsSummary;
import com.leon.hamrah_abfa.fragments.checkout.Kardex;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQ;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQViewModel;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;
import com.leon.hamrah_abfa.fragments.last_bill.BillViewModel;
import com.leon.hamrah_abfa.fragments.usage_history.Attempt;

import java.util.ArrayList;

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
    Call<BillsSummary> getBills();

    @POST("kontoriNew/V1/MobileBill/Add")
    Call<BillCardViewModel> addBill(@Body BillCardViewModel bill);

    @GET("/KontoriNew/V1/MobileBill/GetLast/{id}")
    Call<BillViewModel> getLast(@Path("id") String id);

    @GET("/KontoriNew/V1/MobileBill/GetThis/{zoneId}/{id}")
    Call<BillViewModel> getThis(@Path("id") int id, @Path("zoneId") int zoneId);

    @GET("/KontoriNew/V1/MobileBill/GetKardex/{id}")
    Call<Kardex> getKardex(@Path("id") String id);

    @GET("/KontoriNew/V1/MobileBill/GetAttempts/{id}")
    Call<Attempt> getAttempts(@Path("id") String id);

    @GET("/KontoriNew/V1/ContactAbfa/Get")
    Call<ContactFAQ> getFAQ();
}

