package DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"id"})
@AllArgsConstructor
@Builder(builderMethodName = "builder")
@NoArgsConstructor
public class Banking {
    private Long id;
    private String username;
    private String password;
    private String name;
    private int balance;
    private String transation;
    private LocalDateTime date;
    private String accountNumber;


}
