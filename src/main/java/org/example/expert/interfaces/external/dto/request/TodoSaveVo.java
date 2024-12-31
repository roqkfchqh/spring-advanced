package org.example.expert.interfaces.external.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class TodoSaveVo {

    @NotBlank
    String title;

    @NotBlank
    String contents;
}
