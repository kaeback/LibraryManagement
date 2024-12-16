package net.datasa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter @Setter @ToString
public class Member {
    @Column(name = "MEMBER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude  // 순환참조 문제 해결
    private List<Loan> loans = new ArrayList<>();
}
