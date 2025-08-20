package com.example.bank;

public class Customer {
    private String phoneNumber;
    private String name;
    private String accountNumber;
    private double balance;
    private String cardNumber;
    private String password;
    private double paymentDue; 


    public Customer(String phoneNumber, String name, String accountNumber, 
                    double balance, String cardNumber, String password, double paymentDue) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.password = password;
        this.paymentDue = paymentDue;
    }

    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    public String getName() { 
        return name; 
    }
    public String getAccountNumber() { 
        return accountNumber; 
    }
    public double getBalance() { 
        return balance; 
    }
    public String getCardNumber() { 
        return cardNumber; 
    }
    public String getPassword() { 
        return password; 
    }
    public void setBalance(double balance) { 
        this.balance = balance; 
    }
    public double getPaymentDue() { 
        return paymentDue; 
    }
    public void setPaymentDue(double paymentDue) { 
        this.paymentDue = paymentDue; 
    }
}
