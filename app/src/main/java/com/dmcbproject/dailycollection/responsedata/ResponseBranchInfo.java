package com.dmcbproject.dailycollection.responsedata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponseBranchInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("branch_division")
    @Expose
    private String branchDivision;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("branch_code")
    @Expose
    private String branchCode;
    @SerializedName("branch_manager")
    @Expose
    private String branchManager;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchDivision() {
        return branchDivision;
    }

    public void setBranchDivision(String branchDivision) {
        this.branchDivision = branchDivision;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(String branchManager) {
        this.branchManager = branchManager;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}