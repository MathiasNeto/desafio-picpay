package com.picpay.simplificado.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String name, password;
    @NotBlank(message = "Campo requerido")
    @Email(message = "Email não válido")
    private String email;
    @CPF(message = "CPF inválido")
    @NotBlank(message = "Campo requerido")
    private String cpf;
    private BigDecimal bankAccount;
    @JsonIgnore
    private Long id;
    private UserType userType;

    public UserDTO(User user) {
        this.id = user.getId();
        this.cpf = user.getCpf();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.bankAccount = user.getBankAccount();
        this.userType = user.getUserType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


