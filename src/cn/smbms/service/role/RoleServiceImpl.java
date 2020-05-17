package cn.smbms.service.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.role.RoleMapper;
import cn.smbms.pojo.Role;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	RoleMapper roleMapper;

	@Override
	public List<Role> getRoleList() {
		List<Role> roleList = null;
		try {
			roleList = roleMapper.getRoleList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}

}
