package com.example.bank;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;  
import java.util.List;
import java.util.ArrayList;

@Service
public class CustomerService {
    private List<Customer> customers = new ArrayList<>();

    @PostConstruct
    public void init() {
        customers.add(new Customer("01229385253", "Ali", "1001", 5000.00, "4111111111111111", "1234", 200.00));
        customers.add(new Customer("01111222233", "Mona", "1002", 8000.00, "4222222222222222", "5678", 0.00));
    }

    public Customer findByPhone(String phone) {
        return customers.stream()
                .filter(c -> c.getPhoneNumber().equals(phone))
                .findFirst()
                .orElse(null);
    }

    public Customer findByAccount(String accountNumber) {
    return customers.stream()
            .filter(c -> c.getAccountNumber().equals(accountNumber))
            .findFirst()
            .orElse(null);
        
    }

    public Customer findByCard(String cardNumber) {
    return customers.stream()
        .filter(c -> c.getCardNumber().equals(cardNumber))
        .findFirst()
        .orElse(null);
  }

    public Customer validateCard(String cardNumber, String password) {
        return customers.stream()
                .filter(c -> c.getCardNumber().equals(cardNumber) && c.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public double getBalance(String accountNumber) {
        Customer c = customers.stream()
                .filter(cust -> cust.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
        return (c != null) ? c.getBalance() : -1;
    }

    public double getPaymentDue(String accountNumber) {
        Customer c = customers.stream()
                .filter(cust -> cust.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
        return (c != null) ? c.getPaymentDue() : -1;
   }

    public boolean fundTransfer(String fromAccount, String toAccount, double amount) {
        Customer sender = customers.stream()
                .filter(cust -> cust.getAccountNumber().equals(fromAccount))
                .findFirst()
                .orElse(null);
        Customer receiver = customers.stream()
                .filter(cust -> cust.getAccountNumber().equals(toAccount))
                .findFirst()
                .orElse(null);

        if (sender != null && receiver != null && sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            return true;
        }
        return false;
    }

    public boolean activateCard(String cardNumber) {
        Customer c = customers.stream()
                .filter(cust -> cust.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);
        if (c != null) {
            return true;
        }
        return false;
    }

    public boolean blockCard(String cardNumber) {
        Customer c = customers.stream()
                .filter(cust -> cust.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);
        if (c != null) {
            return true;
        }
        return false;
    }

    public Customer findByAny(String cardNumber, String phoneNumber, String accountNumber) {
        if (cardNumber != null && !cardNumber.isEmpty()) {
            return findByCard(cardNumber);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return findByPhone(phoneNumber);
        }
        if (accountNumber != null && !accountNumber.isEmpty()) {
            return findByAccount(accountNumber);
        }
        return null;
    }
 
}
