package org.dailycodework.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
//this class is created to return the data to the frontend
public class ApiResponse {
    private String message;
    private Object data;
}
