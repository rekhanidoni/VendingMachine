package com.supplier;

import java.io.BufferedInputStream;
import java.util.*;

public class VendingMachine {

    static HashMap<String,Double> itemRates;
    String adminPassword = "admin";
    static Scanner sc = new Scanner(new BufferedInputStream(System.in));

    public void init() {
        itemRates = new HashMap<String,Double>();
        itemRates.put("Cola",1.25);
        itemRates.put("GingerAle",1.65);
        itemRates.put("Water",80.00);


    }

    VendingMachine() {
        init();
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();


        int itemnum;
        while (true) {
            try {
                System.out.println("************************      Let's Start     ************************");
                System.out.println("Make a selection (Type 1,2,3 or 4)");
                System.out.println("1. Cola " + itemRates.get("Cola") + "$");
                System.out.println("2. Ginger Ale " + itemRates.get("GingerAle") + "$");
                System.out.println("3. Water " + itemRates.get("Water") + "$");
                System.out.println("4. Reset rates");

                itemnum = Integer.parseInt(sc.nextLine());

                if (itemnum > 0 && itemnum < 5) {
                    String itemChosen = "Cola";
                    double requiredAmount = machine.itemRates.get("Cola");
                    switch(itemnum) {
                        case 2:
                            itemChosen = "GingerAle";
                            requiredAmount = machine.itemRates.get("GingerAle");
                            break;
                        case 3:
                            itemChosen = "Water";
                            requiredAmount = machine.itemRates.get("Water");
                            break;
                        case 4:
                            machine.performReset();
                            break;
                    }

                    if(itemnum ==4) continue;
                    System.out.println();
                    System.out.println("Please input a positive integer for each of these currencies indicating the quantity you would like to provide ");
                    System.out.println("5 cents:");
                    Map<Double,Integer> givenCurrency = new HashMap<>();
                    double totalAmount=0;
                    String str = sc.nextLine();
                    int num5Cents = str.equals("") ? 0 : Integer.parseInt(str);
                    totalAmount = totalAmount+num5Cents*0.05;
                    givenCurrency.put(0.05,num5Cents);

                    System.out.println("10 cents:");
                    str = sc.nextLine();
                    int num10Cents = str.equals("") ? 0 : Integer.parseInt(str);
                    totalAmount = totalAmount+num10Cents*0.10;
                    givenCurrency.put(0.10,num10Cents);

                    System.out.println("25 cents:");
                    str = sc.nextLine();
                    int num25Cents = str.equals("") ? 0 : Integer.parseInt(str);
                    totalAmount = totalAmount+num25Cents*0.25;
                    givenCurrency.put(0.25,num25Cents);

                    System.out.println("1 dollar:");
                    str = sc.nextLine();
                    int num1Dollar = str.equals("") ? 0 : Integer.parseInt(str);
                    totalAmount = totalAmount+num1Dollar*1;
                    givenCurrency.put(1.00,num1Dollar);

                    System.out.println("2 dollar:");
                    str = sc.nextLine();
                    int num2Dollar = str.equals("") ? 0 : Integer.parseInt(str);
                    totalAmount = totalAmount+num2Dollar*2;
                    givenCurrency.put(2.00,num2Dollar);

                    if(totalAmount < requiredAmount) {
                        System.err.println("************************ Sorry could not complete the request ************************");
                        System.err.println("Provided amount is less than " + requiredAmount);
                        System.err.println("Returning the amount as is");
                        System.err.println("5 cents:"+num5Cents);
                        System.err.println("10 cents:"+num10Cents);
                        System.err.println("25 cents:"+num25Cents);
                        System.err.println("1 Dollar:"+num1Dollar);
                        System.err.println("2 Dollar:"+num2Dollar);
                        System.err.println("***************************************************************************************");
                    } else {

                        ServeUser server = new ServeUser(totalAmount, requiredAmount, itemChosen, givenCurrency);
                        server.start();

                        System.out.println("Type \"cancel\" anytime to cancel the request or type \"reset\" to change item Rates");
                        String command = sc.nextLine();
                        if (command.equalsIgnoreCase("cancel")) {
                            server.interrupt();
                        } else if (command.equalsIgnoreCase("reset")) {
                            //wait for the current user to be served
                            server.join();
                            machine.performReset();
                        }
                        server.join();
                    }
                } else {
                    System.out.println("Please enter a valid number for selecting item");
                }
            } catch(Exception e) {

            }

        }

    }

    public void performReset() {
        //This is made simple here by just asking plain text password. It can be made secure by using DB to store credentails in a secure manner
        System.out.println("Please key in admin password to reset rates");
        String password = sc.nextLine();

        if(password.equals(adminPassword)) {
            System.out.println("Enter new rate for Cola. Existing Rate is " + itemRates.get("Cola") + "$. Press enter to retain the rate");
            String str = sc.nextLine();
            itemRates.put("Cola",str.equals("")?itemRates.get("Cola"):Double.parseDouble(str));

            System.out.println("Enter new rate for GingerAle. Existing Rate is  " + itemRates.get("GingerAle") + "$. Press enter to retain the rate");
            str = sc.nextLine();
            itemRates.put("GingerAle",str.equals("")?itemRates.get("GingerAle"):Double.parseDouble(str));

            System.out.println("Enter new rate for Water. Existing Rate is  " + itemRates.get("Water") + "$. Press enter to retain the rate");
            str = sc.nextLine();
            itemRates.put("Water",str.equals("")?itemRates.get("Water"):Double.parseDouble(str));
        } else  {
            System.err.println("Wrong password");
        }

    }
}
