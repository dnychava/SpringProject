package org.idchavan.bo;


import java.util.List;

import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.BankEntity;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;

public interface BankBO {

	public void saveOrUpdate(BankEntity bank);

	public BankEntity updateBank(BankEntity bank);

	public void deleteBank(BankEntity bank);

	public List<BankEntity> getAllBanks();

	public BankEntity findByRid(String rid);
	
	public BankDetailEntity findByBankDtlRid(String rid);
	
	public List<BankDetailEntity> findBankDtlByGrSanOrdDtlRid(String grSanOrdDtlRid);
	
	public List<BankEntity> getBankReport(SanctionOrderFilterDTO sanOrdFilterDTO);
	
	public DocumentEntity findDocumentByRid(String rid);
	
	public void deleteBankDetail(BankDetailEntity bankDetail);

	public List<BankDetailEntity> getAllBankDetails(List<String> rids);
	
	public BankDetailEntity getAllBankDetails(String grSanOrdDtlRid, String shareType);
	
	public List<BankDetailEntity> getBankDetailsOfGrsanOrdDtlRidUsingGroupBy(String grSanOrdDtlRid, PendingOrderFilterDTO pendingOrderFilterDTO);
	
	public BankDetailEntity getBankDetailsUsingGroupByOfGrsanOrdDtlRid(String grSanOrdDtlRid, String shareType);

}
