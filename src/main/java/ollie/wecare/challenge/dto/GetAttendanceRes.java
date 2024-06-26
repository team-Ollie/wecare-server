package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.challenge.entity.ChallengeAttendance;
import ollie.wecare.program.dto.DateDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetAttendanceRes {
    private DateDto attendanceDate;

     public static GetAttendanceRes fromAttendance(ChallengeAttendance attendance) {
        return GetAttendanceRes.builder().
            attendanceDate(convertToDateDto(attendance.getAttendanceDate())).build();
    }

    private static DateDto convertToDateDto(LocalDateTime dueDate) {
        if (dueDate == null) return null;
        return new DateDto(
                dueDate.getYear(),
                dueDate.getMonthValue(),
                dueDate.getDayOfMonth());
    }

    
}
