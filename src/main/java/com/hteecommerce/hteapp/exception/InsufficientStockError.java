package com.hteecommerce.hteapp.exception;

public class InsufficientStockError extends Exception {

    public InsufficientStockError(){

    }

    public InsufficientStockError(String errMessage) {
        super(errMessage);
    }
    
    

}
