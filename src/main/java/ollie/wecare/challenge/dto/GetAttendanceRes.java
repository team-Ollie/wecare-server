package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetAttendanceRes {
    private DateDto attendanceDate;

    private static GetAttendanceRes fromAttendance(ChallengeAttendance attendance) {
        return GetAttendanceRes.builder().
            attendanceDate(convertToDateDto(attendance.getAttendanceDate()));
    }

    private static DateDto convertToDateDto(LocalDateTime dueDate) {
        if (dueDate == null) return null;
        return new DateDto(
                dueDate.getYear(),
                dueDate.getMonthValue(),
                dueDate.getDayOfMonth());
    }

    
}
