package springlesson.controller;

import org.springframework.web.bind.annotation.*;
import springlesson.service.BankService;

@RestController
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/deposit")
    public String deposit() {
        return bankService.deposit();
    }


}