package top.reviewx.core.base;


import top.reviewx.core.utils.DateUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public interface BaseCustomMapper {
    default Timestamp map(Instant instant) {
        return instant == null ? null : Timestamp.from(instant);
    }

    default Date map(LocalDateTime localDateTime) {
        return DateUtil.convertToUtilDate(localDateTime);
    }

    default Date map(LocalDate localDate) {
        return DateUtil.convertToUtilDate(localDate);
    }

    default Date map(LocalTime localTime) {
        return DateUtil.convertToUtilDate(localTime);
    }
}
