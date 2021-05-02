package com.hackathon.woofy.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hackathon.woofy.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetails loadUserByUsername(String userPk) {
        return userRepo.findById(Long.valueOf(userPk)).orElseThrow(null);
    }
}
