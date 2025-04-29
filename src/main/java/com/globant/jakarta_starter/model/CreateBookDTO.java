package com.globant.jakarta_starter.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {
    @NotBlank(message = "Book title cannot be empty")
    @Size(max = 256, message = "Book title cannot exceed 256 characters")
    private String title;

    @NotNull
    private UUID authorId;
}
