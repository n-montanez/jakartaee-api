package com.globant.jakarta_starter.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, UUID authorId) {
        super(entity + " with ID " + authorId + " not found.");
    }

}
