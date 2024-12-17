package net.datasa;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
// 불변객체(Immutable Object)로 만들기 위해 Setter 제거
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
