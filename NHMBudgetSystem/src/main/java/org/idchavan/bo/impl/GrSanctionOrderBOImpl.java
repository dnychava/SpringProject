package org.idchavan.bo.impl;

import java.util.List;

import org.idchavan.bo.GrSanctionOrderBO;
import org.idchavan.dao.GrSanctionOrderDAO;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.GrSanctionOrderEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("grSanctionOrderBO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GrSanctionOrderBOImpl implements GrSanctionOrderBO{
	
	@Autowired
	private GrSanctionOrderDAO grSanOrdDAO;
	
	public GrSanctionOrderBOImpl() {
		System.out.println("Constructor Called GrSanctionOrderBOImpl");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveOrUpdate(GrSanctionOrderEntity sanctionOrder) {
		grSanOrdDAO.saveOrUpdate(sanctionOrder);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public GrSanctionOrderEntity updateSanctionOrder(GrSanctionOrderEntity sanctionOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteGrSanctionOrder(GrSanctionOrderEntity sanctionOrder) {
		grSanOrdDAO.deleteGrSanctionOrder(sanctionOrder);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<GrSanctionOrderEntity> getAllGrSanctionOrders() {
		
		return grSanOrdDAO.getAllGrSanctionOrders();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public GrSanctionOrderEntity findByRid(String rid) {
		
		return grSanOrdDAO.findByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<GrSanctionOrderEntity> getGrSanctionOrdersReport(SanctionOrderFilterDTO sanOrdFilterDTO) {
		
		return grSanOrdDAO.getGrSanctionOrdersReport(sanOrdFilterDTO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public DocumentEntity findDocumentByRid(String rid) {
		return grSanOrdDAO.findDocumentByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteGrSanctionOrderDetails(GrSanctionOrderDetailEntity sanctionOrderDetailEntity) {
		grSanOrdDAO.deleteGrSanctionOrderDetails(sanctionOrderDetailEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<GrSanctionOrderDetailEntity> getAllGrSanctionOrderDetails(List<String> rids) {
		return grSanOrdDAO.getAllGrSanctionOrderDetails(rids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public GrSanctionOrderDetailEntity getGrDetailsUsingGroupBy(String sanOrdDtlRid, String shareType) {
		return grSanOrdDAO.getGrDetailsUsingGroupBy(sanOrdDtlRid, shareType);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<GrSanctionOrderDetailEntity> getAllGrSanctionOrderDetails(OrderDetailFilterDTO detailFilterDTO) {
		return grSanOrdDAO.getAllGrSanctionOrderDetails(detailFilterDTO);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public GrSanctionOrderDetailEntity findGrSanctionOrderDetailByRid(String rid) {
		return grSanOrdDAO.findGrSanctionOrderDetailByRid(rid);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<GrSanctionOrderDetailEntity> getAllGrSanOrdDtlBySanOrdDtlRid(String sanOrdDtlRid) {
		return grSanOrdDAO.getAllGrSanOrdDtlBySanOrdDtlRid(sanOrdDtlRid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<GrSanctionOrderEntity> findGrSanOrdByYear(String year) {
		return grSanOrdDAO.findGrSanOrdByYear(year);
	}
}
