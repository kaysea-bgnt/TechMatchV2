package appdev.com.techmatch.service;

import appdev.com.techmatch.model.UserAccount;
import appdev.com.techmatch.repository.AccountRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private AccountRepo accountRepo;

    public List<UserAccount> listAll() {
        return accountRepo.findAll();
    }
    
    public UserAccount CreateUser(UserAccount user) {
        return accountRepo.save(user);
    }

    public void deleteUser(Long id) {
        accountRepo.deleteById(id);
    }
}
