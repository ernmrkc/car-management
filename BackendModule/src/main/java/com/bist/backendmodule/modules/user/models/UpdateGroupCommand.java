package com.bist.backendmodule.modules.user.models;

import lombok.Data;

import java.util.List;

/**
 * Command class for updating user groups.
 */
@Data
public class UpdateGroupCommand {
    private Long userId;
    private List<Long> groupIds;
}
