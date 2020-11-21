package ar.com.edu.itba.hci_app.network.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerificationCodeModel {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     *
     */
    public VerificationCodeModel() {
    }

    /**
     *
     * @param code
     * @param email
     */
    public VerificationCodeModel(String email, String code) {
        super();
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public VerificationCodeModel withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public VerificationCodeModel withCode(String code) {
        this.code = code;
        return this;
    }

}
