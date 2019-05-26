package com.supplier;
import java.util.*;

public class ServeUser extends  Thread {

    double totalAmount;
    double requiredAmount;
    String itemChosen;
    Map<Double,Integer> givenCurrency;

    public ServeUser(double totalAmount, double requiredAmount, String itemChosen,Map<Double,Integer> givenCurrency) {
        this.totalAmount = totalAmount;
        this.requiredAmount = requiredAmount;
        this.itemChosen = itemChosen;
        this.givenCurrency = givenCurrency;
    }

    public ServeUser() {

    }
    @Override
    public void run() {
        try {

            double change = totalAmount-requiredAmount;
            int num2Dollars =0,num1Dollar=0,num25Cents=0,num10Cents=0,num5Cents=0;

            while(change > 2) {
                num2Dollars++;
                change=change-2.00;
            }

            while(change > 1) {
                num1Dollar++;
                change=change-2.00;
            }

            while(change > 0.25) {
                num25Cents++;
                change=change-0.25;
            }

            while(change > 0.10) {
                num10Cents++;
                change=change-0.10;
            }

            while(change > 0.05) {
                num5Cents++;
                change=change-0.05;
            }

            System.out.println(" Processing your request........................................");
            Thread.sleep(30000);
            System.out.println("************************* Congrats here is your item **************************");
            System.out.print("Please pick your item:"+this.itemChosen);
            System.out.println("And here is the change");
            System.out.println("5 cents:"+num5Cents);
            System.out.println("10 cents:"+num10Cents);
            System.out.println("25 cents:"+num25Cents);
            System.out.println("1 Dollar:"+num1Dollar);
            System.out.println("2 Dollar:"+num2Dollars);


        }  catch(InterruptedException ie) {
            cancel();
        } catch (Exception e) {

        }

    }

    public void cancel() {
        System.err.println("Cancelling the request and returning the currency provided by you");
        System.err.println("5 cents:"+givenCurrency.get(0.05));
        System.err.println("10 cents:"+givenCurrency.get(0.10));
        System.err.println("25 cents:"+givenCurrency.get(0.25));
        System.err.println("1 Dollar:"+givenCurrency.get(1.00));
        System.err.println("2 Dollar:"+givenCurrency.get(2.00));

    }



}
