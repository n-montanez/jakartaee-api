package com.globant.jakarta_starter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorDTO {
    @NotBlank(message = "Author name cannot be empty")
    @Size(max = 128, message = "Author name cannot exceed 128 characters")
    private String name;
}
