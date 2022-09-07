package com.example.task6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamDto {

    private String selectedRegion;
    private String errorPerRecord;
    private String seedNumber;
    private int dataLength;

    public ParamDto(String selectedRegion, String errorPerRecord, String seedNumber) {
        this.selectedRegion = selectedRegion;
        this.errorPerRecord = errorPerRecord;
        this.seedNumber = seedNumber;
    }
}
