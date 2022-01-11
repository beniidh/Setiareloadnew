package com.c.setiareload.Api;

import com.c.setiareload.CetakStruk.ResponStruk;
import com.c.setiareload.DaftarHarga.ResponProdukDH;
import com.c.setiareload.DaftarHarga.ResponProdukList;
import com.c.setiareload.DaftarHarga.ResponSubProdukDH;
import com.c.setiareload.Fragment.RekapSaldo.responRekap;
import com.c.setiareload.Fragment.RiwayatTransaksi.ResponTransaksi;
import com.c.setiareload.KomisiSales.ResponSales;
import com.c.setiareload.MarkUP.ResponMarkup;
import com.c.setiareload.MarkUP.markupSpesifik.ResponProdukDHM;
import com.c.setiareload.MarkUP.markupSpesifik.ResponProdukListM;
import com.c.setiareload.MarkUP.markupSpesifik.ResponSubProdukDHM;
import com.c.setiareload.MarkUP.sendMarkUP;
import com.c.setiareload.Model.MResestPIN;
import com.c.setiareload.Model.mResetPassword;
import com.c.setiareload.Notifikasi.ResponTransaksiN;
import com.c.setiareload.PengajuanLimit.ResponPengajuan;
import com.c.setiareload.PengajuanLimit.SendPengajuan;
import com.c.setiareload.PersetujuanSaldoSales.ResponPersetujuan;
import com.c.setiareload.PersetujuanSaldoSales.ResponPersetujuanSaldo;
import com.c.setiareload.PersetujuanSaldoSales.SendDataPersetujuan;
import com.c.setiareload.Profil.MPin;
import com.c.setiareload.Profil.MProfilEdit;
import com.c.setiareload.Profil.ResEdit;
import com.c.setiareload.Respon.Respon;
import com.c.setiareload.Respon.ResponBanner;
import com.c.setiareload.Respon.ResponEditKec;
import com.c.setiareload.Respon.ResponEditLokasi;
import com.c.setiareload.Respon.ResponEditPost;
import com.c.setiareload.Respon.ResponEditkel;
import com.c.setiareload.Respon.ResponK;
import com.c.setiareload.Respon.ResponKEditKab;
import com.c.setiareload.Respon.ResponKe;
import com.c.setiareload.Respon.ResponMenu;
import com.c.setiareload.Respon.ResponMenuUtama;
import com.c.setiareload.Respon.ResponPost;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.Respon.ResponResetPassword;
import com.c.setiareload.Respon.ResponResetPin;
import com.c.setiareload.Respon.ResponSubCategory;
import com.c.setiareload.Respon.ResponSubP;
import com.c.setiareload.Respon.Responkel;
import com.c.setiareload.Model.MOtpVerif;
import com.c.setiareload.Model.MRegisData;
import com.c.setiareload.Model.MRegister;
import com.c.setiareload.Model.Mlogin;
import com.c.setiareload.Model.Mphone;
import com.c.setiareload.Model.MsetPIN;
import com.c.setiareload.Model.Responphoto;
import com.c.setiareload.SaldoServer.AddUPP;
import com.c.setiareload.SaldoServer.ResponTagihanPayLatter;
import com.c.setiareload.SaldoServer.ResponUPP;
import com.c.setiareload.SaldoServer.Responn;
import com.c.setiareload.TagihanKonter.ResponApprove;
import com.c.setiareload.TagihanKonter.ResponTagihanKonter;
import com.c.setiareload.TagihanKonter.SendApprove;
import com.c.setiareload.TambahKonter.ResponTambahKonter;
import com.c.setiareload.TambahKonter.SendDataKonter;
import com.c.setiareload.TopUpSaldoku.ReqSaldoku;
import com.c.setiareload.TopUpSaldoku.ResponTopUp;
import com.c.setiareload.Transaksi.MInquiry;
import com.c.setiareload.Transaksi.ResponInquiry;
import com.c.setiareload.Transfer.ModelKonter;
import com.c.setiareload.Transfer.Mtransfer;
import com.c.setiareload.TagihanKonterSales.ResponTagihanKonterSales;
import com.c.setiareload.TransferBank.MTransfer;
import com.c.setiareload.TransferBank.MinquiryBank;
import com.c.setiareload.TransferBank.ModelNamaBank;
import com.c.setiareload.TransferBank.ResponInquiryBank;
import com.c.setiareload.TransferBank.ResponTransfer;
import com.c.setiareload.konter.Mkonter;
import com.c.setiareload.menuUtama.PaketData.AngsuranKredit.ResponAngsuran;
import com.c.setiareload.menuUtama.PaketData.AngsuranKredit.ResponProdukAngsuran;
import com.c.setiareload.menuUtama.PaketData.BPJS.ResponBPJS;
import com.c.setiareload.menuUtama.PaketData.BPJS.ResponProdukBPJS;
import com.c.setiareload.menuUtama.PaketData.GasNegara.ResponGasnegara;
import com.c.setiareload.menuUtama.PaketData.GasNegara.ResponProdukGasnegara;
import com.c.setiareload.menuUtama.PaketData.Internet.ResponIntenet;
import com.c.setiareload.menuUtama.PaketData.Internet.ResponProdukInternet;
import com.c.setiareload.menuUtama.PaketData.ListrikPLN.ResponListrikPln;
import com.c.setiareload.menuUtama.PaketData.ListrikPLNPasca.ResponCodeSub;
import com.c.setiareload.menuUtama.PaketData.ListrikPLNPasca.ResponGetCodePasca;
import com.c.setiareload.menuUtama.PaketData.ListrikPLNPasca.ResponListrikPlnPasca;
import com.c.setiareload.menuUtama.PaketData.PajakPBB.ResponPajak;
import com.c.setiareload.menuUtama.PaketData.PajakPBB.ResponProdukPBB;
import com.c.setiareload.menuUtama.PaketData.Paket.ResponPaketData;
import com.c.setiareload.menuUtama.PaketData.PaketsmsTelpon.ResponProdukSmsTelp;
import com.c.setiareload.menuUtama.PaketData.PaketsmsTelpon.ResponSmsTelpon;
import com.c.setiareload.menuUtama.PaketData.PulsaPascaBayar.ResponProdukSubPPasca;
import com.c.setiareload.menuUtama.PaketData.PulsaPascaBayar.ResponPulsaPasca;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.MTransaksiPraPulsa;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.Mchek;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.ResponPulsaPra;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.ResponTransaksiPulsaPra;
import com.c.setiareload.menuUtama.PaketData.TV.ResponProdukTV;
import com.c.setiareload.menuUtama.PaketData.TV.ResponTV;
import com.c.setiareload.menuUtama.PaketData.UangElektronik.ResponProdukUE;
import com.c.setiareload.menuUtama.PaketData.UangElektronik.ResponUangElektronik;
import com.c.setiareload.menuUtama.PaketData.Voucher.ResponProdukVoucherv;
import com.c.setiareload.menuUtama.PaketData.Voucher.ResponVoucher;
import com.c.setiareload.menuUtama.PaketData.VoucherGame.ResponProdukVoucher;
import com.c.setiareload.menuUtama.PaketData.VoucherGame.ResponVoucherGame;
import com.c.setiareload.menuUtama.PaketData.air.ResponAir;
import com.c.setiareload.menuUtama.PaketData.air.ResponProdukAir;
import com.c.setiareload.reseller.ResponSaldoReseller;
import com.c.setiareload.reseller.mSetujuSaldo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("signin")
    Call<Mlogin> Login(@Body Mlogin mlogin);

    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<MRegister> Register(@Body MRegister mRegister);

    @Headers("Content-Type: application/json")
    @POST("otp-email")
    Call<MRegisData> SendOTP(@Body MRegisData mRegisData);

    @Headers("Content-Type: application/json")
    @POST("otp-verify")
    Call<MOtpVerif> verifOTP(@Body MOtpVerif mOtpVerif);

    @Multipart
    @POST("ekyc-idcard")
    Call<Responphoto> uploadImage(@Part MultipartBody.Part image, @Part("id") RequestBody id);

    @Multipart
    @POST("ekyc-selfie")
    Call<Responphoto> uploadImageDiri(@Part MultipartBody.Part image, @Part("id") RequestBody id);

    @POST("transaction")
    Call<ResponTransfer> TransferBank(@Header("X-Signature") String token,
                                      @Body MTransfer mTransfer);

    @GET("product-us/sub-category/{id}")
    Call<ResponGetCodePasca> getCodePLNPasca(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponCodeSub> getCodeSubPln(@Header("X-Signature") String token, @Path("id") String id);

    @Multipart
    @POST("ekyc-idcardselfie")
    Call<Responphoto> uploadImageDiridanKTP(@Part MultipartBody.Part image, @Part("id") RequestBody id);

    @Multipart
    @POST("photo-upload")
    Call<ResponTopUp> uploadBuktiBayar(@Header("X-Signature") String token,@Part MultipartBody.Part image, @Part("type") RequestBody type, @Part("primary_id") RequestBody primary_id);

    @GET("all-province?next=39")
    Call<Respon> getAllProvinsi();

    @GET("profile")
    Call<Mlogin> getProfile(@Header("X-Signature") String token);

    @GET("banner/serverid/{id}")
    Call<ResponBanner> getBanner(@Header("X-Signature") String token,@Path("id") String id);

    @GET("profile")
    Call<ResponProfil> getProfileDas(@Header("X-Signature") String token);

    @GET("all-product-category")
    Call<ResponMenu> getAllProduct(@Header("X-Signature") String token);

    @POST("set-pin")
    Call<MsetPIN> SetPIN(@Header("X-Signature") String token, @Body MsetPIN msetPIN);

    @POST("send-saldoku")
    Call<Mtransfer> sendsaldoku(@Header("X-Signature") String token, @Body Mtransfer mtransfer);

    @POST("pengajuan-dompet")
    Call<SendPengajuan> SetPengajuanLimit(@Header("X-Signature") String token, @Body SendPengajuan pengajuan);

    @POST("request-paylater")
    Call<SendPengajuan> SetPayLetter(@Header("X-Signature") String token, @Body SendPengajuan pengajuan);

    @GET("request-paylater")
    Call<Responn> GetPayLetter(@Header("X-Signature") String token);

    @GET("request-paylater")
    Call<ResponPengajuan> getPengajuanDompet(@Header("X-Signature") String token);

    @POST("auth-check")
    Call<Mphone> ChekPhone(@Body Mphone mphone);

    @GET("regencies/province/{id}")
    Call<ResponK> getAllKabupaten(@Path("id") long id);

    @GET("districts/regencies/{id}")
    Call<ResponKe> getAllKecamatan(@Path("id") long id);

    @Headers("Content-Type: application/json")
    @GET("province/{id}")
    Call<ResponEditLokasi> getProvinsiByIdd(@Path("id") long id);

    @GET("regencies/{id}")
    Call<ResponKEditKab> getKabupatenById(@Path("id") long id);

    @GET("product-us/sub-category/{id}")
    Call<ResponPulsaPra> getProdukPulsaPraById(@Header("X-Signature") String token, @Path("id") String id);

    @GET("districts/{id}")
    Call<ResponEditKec> getKecamatanById(@Path("id") long id);

    @GET("sub-districts/{id}")
    Call<ResponEditkel> getKelurahanById(@Path("id") long id);

    @GET("postal-code/{id}")
    Call<ResponEditPost> getPostById(@Path("id") long id);

    @GET("product-subcategory/prefix/{id}/{prefix}")
    Call<ResponSubCategory> getSubPrdoductByPrefix(@Header("X-Signature") String token, @Path("prefix") String prefix, @Path("id") String id);

    @GET("sub-districts/districts/{id}")
    Call<Responkel> getAllKelurahan(@Path("id") long id);

    @GET("postal-code/sub-districts/{id}")
    Call<ResponPost> getAllPost(@Path("id") long id);

    @GET("all-product-category?$limit=&$order=urutan&status=1")
    Call<ResponMenuUtama> getAllMenu(@Header("X-Signature") String token);

    @GET("all-product-category?$limit=&$order=urutan&status=1")
    Call<ResponMenuUtama> getAllMenu2(@Header("X-Signature") String token);

    @GET("product-subcategory/category/{id}")
    Call<ResponSubP> getSubCategoryPLN(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponListrikPln> getProdukPLNListrik(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponListrikPlnPasca> getProdukPLNListrikPasca(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponVoucherGame> getProdukVoucherGame(@Header("X-Signature") String token, @Path("id") String id);

    @POST("request-saldoku")
    Call<ReqSaldoku> AddRequestSaldoku(@Header("X-Signature") String token, @Body ReqSaldoku saldoku);

    @POST("set-pin")
    Call<MPin> UbahPin(@Header("X-Signature") String token, @Body MPin mPin);

    @POST("inquiry")
    Call<ResponInquiry> CekInquiry(@Header("X-Signature") String token, @Body MInquiry mInquiry);

    @POST("approve-paylater")
    Call<ResponPersetujuan> sendDataPersetujuan(@Header("X-Signature") String token, @Body SendDataPersetujuan sendDataPersetujuan);

    @GET("approve-paylater")
    Call<ResponPersetujuanSaldo> getDataAprroval(@Header("X-Signature") String token);

    @POST("register-konter")
    Call<ResponTambahKonter> registerKonter(@Header("X-Signature") String token, @Body SendDataKonter sendDataKonter);

    @POST("reset-pin")
    Call<ResponResetPin> resetPIN(@Header("X-Signature") String token, @Body MResestPIN mResestPIN);

    @POST("transaction")
    Call<ResponTransaksiPulsaPra> transalsiPulsaPra(@Header("X-Signature") String token, @Body MTransaksiPraPulsa mTransaksiPraPulsa);

    @GET("transaction/status/{id}")
    Call<Mchek> CekTransaksi(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponProdukSmsTelp> getProdukSmsTelpon(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponSmsTelpon> getProdukSMST(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponPaketData> getPaketDataProduk(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/prefix/{id}/{prefix}")
    Call<ResponPulsaPasca> getSubPulsaPascaByPrefix(@Header("X-Signature") String token, @Path("prefix") String prefix, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukSubPPasca> getProdukPulsaPasca(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukVoucher> getProdukVG(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponUangElektronik> getProdukCategoryUE(@Header("X-Signature") String token, @Path("id") String id);

    @GET("transaction/history")
    Call<ResponTransaksi> getHistoriTransaksi(@Header("X-Signature") String token, @Query("date") String date);

    @GET("transaction/history?date=week")
    Call<ResponTransaksiN> getHistoriTransaksiN(@Header("X-Signature") String token);

    @GET("transaction/history?date=week")
    Call<ResponStruk> getHistoriStruk(@Header("X-Signature") String token);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukUE> getProdukUE(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponAir> getProdukCategoryAir(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukAir> getProdukAir(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponIntenet> getProdukInternet(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukInternet> getProdukInternetsub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponTV> getProdukTV(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukTV> getProdukTVsub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponVoucher> getProdukVoucher(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukVoucherv> getProdukVoucherSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponBPJS> getProdukBpjs(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponBPJS> getProdukBpjsSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponAngsuran> getProdukAngsuran(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponProdukAngsuran> getProdukAngsuranSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponPajak> getProdukPajak(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponProdukPBB> getProdukPBBSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponGasnegara> getProdukGas(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponProdukGasnegara> getProdukGasSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("all-product-category?$limit=0&$order=urutan&status=1")
    Call<ResponProdukDH> getProdukDH(@Header("X-Signature") String token);
    @GET("all-product-category?$limit=0&$order=urutan&status=1")
    Call<ResponProdukDHM> getProdukDHM(@Header("X-Signature") String token);

    @GET("product-subcategory/category/{id}")
    Call<ResponSubProdukDH> getProdukDHsub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponSubProdukDHM> getProdukDHsubM(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukList> getProdukDHList(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukListM> getProdukDHListM(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us-sm/sub-category/{id}")
    Call<ResponProdukListM> getProdukDHListMM(@Header("X-Signature") String token, @Path("id") String id);

    @POST("inquiry")
    Call<ResponInquiryBank> getInquiryBank(@Header("X-Signature") String token, @Body MinquiryBank minquiryBank);

    @POST("reset-password")
    Call<ResponResetPassword> resetPassword(@Body mResetPassword mResest);

    @GET("user-paylater")
    Call<ResponTagihanPayLatter> getTagihan(@Header("X-Signature") String token);

    @GET("users-referal")
    Call<Mkonter> getKonterSales(@Header("X-Signature") String token);

    @POST("user-paylater-payment")
    Call<ResponUPP> SetUPP(@Header("X-Signature") String token, @Body AddUPP upp);

    @GET("user-paylater-payment")
    Call<ResponTagihanKonter> getTagihanSales(@Header("X-Signature") String token);

    @GET("users-referal")
    Call<ModelKonter> getKonter(@Header("X-Signature") String token);

    @POST("approve-paylater-payment")
    Call<ResponApprove> ApproveTagihan(@Header("X-Signature") String token, @Body SendApprove approve);

    @Headers({"Content-Type: application/json"})
    @PUT("profile")
    Call<ResEdit> editProfil(@Header("X-Signature") String token, @Body MProfilEdit mProfilEdit);

    @PUT("users/markup")
    Call<ResponMarkup> markup(@Header("X-Signature") String token, @Body sendMarkUP markup);

    @GET("sales-komisi/history")
    Call<ResponSales> getKomisiSales(@Header("X-Signature") String token,@Query("datestart")
            String datestart,@Query("dateend") String dateend);

    @GET("user-paylater/sales")
    Call<ResponTagihanKonterSales> getTagihanSalesKonter(@Header("X-Signature") String token);
    @GET("all-bank")
    Call<ModelNamaBank> getNamaBank(@Header("X-Signature") String token);

    @GET("history/saldo")
    Call<responRekap> getSaldoRekap(@Header("X-Signature") String token,
                                    @Query("start") String start,@Query("end") String end,
                                    @Query("type") String type);
    @Multipart
    @PUT("product-us-sm/{id}")
    Call<ResponMarkup> markupSpesifik(@Header("X-Signature") String token,@Path("id")String id,@Part("user_id") RequestBody user_id,@Part("server_code") RequestBody server_code
                                      ,@Part("sales_code") RequestBody sales_code,@Part("product_id") RequestBody product_id,
                                      @Part("markup_price") RequestBody markup_price,@Part("status") RequestBody status);

    @GET("approve-saldoku-reseller")
    Call<ResponSaldoReseller> getSaldoReseller(@Header("X-Signature") String token);

    @POST("approve-paylater-payment")
    Call<ResponApprove> ApproveSaldokuReselesser(@Header("X-Signature") String token, @Body mSetujuSaldo setuju);
}
