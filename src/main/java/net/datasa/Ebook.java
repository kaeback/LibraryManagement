package net.datasa;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Ebook extends Book {
    private String downloadUrl;
    private Long size;
}