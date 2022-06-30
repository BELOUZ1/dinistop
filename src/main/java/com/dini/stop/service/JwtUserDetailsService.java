package com.dini.stop.service;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.configuration.JwtTokenUtil;
import com.dini.stop.dao.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserDao userDao;


    @Autowired
    public JwtUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean userBean = userDao.getUserByUserName(username);
        if (userBean != null) {
            return new User(userBean.getEmail(), userBean.getEncodedMotDePasse(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
