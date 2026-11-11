package springlesson.service;

import org.springframework.stereotype.Service;

@Service
public class BankService {

    public String deposit() {
        return  "Money has been deposited!!!";
    }
}
