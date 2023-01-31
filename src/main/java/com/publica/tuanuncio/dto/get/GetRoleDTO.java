package com.publica.tuanuncio.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoleDTO {

    private Long idRole;
    private String roleAuthority;
}
