package com.bist.backendmodule.modules.role.models;

import lombok.Data;

import java.util.List;

/**
 * Command class for updating permissions of role.
 */
@Data
public class UpdatePermissionsCommand {
    private Long roleId;
    private List<Long> permissionIds;
}
