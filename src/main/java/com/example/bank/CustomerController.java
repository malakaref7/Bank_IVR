package com.example.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/check")
    public ResponseEntity<GenericResponse> checkCustomer(@RequestParam String phoneNumber) {
        Customer customer = customerService.findByPhone(phoneNumber);
        if (customer != null) {
            return successResponse("Customer found: " + customer.getName());
        } else {
            return badResponse("Your phone number is not registered at our bank. Please enter your card number.");
        }
    }

    @PostMapping("/validateCard")
    public ResponseEntity<GenericResponse> validateCard(@RequestBody CardRequest req) {
        Customer customer = customerService.validateCard(req.getCardNumber(), req.getPassword());
        if (customer != null) {
            return successResponse("Card validated successfully for customer: " + customer.getName());
        } else {
            return badResponse("Invalid card number or password. Please try again.");
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cardNumber) {

        Customer c = customerService.findByAny(cardNumber, phoneNumber, accountNumber);
        if (c != null) {
            return ResponseEntity.ok(String.valueOf(c.getBalance())); // just 5000.0
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
    }


    @GetMapping("/paymentDue")
    public ResponseEntity<String> getPaymentDue( @RequestParam(required = false) String accountNumber, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String cardNumber) {

        Customer c = customerService.findByAny(cardNumber, phoneNumber, accountNumber);
        if (c != null) {
            return ResponseEntity.ok(String.valueOf(c.getPaymentDue())); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("0"); 
        }
    }

    @PostMapping("/card/activate")
    public ResponseEntity<String> activateCard(
            @RequestBody CardRequest req,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String accountNumber) {

        Customer c = customerService.findByAny(
            req.getCardNumber() != null ? req.getCardNumber().trim() : null,
            phoneNumber != null ? phoneNumber.trim() : null,
            accountNumber != null ? accountNumber.trim() : null
        );
        if (c != null && customerService.activateCard(c.getCardNumber())) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("0");
        }
    }

    @PostMapping("/card/block")
    public ResponseEntity<String> blockCard( @RequestBody CardRequest req, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String accountNumber) {

        Customer c = customerService.findByAny(
            req.getCardNumber() != null ? req.getCardNumber().trim() : null,
            phoneNumber != null ? phoneNumber.trim() : null,
            accountNumber != null ? accountNumber.trim() : null
        );
        if (c != null && customerService.blockCard(c.getCardNumber())) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("0");
        }
    }

    @PostMapping("/fund/transfer") 
    public ResponseEntity<String> fundTransfer(@RequestBody FundTransferRequest req) { 
        if (customerService.fundTransfer(req.getFromAccount(), req.getToAccount(), req.getAmount())) { 
            return ResponseEntity.ok("Your transfer of " + req.getAmount() + " to account " + req.getToAccount() + " was successful."); 
        } else { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) 
                                .body("Transfer failed. Check account numbers or balance.");
                         
        } 
    }

    // Helper methods
    private ResponseEntity<GenericResponse> successResponse(String message) {
        return ResponseEntity.ok(new GenericResponse(true, message));
    }

    private ResponseEntity<GenericResponse> badResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse(false, message));
    }
}