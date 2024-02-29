package com.picpay.simplificado.entities;

import com.picpay.simplificado.dto.UserDTO;
import com.picpay.simplificado.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, password;
    @Column(unique = true)

    private String email;
    @Column(unique = true)
    private String cpf;
    private BigDecimal bankAccount;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.bankAccount = userDTO.getBankAccount();
        this.cpf = userDTO.getCpf();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }
}
