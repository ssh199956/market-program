package cn.smbms.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.role.RoleService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.Tools;

@Controller
@RequestMapping("/provider")
public class ProviderController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	ProviderService providerService;

	/**
	 * 查看指定供应商
	 * 
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{pid}")
	public String providerView(@PathVariable String pid, Model model) {
		Provider provider = providerService.getProviderById(pid);
		model.addAttribute(provider);
		return "providerview";
	}

	@RequestMapping(value = "/providermodifysave.html", method = RequestMethod.POST)
	public String modifyProviderSave(Provider provider, HttpSession session) throws Exception {
		provider.setCreatedBy(((User) session
				.getAttribute(Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());
		provider.setModifyBy(((User) session
				.getAttribute(Constants.USER_SESSION)).getId());
		provider.setModifyDate(new Date());
		if (providerService.modify(provider)) {
			return "redirect:/provider/providerList.html";
		}
		return "providerlist";
	}

	/**
	 * 根据ID获取供应商信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/providermodify.html", method = RequestMethod.GET)
	public String getProviderById(@RequestParam String pid, Model model) {
		Provider provider = providerService.getProviderById(pid);
		model.addAttribute(provider);
		return "providermodify";
	}

	/**
	 * 打开添加供应商页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addProvider.html", method = RequestMethod.GET)
	public String ProviderAdd(@ModelAttribute("provider") Provider provider) {
		return "provideradd";
	}

	@RequestMapping(value = "/addProvider.html", method = RequestMethod.POST)
	public String addProviderSave(@Valid Provider provider,
			BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "provideradd";
		}
		provider.setCreatedBy(((User) session
				.getAttribute(Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());
		provider.setModifyBy(((User) session
				.getAttribute(Constants.USER_SESSION)).getId());
		provider.setModifyDate(new Date());
		if (providerService.add(provider)) {
			return "redirect:/provider/providerList.html";
		}
		return "provideradd";
	}

	/**
	 * 查询供应商信息
	 * 
	 * @param model
	 * @param queryProCode
	 * @param queryProName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/providerList.html")
	public String getProviderList(Model model,
			@RequestParam(required = false) String queryProCode,
			@RequestParam(required = false) String queryProName)
			throws UnsupportedEncodingException {
		queryProCode = Tools.toUTF_8(queryProCode == null ? "" : queryProCode);
		queryProName = Tools.toUTF_8(queryProName == null ? "" : queryProName);
		model.addAttribute("providerList",
				providerService.getProviderList(queryProName, queryProCode));
		model.addAttribute("queryProCode", queryProCode);
		model.addAttribute("queryProName", queryProName);
		return "providerlist";
	}
}
