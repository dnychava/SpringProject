package org.idchavan.bo;


import java.util.List;

import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.GrSanctionOrderEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;

public interface GrSanctionOrderBO {

	public void saveOrUpdate(GrSanctionOrderEntity grSanctionOrder);

	public GrSanctionOrderEntity updateSanctionOrder(GrSanctionOrderEntity sanctionOrder);

	public void deleteGrSanctionOrder(GrSanctionOrderEntity sanctionOrder);

	public List<GrSanctionOrderEntity> getAllGrSanctionOrders();

	public GrSanctionOrderEntity findByRid(String rid);
	
	public List<GrSanctionOrderEntity> getGrSanctionOrdersReport(SanctionOrderFilterDTO sanOrdFilterDTO);
	
	public DocumentEntity findDocumentByRid(String rid);
	
	public void deleteGrSanctionOrderDetails(GrSanctionOrderDetailEntity sanctionOrderDetailEntity);

	public List<GrSanctionOrderDetailEntity> getAllGrSanctionOrderDetails(List<String> rids);
	
	public List<GrSanctionOrderDetailEntity> getAllGrSanOrdDtlBySanOrdDtlRid(String sanOrdDtlRid);
	
	/**
	 * This method add the group by clause of <code>sanOrdDtlRid</code> <br>
	 * for sum of the values of <code>orderAmt</code> and <code>orderAmtInLakh</code>
	 * @param sanOrdDtlRid of SanctionOrderDetailEntity object
	 * @param shareType of share type
	 * @return 
	 * The <code>GrSanctionOrderDetailEntity</code> with four fields
	 * <ol>
	 * 		<li>completed</li>
	 * 		<li>orderAmt</li>
	 * 		<li>orderAmtInLakh</li>
	 * 		<li>sanOrdDtlRid</li>
	 * </ol>
	 */
	public GrSanctionOrderDetailEntity getGrDetailsUsingGroupBy(String sanOrdDtlRid, String shareType);
	
	public List<GrSanctionOrderDetailEntity> getAllGrSanctionOrderDetails(OrderDetailFilterDTO detailFilterDTO);
	
	public GrSanctionOrderDetailEntity findGrSanctionOrderDetailByRid(String rid);
	
	public List<GrSanctionOrderEntity> findGrSanOrdByYear(String year);

}
