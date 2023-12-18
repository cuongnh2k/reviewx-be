package top.reviewx.rest.admin.objectv1.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.enums.ObjectV1StatusEnum;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectV1AdminReq {
    @NotNull
    private ObjectV1StatusEnum status;
    private String note;
}
