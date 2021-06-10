package me.m92.tatbook_web;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

@MappedSuperclass
public abstract class DomainEntity {

    @Column(name = "create_date")
    private LocalDateTime createTime;

    @Column(name= "modify_date")
    private LocalDateTime modifyTime;

    public DomainEntity() {
        createTime = LocalDateTime.now();
        modifyTime = LocalDateTime.now();
    }

    public abstract Long getId();

    public static void main(String...args) {
        String aLetterBinary = "a";
        for(byte b : aLetterBinary.getBytes()) {
            System.out.print(b);
        }
    }
}
