package ru.testcdek.tasktimetracker.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.testcdek.tasktimetracker.model.AppUser;

@Mapper
public interface UserMapper {

    AppUser findByUsername(@Param("username") String username);

}
