package com.avalanchelabs.app.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    private final static long serialVersionUID = 7702L;
    private String cityName;
    private String streetName;
    private int postalCode;
}
