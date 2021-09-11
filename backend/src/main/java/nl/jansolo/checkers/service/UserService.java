package nl.jansolo.checkers.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.checkers.config.UserBean;
import nl.jansolo.checkers.exception.OpponentNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {


    private final List<UserBean> users;

    public List<UserBean> findAllUsers(){
        return users;
    }

    public List<UserBean> getOpponents(Principal principal){
        return users.stream().filter(u -> !u.getName().equals(principal.getName())).collect(Collectors.toList());
    }

    public UserBean getOpponent(Principal principal, String opponentName){
       return  getOpponents(principal).stream().filter(o -> o.getName().equals(opponentName)).findFirst().orElseThrow(() -> new  OpponentNotFoundException(opponentName));
    }


}
