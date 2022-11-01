package com.test.test_app.login;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class loginDto {
    @SerializedName("COMP_CD")
    private String COMP_CD;

    @SerializedName("ID")
    private String ID;

    @SerializedName("PW")
    private String PW;
}
