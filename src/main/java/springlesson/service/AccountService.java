package springlesson.service;

import springlesson.exception.ResourceNotFoundException;
import springlesson.model.Account;
import springlesson.model.User;
import springlesson.repository.AccountRepository;
import springlesson.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {

        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(Long userId, Account account) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID " + userId + " not found"
                        )
                );

        account.setUser(user);

        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User with ID " + userId + " not found"
            );
        }

        return accountRepository.findByUserId(userId);
    }
}

