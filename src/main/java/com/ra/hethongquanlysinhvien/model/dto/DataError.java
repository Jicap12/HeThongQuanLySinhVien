package com.ra.hethongquanlysinhvien.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataError <T>{
    private T message;
    private int statusCode;
}
