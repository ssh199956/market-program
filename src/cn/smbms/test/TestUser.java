package cn.smbms.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.role.RoleMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;

public class TestUser {
	ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext-jdbc.xml");
	BillService billService = (BillService) context.getBean("billService");
	RoleService roleService = (RoleService) context.getBean("roleService");
	ProviderService providerService = (ProviderService) context
			.getBean("providerService");

	// UserService userService = (UserService) context.getBean("userService");

	@Test
	public void test() throws Exception {
		System.out.println(billService.getBillCountByProviderId("1"));

		// List<Provider> poList = providerService.getProviderList("", "");
		// for (Provider provider : poList) {
		// System.out.println(provider.getProCode());
		// }

		// List<Bill> bills = billService.getBillList(new Bill("", 0, 0));
		// for (Bill bill : bills) {
		//
		// System.out.println(bill.getProductName());
		// }

		// System.out.println(userService.getUserCount("", 1));
		// System.out.println(userService.deleteUserById(15));

		// List<Role> roles=roleService.getRoleList();
		// for (Role role : roles) {
		// System.out.println(role.getRoleName());
		// }

	}

}
