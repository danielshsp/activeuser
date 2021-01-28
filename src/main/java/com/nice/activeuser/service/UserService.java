package com.nice.activeuser.service;

import com.nice.activeuser.dto.User;
import com.nice.activeuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    @Value("${file.path}")
    private String filePath;

    //If noCache is false the cache will return the data,
    // else there is a manual update to the cache and the data will return from sub system(file)
    @Cacheable(cacheNames = "users", condition = "!#noCache")
    public List<User> findAll(Boolean noCache){
        log.info("Retrieving users from file");
        Map<LocalDate, Set<String>> userMap = userRepository.loadUsersGroupByInsertDate(filePath);
        return mapDynamicObjToListUserDTO(userMap);
    }
    //Clean allEntries from the cache
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void clearCache() {
    }
    //find all active users per daily
    @Cacheable(cacheNames = "users", key="#insertDate")
    public Optional<User> findUserByInsertDate(LocalDate insertDate){
        log.info("Retrieving users from file");
        Map<LocalDate, Set<String>> userMap = userRepository.loadUsersGroupByInsertDate(filePath);
        return mapDynamicObjToUserDTO(userMap, insertDate);
    }


    public List<User> mapDynamicObjToListUserDTO(Map<LocalDate, Set<String>> userMap){
        LocalDate[] keys = userMap.keySet().toArray(new LocalDate[userMap.keySet().size()]);
        List<User> userList = new ArrayList<User>();
        for (LocalDate key : keys) {
            List<String> users = new ArrayList<>(userMap.get(key));
            User user = new User(key, users);
            System.out.println(key);
            userList.add(user);
        }
        return userList;
    }

    public Optional<User> mapDynamicObjToUserDTO(Map<LocalDate, Set<String>> userMap, LocalDate insertDate){
        List<User> userList = new ArrayList<User>();
        List<String> users = new ArrayList<>(userMap.get(insertDate));
        User user = new User(insertDate, users);
        System.out.println(user);
        return Optional.of(user);
    }
}
