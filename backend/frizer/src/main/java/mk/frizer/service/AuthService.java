package mk.frizer.service;


import mk.frizer.model.BaseUser;

import java.util.List;

public interface AuthService {
    BaseUser login(String username, String password);

    List<BaseUser> findAll();

}

