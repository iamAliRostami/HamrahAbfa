package com.leon.hamrah_abfa.infrastructure;

import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;
import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.cards.BillsSummary;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileViewModel;
import com.leon.hamrah_abfa.fragments.checkout.Kardex;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQ;
import com.leon.hamrah_abfa.fragments.contact_us.ForbiddenViewModel;
import com.leon.hamrah_abfa.fragments.counter.CounterViewModel;
import com.leon.hamrah_abfa.fragments.follow_request.DetailHistory;
import com.leon.hamrah_abfa.fragments.follow_request.DetailHistoryItem;
import com.leon.hamrah_abfa.fragments.follow_request.MasterHistory;
import com.leon.hamrah_abfa.fragments.last_bill.BillViewModel;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.fragments.usage_history.Attempt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAbfaService {
    @POST("kontoriNew/V1/MobileAccount/PreLogin")
    Call<PreLoginViewModel> preLogin(@Body VerificationViewModel preLogin);

    @POST("kontoriNew/V1/MobileAccount/VerifyCode")
    Call<PreLoginViewModel> verifyCode(@Body VerificationViewModel preLogin);

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

    @GET("KontoriNew/V1/ContactAbfa/Faq")
    Call<ContactFAQ> getFAQ();

    @GET("KontoriNew/V1/MobileRequest/MasterHistory/{id}")
    Call<MasterHistory> getMasterHistory(@Path("id") String id);

    @GET("KontoriNew/V1/MobileRequest/DetailsHistory/{trackNumber}")
    Call<DetailHistory> getDetailHistory(@Path("trackNumber") int trackNumber);

    @GET("KontoriNew/V1/MobileRequest/DetailsInState/{id}")
    Call<DetailHistoryItem> getDetailHistoryItem(@Path("id") String id);

    @POST("kontoriNew/V1/Verification/Code")
    Call<VerificationViewModel> askVerificationCode(@Body VerificationViewModel verification);

    @POST("kontoriNew/V1/MobileBill/Generate")
    Call<CounterViewModel> generateBill(@Body CounterViewModel counter);

    @POST("KontoriNew/V1/MobileRequest/ChangeMobile")
    Call<ChangeMobileViewModel> changeMobile(@Body ChangeMobileViewModel changeMobile);

    @POST("KontoriNew/V1/MobileRequest/New")
    Call<ServicesViewModel> requestNew(@Body ServicesViewModel service);

    @POST("KontoriNew/V1/MobileRequest/AS")
    Call<ServicesViewModel> requestAfter(@Body ServicesViewModel service);

    @POST("KontoriNew/V1/MobileRequest/AbBaha")
    Call<ServicesViewModel> requestAb(@Body ServicesViewModel service);


    @POST("KontoriNew/V1/ForbiddenMobile/Single")
    Call<ForbiddenViewModel> forbidden(@Body ForbiddenViewModel forbidden);

}

