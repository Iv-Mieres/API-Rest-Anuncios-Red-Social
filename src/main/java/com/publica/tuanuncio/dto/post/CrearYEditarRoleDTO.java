package com.publica.tuanuncio.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearYEditarRoleDTO {

    private Long idRole;
    @NotNull(message = "No puede estar vacío.")
    @Size(min = 4, max = 20, message = "Debe contener entre 4 y 20 caracteres.")
    private String roleAuthority;

}
