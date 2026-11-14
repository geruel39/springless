package springlesson.controller;

import springlesson.model.Account;
import springlesson.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(
            @PathVariable Long userId,
            @RequestBody Account account) {

        return accountService.createAccount(userId, account);
    }

    @GetMapping
    public List<Account> getUserAccounts(
            @PathVariable Long userId) {

        return accountService.getUserAccounts(userId);
    }
}

