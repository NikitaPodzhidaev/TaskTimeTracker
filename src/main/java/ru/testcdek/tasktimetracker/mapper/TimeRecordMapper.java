package ru.testcdek.tasktimetracker.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.testcdek.tasktimetracker.model.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TimeRecordMapper {

    void insertTimeRecord(TimeRecord timeRecord);

    List<TimeRecord> findTimeRecordsByEmployeeIdAndPeriod(
            @Param("employeeId") Long employeeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime

    );

}
