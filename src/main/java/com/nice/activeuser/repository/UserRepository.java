package com.nice.activeuser.repository;

import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Repository
public interface UserRepository {
    //load all user from json file and group them by date field
    Map<LocalDate, Set<String>> loadUsersGroupByInsertDate(String filePath);
}
