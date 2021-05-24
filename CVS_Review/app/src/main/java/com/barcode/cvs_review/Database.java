package com.barcode.cvs_review;

public class Database {
    private String CVS_NAME;
    private String PRODUCT_NAME;
    private String BARCODE;
    private String PRODUCT_IMAGE_URL;


    public String getCVS_NAME() {
        return CVS_NAME;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public String getBARCODE(){return BARCODE;}

    public String getPRODUCT_IMAGE_URL(){return PRODUCT_IMAGE_URL;}

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

}
