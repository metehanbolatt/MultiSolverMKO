package com.deu.multisolvermko.topsis_ahp.topsis.models;

public class ThreeCriteriaTopsisModel {

    String alternate1;
    String alternate2;
    String alternate3;

    public ThreeCriteriaTopsisModel(String alternate1, String alternate2, String alternate3) {
        this.alternate1 = alternate1;
        this.alternate2 = alternate2;
        this.alternate3 = alternate3;
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
}
