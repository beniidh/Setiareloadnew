package com.c.dompetabata.Model;

public class MOtpVerif {

    String user_id,otp_id,otp,code;

    public MOtpVerif(String user_id, String otp_id, String otp) {
        this.user_id = user_id;
        this.otp_id = otp_id;
        this.otp = otp;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOtp_id() {
        return otp_id;
    }

    public String getOtp() {
        return otp;
    }

    public String getCode() {
        return code;
    }
}
