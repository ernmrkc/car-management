package com.bist.backendmodule.modules.role.models;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for Role.
 */
@Data
public class RoleCreateDTO {
    private String name;
    private List<Long> permissionIds;
}
