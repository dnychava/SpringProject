package org.idchavan.bo.impl;

import java.util.List;

import org.idchavan.bo.BankBO;
import org.idchavan.dao.BankDAO;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.BankEntity;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bankBO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BankBOImpl implements BankBO{
	
	@Autowired
	private BankDAO bankDAO;
	
	public BankBOImpl() {
		System.out.println("Constructor Called BankBOImpl");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveOrUpdate(BankEntity bank) {
		bankDAO.saveOrUpdate(bank);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public BankEntity updateBank(BankEntity bank) {
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteBank(BankEntity bank) {
		bankDAO.deleteBank(bank);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BankEntity> getAllBanks() {
		
		return bankDAO.getAllBanks();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public BankEntity findByRid(String rid) {
		return bankDAO.findByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BankEntity> getBankReport(SanctionOrderFilterDTO sanOrdFilterDTO) {
		
		return bankDAO.getBankReport(sanOrdFilterDTO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public DocumentEntity findDocumentByRid(String rid) {
		return bankDAO.findDocumentByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteBankDetail(BankDetailEntity bankDetail) {
		bankDAO.deleteBankDetail(bankDetail);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BankDetailEntity> getAllBankDetails(List<String> rids) {
		return bankDAO.getAllBankDetails(rids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public BankDetailEntity getAllBankDetails(String grSanOrdDtlRid, String shareType) {
		return bankDAO.getAllBankDetails(grSanOrdDtlRid, shareType);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BankDetailEntity> getBankDetailsOfGrsanOrdDtlRidUsingGroupBy(String grSanOrdDtlRid, PendingOrderFilterDTO pendingOrderFilterDTO) {
		return bankDAO.getBankDetailsOfGrsanOrdDtlRidUsingGroupBy(grSanOrdDtlRid, pendingOrderFilterDTO);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public BankDetailEntity findByBankDtlRid(String rid) {
		return bankDAO.findByBankDtlRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BankDetailEntity> findBankDtlByGrSanOrdDtlRid(String grSanOrdDtlRid) {
		return bankDAO.findBankDtlByGrSanOrdDtlRid(grSanOrdDtlRid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public BankDetailEntity getBankDetailsUsingGroupByOfGrsanOrdDtlRid(String grSanOrdDtlRid, String shareType) {
		return bankDAO.getBankDetailsUsingGroupByOfGrsanOrdDtlRid(grSanOrdDtlRid, shareType);
	}
	
}
