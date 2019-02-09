package org.idchavan.bo.impl;

import java.util.List;

import org.idchavan.bo.SanctionOrderBO;
import org.idchavan.dao.SanctionOrderDAO;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("sanctionOrderBO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SanctionOrderBOImpl implements SanctionOrderBO{
	
	@Autowired
	private SanctionOrderDAO sanctionOrderDAO;
	
	public SanctionOrderBOImpl() {
		System.out.println("Constructor Called SanctionOrderBOImpl");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveOrUpdate(SanctionOrderEntity sanctionOrder){
		sanctionOrderDAO.saveOrUpdate(sanctionOrder);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public SanctionOrderEntity updateSanctionOrder(SanctionOrderEntity sanctionOrder) {
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteSanctionOrder(SanctionOrderEntity sanctionOrder) {
		sanctionOrderDAO.deleteSanctionOrder(sanctionOrder);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderEntity> getAllSanctionOrders() {
		
		return sanctionOrderDAO.getAllSanctionOrders();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public SanctionOrderEntity findByRid(String rid) {
		return sanctionOrderDAO.findByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderEntity> getSanctionOrdersReport(SanctionOrderFilterDTO sanOrdFilterDTO) {
		
		return sanctionOrderDAO.getSanctionOrdersReport(sanOrdFilterDTO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public DocumentEntity findDocumentByRid(String rid) {
		return sanctionOrderDAO.findDocumentByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteSanctionOrderDetails(SanctionOrderDetailEntity sanctionOrderDetailEntity) {
		sanctionOrderDAO.deleteSanctionOrderDetails(sanctionOrderDetailEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderDetailEntity> getAllSanctionOrderDetails(List<String> rids) {
		return sanctionOrderDAO.getAllSanctionOrderDetails(rids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderDetailEntity> getAllSanctionOrderDetails(OrderDetailFilterDTO detailFilterDTO) {
		return sanctionOrderDAO.getAllSanctionOrderDetails(detailFilterDTO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public SanctionOrderDetailEntity findSanctionOrderDetailByRid(String rid) {
		return sanctionOrderDAO.findSanctionOrderDetailByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderEntity> getSactionOrdGroubyProgGroupName(PendingOrderFilterDTO pendingOrderFilterDTO) {
		return sanctionOrderDAO.getSactionOrdGroubyProgGroupName(pendingOrderFilterDTO);	
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderDetailEntity> getSanOrdDtlOfGroupByProgramName(String sanOrdRid,
			PendingOrderFilterDTO pendingOrderFilterDTO) {
		return sanctionOrderDAO.getSanOrdDtlOfGroupByProgramName(sanOrdRid, pendingOrderFilterDTO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<SanctionOrderEntity> findSanOrdByYear(String year) {
		return sanctionOrderDAO.findSanOrdByYear(year);
	}
		
}
