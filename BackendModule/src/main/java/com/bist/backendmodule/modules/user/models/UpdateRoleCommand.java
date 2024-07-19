package com.bist.backendmodule.modules.user.models;

import lombok.Data;

import java.util.List;

/**
 * Command class for updating user roles.
 */
@Data
public class UpdateRoleCommand {
    private Long userId;
    private List<Long> roleIds;
}
