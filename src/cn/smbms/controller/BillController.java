package cn.smbms.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.bill.BillServiceImpl;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.Tools;

@Controller
@RequestMapping("/bill")
public class BillController {

	@Resource
	BillService billService;

	@Resource
	ProviderService providerService;

	/**
	 * 异步加载供应商列表
	 * 
	 * @return
	 */
	@RequestMapping("/queryProviderList")
	@ResponseBody
	public String queryProviderList() {
		return JSONArray.toJSONString(providerService.getProviderList("", ""));
	}

	/**
	 * 显示所有/指定条件订单
	 * 
	 * @param model
	 * @param bill
	 * @return
	 * @throws NumberFormatException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/billList")
	public String queryBillList(Model model, Bill bill

	) throws NumberFormatException, UnsupportedEncodingException {
		bill.setProductName(bill.getProductName() == null ? "" : bill
				.getProductName());
		bill.setProviderId(bill.getProviderId() == null ? 0 : bill
				.getProviderId());
		bill.setIsPayment(bill.getIsPayment() == null ? 0 : bill.getIsPayment());
		model.addAttribute("billList", billService.getBillList(bill));
		model.addAttribute("queryProductName", bill.getProductName());
		model.addAttribute("queryProviderId", bill.getProviderId());
		model.addAttribute("queryIsPayment", bill.getIsPayment());
		model.addAttribute("providerList",
				providerService.getProviderList("", ""));
		return "billlist";
	}

	/**
	 * 打开订单添加页面/添加订单
	 * 
	 * @param session
	 * @param bill
	 * @return
	 */
	@RequestMapping("/billAdd")
	public String billAdd(HttpSession session, Bill bill) {
		if (bill.getBillCode() == null) {
			return "billadd";
		}
		bill.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION))
				.getId());
		bill.setCreationDate(new Date());
		if (billService.add(bill)) {
			return "redirect:/bill/billList";
		} else {
			return "billadd";
		}
	}

	/**
	 * 删除指定ID订单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delBill")
	@ResponseBody
	public String delBill(@RequestParam String id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (!StringUtils.isNullOrEmpty(id)) {
			if (billService.deleteBillById(id)) {// 删除成功
				resultMap.put("delResult", "true");
			} else {// 删除失败
				resultMap.put("delResult", "false");
			}
		} else {
			resultMap.put("delResult", "notexit");
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 打开修改信息页面/保存修改信息
	 * 
	 * @param billid
	 * @param model
	 * @param bill
	 * @param session
	 * @return
	 */
	@RequestMapping("/modify")
	public String modify(@RequestParam(required = false) String billid,
			Model model, Bill bill, HttpSession session) {
		if (billid != null) {
			model.addAttribute("bill", billService.getBillById(billid));
			return "billmodify";
		} else {
			bill.setModifyBy(((User) session
					.getAttribute(Constants.USER_SESSION)).getId());
			bill.setModifyDate(new Date());
			if (billService.modify(bill)) {
				return "redirect:/bill/billList";
			} else {
				return "redirect:/bill/modify";
			}
		}
	}

	/**
	 * 查看指定ID订单
	 * 
	 * @param billid
	 * @return
	 */
	@RequestMapping("/view")
	public String view(@RequestParam String billid, Model model) {
		model.addAttribute("bill", billService.getBillById(billid));
		return "billview";
	}

}
