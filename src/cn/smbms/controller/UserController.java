package cn.smbms.controller;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@RequestMapping("/syserror.html")
	public String login() {
		return "login";
	}

	/**
	 * 登录
	 * 
	 * @param userCode
	 * @param userPassword
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String doLogin(@RequestParam String userCode,
			@RequestParam String userPassword, HttpSession session,
			HttpServletRequest request) {
		User user = userService.login(userCode, userPassword);
		if (user != null && user.getUserPassword() != null) {
			session.setAttribute(Constants.USER_SESSION, user);
			return "frame";
		} else if (user != null && user.getUserPassword() == null) {
			request.setAttribute("error", "密码输入错误!");
			return "login";
		} else {
			request.setAttribute("error", "用户名不存在!");
			return "login";
		}
	}

	/**
	 * 打开添加用户页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAdd.html", method = RequestMethod.GET)
	public String addUser(@ModelAttribute("user") User user, Model model)
			throws Exception {
		model.addAttribute("roleLists", roleService.getRoleList());
		return "useradd";
	}

	/**
	 * 异步加载用户角色---->用户添加页面
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRoleList")
	@ResponseBody
	public String getrolelist() throws Exception {
		List<Role> rolelist = roleService.getRoleList();
		System.out.println("JSONArray.toJSONString(rolelist)="
				+ JSONArray.toJSONString(rolelist));
		return JSONArray.toJSONString(rolelist);
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @param session
	 * @return
	 * 
	 */
	// @Valid属性有效验证
	@RequestMapping(value = "/userAdd.html", method = RequestMethod.POST)
	public String addUserSave(
			@Valid User user,
			BindingResult bindingResult,
			HttpSession session,
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "files", required = false) MultipartFile[] files) {
		System.out.println("/userAdd.html=" + user.getUserName());

		String idPicPath = null;// 个人照片文件路径
		String workPicPath = null;// 工作证照片路径
		String errorInfo = null;
		int i = 1;

		if (files.length > 0) {
			for (MultipartFile file : files) {
				if (i == 1) {
					errorInfo = "uploadFileError";
				} else {
					errorInfo = "uploadWpError";
				}

				String path = request.getSession().getServletContext()
						.getRealPath(File.separator + "/Photo");// 系统路径
				String oldFileName = file.getOriginalFilename();// 源文件名
				String prefix = FilenameUtils.getExtension(oldFileName);// 文件名后缀

				if (file.getSize() > 5000000) {// 上传文件不得大于5M
					request.setAttribute(errorInfo, "*上传文件大小不得超过5M");
					return "useradd";
				} else if (prefix.equalsIgnoreCase("jpg")
						|| prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("jpeg")
						|| prefix.equalsIgnoreCase("pneg")) { // 上传图片格式正确

					String fileName = System.currentTimeMillis()
							+ RandomUtils.nextInt(1000000) + "_Personal.jpg";
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {// 路径不存在
						targetFile.mkdirs();
					}
					// 保存
					try {
						file.transferTo(targetFile);// 上传到服务器
					} catch (IllegalStateException e) {
						e.printStackTrace();
						request.setAttribute(errorInfo, "*上传失败!");
						return "useradd";
					} catch (IOException e) {
						e.printStackTrace();
						request.setAttribute(errorInfo, "*上传失败!");
						return "useradd";
					}
					if (i == 1) {
						idPicPath = path + File.separator + fileName;
						logger.info("个人照路径" + idPicPath);
						user.setIdPicPath(idPicPath);
					} else {
						logger.info("工作照路径" + idPicPath);
						workPicPath = path + File.separator + fileName;
						user.setWorkPicPath(workPicPath);
					}
					i++;
				} else {
					System.out.println("图片格式不正确!");
					request.setAttribute(errorInfo, "*图片格式不正确!");
					return "useradd";
				}
			}
		}

		if (bindingResult.hasErrors()) {
			return "useradd";
		}
		user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION))
				.getId());
		user.setCreationDate(new Date());
		user.setIdPicPath(idPicPath);
		user.setWorkPicPath(workPicPath);

		if (userService.add(user)) {
			return "redirect:/user/userlist.html";
		} else {
			// model.addAttribute("roleLists", roleService.getRoleList());
			return "useradd";
		}
	}

	/**
	 * 根据ID删除用户
	 * 
	 * @return
	 */
	@RequestMapping("/delUserById")
	@ResponseBody
	public Object delUserById(@RequestParam String uid) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		int id = Integer.parseInt(uid);
		if (id <= 0) {
			resultMap.put("delResult", "notexist");
		} else {
			if (userService.deleteUserById(id)) {
				resultMap.put("delResult", "true");
			} else {
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 注销
	 */
	@RequestMapping("/loginOut")
	public String logOut(HttpSession session, HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.USER_SESSION);
		return "login";
	}

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @param queryUserName
	 * @param queryUserRole
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userList")
	public String getUserList(Model model,
			@RequestParam(required = false) String queryname,
			@RequestParam(required = false) String queryUserRole,
			@RequestParam(required = false) String pageIndex) throws Exception {
		int userRole = 0;
		int pageSize = Constants.pageSize;
		int pageNo = 1;
		// 字符编码转换
		queryname = new String(
				(queryname == null ? "" : queryname).getBytes("iso-8859-1"),
				"utf-8");
		if (queryUserRole != null && !queryUserRole.equals("")) {
			userRole = Integer.parseInt(queryUserRole);
		}
		if (pageIndex != null && pageIndex != "") {
			pageNo = Integer.valueOf(pageIndex);
		}

		// 总数量(表)
		int totalCount = userService.getUserCount(queryname, userRole);
		PageSupport pages = new PageSupport(pageNo, pageSize, totalCount);
		// 总页数
		int totalPageCount = pages.getTotalPageCount();
		// 控制首页和尾页
		pageNo = pageNo < 1 ? 1 : pageNo > totalPageCount ? totalPageCount
				: pageNo;
		// 查询符合条件用户
		List<User> userList = userService.getUserList(queryname, userRole,
				pageNo, pageSize);

		model.addAttribute("queryUserName", queryname); // 用户名
		model.addAttribute("queryUserRole", userRole); // 用户角色
		List<Role> roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList); // 用户集合

		model.addAttribute("userList", userList);

		model.addAttribute("totalCount", totalCount); // 记录总数
		model.addAttribute("currentPageNo", pageNo); // 当前页码
		model.addAttribute("totalPageCount", totalPageCount); // 总页数

		return "userlist";
	}

	/**
	 * 打开修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/modify")
	public String pwdModify() {
		return "pwdmodify";
	}

	/**
	 * 检查旧密码是否正确
	 * 
	 * @param oldpassword
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPwdUserId")
	@ResponseBody
	public Object getPwdUserId(@RequestParam String oldpassword,
			HttpServletRequest request) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);

		// 密码为空
		if (oldpassword == null) {
			resultMap.put("result", "error");
		} else if (o == null) { // 会话为空
			resultMap.put("result", "sessionerror");
		} else {
			String sessionPwd = ((User) o).getUserPassword();
			if (sessionPwd.equals(oldpassword)) {
				resultMap.put("result", "true");
			} else {
				resultMap.put("result", "false");
			}
		}
		return JSONArray.toJSON(resultMap);
	}

	/**
	 * 保存修改
	 * 
	 * @param newpassword
	 * @param request
	 * @return
	 */
	@RequestMapping("/pwdSave")
	public String pwdSave(@RequestParam String newpassword,
			HttpServletRequest request) {
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		if (o != null && newpassword != "") {
			if (userService.updatePwd(((User) o).getId(), newpassword)) {
				request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功!");
				return "login";
			}
		}
		request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败!");
		return "pwdmodify";

	}

	@RequestMapping(value = "/view")
	@ResponseBody
	public Object view(@RequestParam String id) {
		if (id == null || id == "") {
			return "nodata";
		} else {
			User user = userService.getUserById(id);
			// System.out.println("/view" + JSONArray.toJSONString(user));
			System.out.println("/view" + user);
			return user;
		}
	}

	@RequestMapping("/UCexist")
	@ResponseBody
	public Object userCodeIsExist(@RequestParam String userCode) {
		System.out.println("userCode=" + userCode);
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(userCode)) {
			resultMap.put("userCode", "null");
		} else {
			User user = userService.selectUserCodeExist(userCode);
			if (user != null)
				resultMap.put("userCode", "exist");
			else
				resultMap.put("userCode", "noExist");
		}
		System.out.println(JSONArray.toJSONString(resultMap));
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/usermodifysave.html", method = RequestMethod.POST)
	public String modifyUserSave(User user, HttpSession session) {
		user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION))
				.getId());
		user.setCreationDate(new Date());
		user.setModifyBy(((User) session.getAttribute(Constants.USER_SESSION))
				.getId());
		user.setModifyDate(new Date());
		if (userService.modify(user)) {
			return "redirect:/user/userList";
		}
		return "usermodify";
	}

	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/usermodify.html", method = RequestMethod.GET)
	public String getUserById(@RequestParam String uid, Model model) {
		logger.info("UserController----------->getUserById()");
		User user = userService.getUserById(uid);
		model.addAttribute(user);
		return "usermodify";
	}

	/**
	 * 登录失败
	 * 
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	@RequestMapping("exlogin.html")
	public String exLogin(@RequestParam String userCode,
			@RequestParam String userPassword) {
		User user = userService.login(userCode, userPassword);
		if (user == null) {// 登录失败
			throw new RuntimeException("用户名或密码不正确");
		}
		return "redirect:/user/main.html";
	}

}
