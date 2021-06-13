package com.barcode.cvs_review;

public class Database {
    private String CVS_NAME;
    private String PRODUCT_NAME;
    private String BARCODE;
    private String PRODUCT_IMAGE_URL;
    private String AVE_GRADE;
    private String USER_INFO_ID;
    private String COMMENTS;
    private String PRODUCT_POINT;


    public String getCVS_NAME() {
        return CVS_NAME;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public String getBARCODE(){return BARCODE;}

    public String getPRODUCT_IMAGE_URL(){return PRODUCT_IMAGE_URL;}

    public String getAVE_GRADE() {return AVE_GRADE;}

    public String getUSER_ID() {return USER_INFO_ID;}

    public String getCOMMENTS() {return COMMENTS;}

    public String getPRODUCT_POINT() {return PRODUCT_POINT;}

    public void setCVS_NAME(String CVS_NAME) {
        this.CVS_NAME = CVS_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public void setPRODUCT_IMAGE_URL(String PRODUCT_IMAGE_URL) {this.PRODUCT_IMAGE_URL = PRODUCT_IMAGE_URL;}

    public void setAVE_GRADE(String AVE_GRADE) {this.AVE_GRADE = AVE_GRADE;}

    public void setUSER_ID(String USER_INFO_ID) {this.USER_INFO_ID = USER_INFO_ID;}

    public void setCOMMENTS(String COMMENTS) {this.COMMENTS = COMMENTS;}

    public void setPRODUCT_POINT(String PRODUCT_POINT) {this.PRODUCT_POINT = PRODUCT_POINT;}

}
