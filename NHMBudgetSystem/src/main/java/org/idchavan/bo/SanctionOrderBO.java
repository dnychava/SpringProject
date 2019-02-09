package org.idchavan.bo;


import java.util.List;

import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface SanctionOrderBO {

	public void saveOrUpdate(SanctionOrderEntity sanctionOrder) ;

	public SanctionOrderEntity updateSanctionOrder(SanctionOrderEntity sanctionOrder);

	public void deleteSanctionOrder(SanctionOrderEntity sanctionOrder);

	public List<SanctionOrderEntity> getAllSanctionOrders();

	public SanctionOrderEntity findByRid(String rid);
	
	public List<SanctionOrderEntity> getSanctionOrdersReport(SanctionOrderFilterDTO sanOrdFilterDTO);
	
	public DocumentEntity findDocumentByRid(String rid);
	
	public void deleteSanctionOrderDetails(SanctionOrderDetailEntity sanctionOrderDetailEntity);

	public List<SanctionOrderDetailEntity> getAllSanctionOrderDetails(List<String> rids);
	
	public List<SanctionOrderDetailEntity> getAllSanctionOrderDetails(OrderDetailFilterDTO detailFilterDTO);
	
	public SanctionOrderDetailEntity findSanctionOrderDetailByRid(String rid);
	
	public List<SanctionOrderEntity> getSactionOrdGroubyProgGroupName(PendingOrderFilterDTO  pendingOrderFilterDTO);
	
	public List<SanctionOrderDetailEntity> getSanOrdDtlOfGroupByProgramName(String sanOrdRid,
			PendingOrderFilterDTO pendingOrderFilterDTO);
	
	public List<SanctionOrderEntity> findSanOrdByYear(String year);

}
