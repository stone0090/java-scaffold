package com.example.demo.service.service.bo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionBO {
    private String id;
    private String permissionCode;
    private String permissionName;
}
