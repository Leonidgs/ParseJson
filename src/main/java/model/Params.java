package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Params {
    private String orgName;
    private String address;
    private String dateOfFoundation;
    private String phoneNumber;
    private String INN;
    private String OGRN;
    private List<Securities> securities;
}
