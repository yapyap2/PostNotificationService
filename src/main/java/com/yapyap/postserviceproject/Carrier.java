package com.yapyap.postserviceproject;

public enum Carrier {

    CJ(1), POST(2), LOGEN(3), HANJIN(4), LOTTE(5);

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
            case 3 : return LOGEN;
            case 4 : return HANJIN;
            case 5 : return LOTTE;
            default: throw new AssertionError("Unknown Value : " + value);
        }
    }

    public static int carrierCount(){
        return values().length;
    }
}
