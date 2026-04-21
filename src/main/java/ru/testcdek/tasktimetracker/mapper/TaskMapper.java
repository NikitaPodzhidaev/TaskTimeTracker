package ru.testcdek.tasktimetracker.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TaskStatus;

@Mapper
public interface TaskMapper {

    void insertTask(Task task);
    Task findTaskById(@Param("id") Long id);
    void updateTaskStatus(@Param("id") Long id, @Param("status") TaskStatus taskStatus);
}
