package models;

import lombok.Data;

@Data
public class SuccessfulRegistrationResponseModel {
    private String id, token;
}