package cn.smbms.service.provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Provider;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

	@Resource
	BillMapper billMapper;

	@Resource
	ProviderMapper providerMapper;

	@Override
	public boolean add(Provider provider) {
		boolean flag = false;
		try {
			if (providerMapper.add(provider) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Provider> getProviderList(String proName, String proCode) {
		List<Provider> providerList = null;
		try {
			providerList = providerMapper.getProviderList(proName, proCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return providerList;
	}

	/**
	 * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除 返回值billCount 1> billCount == 0 删除---1 成功 （0） 2 不成功
	 * （-1） 2> billCount > 0 不能删除 查询成功（0）查询不成功（-1）
	 * 
	 * ---判断 如果billCount = -1 失败 若billCount >= 0 成功
	 */
	@Override
	public int deleteProviderById(String delId) {
		Connection connection = null;
		int billCount = -1;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);
			billCount = billMapper.getBillCountByProviderId(delId);
			if (billCount == 0) {
				providerMapper.deleteProviderById(delId);
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			billCount = -1;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return billCount;
	}

	@Override
	public Provider getProviderById(String id) {
		Provider provider = null;
		try {
			provider = providerMapper.getProviderById(id);
		} catch (Exception e) {
			e.printStackTrace();
			provider = null;
		}
		return provider;
	}

	@Override
	public boolean modify(Provider provider) throws Exception {
		return providerMapper.modify(provider) > 0 ? true : false;
	}

}
