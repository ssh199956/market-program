package cn.smbms.service.bill;

import java.sql.Connection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.pojo.Bill;

@Service("billService")
public class BillServiceImpl implements BillService {

	@Resource
	BillMapper billMapper;

	@Override
	public int getBillCountByProviderId(String providerId) throws Exception {
		return billMapper.getBillCountByProviderId(providerId);
	}

	@Override
	public boolean add(Bill bill) {
		boolean flag = false;
		try {
			if (billMapper.add(bill) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Bill> getBillList(Bill bill) {
		List<Bill> billList = null;
		try {
			billList = billMapper.getBillList(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return billList;
	}

	@Override
	public boolean deleteBillById(String delId) {
		boolean flag = false;
		try {
			if (billMapper.deleteBillById(delId) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Bill getBillById(String id) {
		Bill bill = null;
		try {
			bill = billMapper.getBillById(id);
		} catch (Exception e) {
			e.printStackTrace();
			bill = null;
		}
		return bill;
	}

	@Override
	public boolean modify(Bill bill) {
		boolean flag = false;
		try {
			if (billMapper.modify(bill) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
