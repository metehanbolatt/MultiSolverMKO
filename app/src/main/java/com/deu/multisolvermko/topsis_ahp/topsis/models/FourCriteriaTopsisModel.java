package com.deu.multisolvermko.topsis_ahp.topsis.models;

public class FourCriteriaTopsisModel {

    String alternate1;
    String alternate2;
    String alternate3;
    String alternate4;

    public FourCriteriaTopsisModel(String alternate1, String alternate2, String alternate3, String alternate4) {
        this.alternate1 = alternate1;
        this.alternate2 = alternate2;
        this.alternate3 = alternate3;
        this.alternate4 = alternate4;
    }

    public String getAlternate1() {
        return alternate1;
    }

    public String getAlternate2() {
        return alternate2;
    }

    public String getAlternate3() {
        return alternate3;
    }

    public String getAlternate4() {
        return alternate4;
    }
}
