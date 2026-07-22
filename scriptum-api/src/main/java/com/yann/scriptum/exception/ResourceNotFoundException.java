package com.yann.scriptum.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " introuvable avec l'id : " + id);
    }
}