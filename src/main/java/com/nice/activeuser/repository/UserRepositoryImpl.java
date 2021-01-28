package com.nice.activeuser.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.activeuser.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final ObjectMapper objectMapper;

    @Override
    public Map<LocalDate, Set<String>> loadUsersGroupByInsertDate(String filePath) {

        Map<LocalDate, Set<String>> uniqueUsersGroupbyInsertDate = null;
        //Load users data by external file(json)
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        try{
                List<User> users = objectMapper.readValue(new File(file.getAbsolutePath()), new TypeReference<List<User>>(){});
                // group by date, uses 'mapping' to convert List<User> to Set<String>
                uniqueUsersGroupbyInsertDate =
                        users.stream().collect(
                                Collectors.groupingBy(User::getInsertDate,
                                        Collectors.mapping(User::getUserName, Collectors.toSet())
                                )
                        );
                return uniqueUsersGroupbyInsertDate;
        }catch (IOException ex){
                log.info(ex.getMessage());
        }

        return uniqueUsersGroupbyInsertDate;
    }


}
