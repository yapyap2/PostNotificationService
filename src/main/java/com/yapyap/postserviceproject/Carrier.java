package com.yapyap.postserviceproject;

public enum Carrier {

    CJ(1), POST(2);

    private final int value;

    Carrier(int value) {
        this.value = value;
    }

    public int intValue(){
        return value;
    }

    public static Carrier valueOf(int value){

        switch (value){
            case 1 : return CJ;
            case 2 : return POST;
            default: throw new AssertionError("Unknown Value : " + value);
        }
    }

    public static int carrierCount(){
        return values().length;
    }
}
