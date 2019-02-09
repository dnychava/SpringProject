package org.idchavan.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.idchavan.dao.AbstractDAO;
import org.idchavan.dao.BankDAO;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.BankEntity;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.stereotype.Repository;

@Repository("bankDAO")
public class BankDAOImpl extends AbstractDAO implements BankDAO {

	public BankDAOImpl() {
		System.out.println(" Constructor Called BankDAOImpl");
	}

	@Override
	public void saveOrUpdate(BankEntity bank) {
		super.saveOrUpdate(bank);
	}

	@Override
	public BankEntity updateBank(BankEntity bank) {
		getSession().update(bank);
		return bank;
	}

	@Override
	public void deleteBank(BankEntity bank) {
		super.delete(bank);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankEntity> getAllBanks() {
		Criteria criteria = getSession().createCriteria(BankEntity.class);
		criteria.addOrder(Order.desc("createdDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public BankEntity findByRid(String rid) {
		Criteria criteria = getSession().createCriteria(BankEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (BankEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankEntity> getBankReport(SanctionOrderFilterDTO sanOrdFilterDTO) {

		return null;
		/*
		 * StringBuilder hqlQuery = new
		 * StringBuilder("select {s.*}, {d.*} from SAN_ORD s, SAN_ORD_DTL d where s.SAN_ORD_RID=d.SAN_ORD_DTL_SAN_ORD_RID "
		 * ); if( !"All".equalsIgnoreCase(sanOrdFilterDTO.getYear() ) ){
		 * hqlQuery.append(" and s.SAN_ORD_YEAR in( :sanYear ) "); } if(
		 * !"All".equalsIgnoreCase(sanOrdFilterDTO.getPrograms())){
		 * hqlQuery.append(" and d.SAN_ORD_DTL_PROGRAM_NAME in( :sanProgram ) "); } if(
		 * !"All".equalsIgnoreCase(sanOrdFilterDTO.getCategories())){
		 * hqlQuery.append(" and d.SAN_ORD_DTL_CATEGORY in( :sanCategory ) "); }
		 * SQLQuery nativeQuery = getSession().createSQLQuery( hqlQuery.toString());
		 * nativeQuery.addEntity("s",SanctionOrderEntity.class);
		 * nativeQuery.addJoin("d", "s.sanctionOrderDetails");
		 * 
		 * 
		 * if( !"All".equalsIgnoreCase(sanOrdFilterDTO.getYear() ) ){
		 * nativeQuery.setParameterList("sanYear",
		 * sanOrdFilterDTO.getYear().split(",")); } if(
		 * !"All".equalsIgnoreCase(sanOrdFilterDTO.getPrograms())){
		 * nativeQuery.setParameterList("sanProgram",sanOrdFilterDTO.getPrograms().split
		 * (",")); } if( !"All".equalsIgnoreCase(sanOrdFilterDTO.getCategories())){
		 * nativeQuery.setParameterList("sanCategory",sanOrdFilterDTO.getCategories().
		 * split(",")); }
		 * 
		 * List<Object[]> rows = nativeQuery.list();
		 * 
		 * List<SanctionOrderEntity> resultList = new ArrayList<SanctionOrderEntity>();
		 * for (Object[] row : rows ) { SanctionOrderEntity san = (SanctionOrderEntity)
		 * row[0]; SanctionOrderDetailEntity sanDtl = (SanctionOrderDetailEntity)
		 * row[1]; List<SanctionOrderDetailEntity> sanDtlList = new
		 * ArrayList<SanctionOrderDetailEntity>(); sanDtlList.add( sanDtl );
		 * san.setSanctionOrderDetails(sanDtlList); resultList.add(san); }
		 * 
		 * 
		 * return resultList;
		 */

		/*
		 * Criteria sanOrdCrit = getSession().createCriteria( SanctionOrderEntity.class,
		 * "sanOrd" ); sanOrdCrit.createAlias("sanOrd.sanctionOrderDetails",
		 * "sanOrdDtl"); //sanOrdCrit.setFetchMode("sanctionOrderDetails",
		 * FetchMode.JOIN);
		 * 
		 * if( !"All".equalsIgnoreCase(sanOrdFilterDTO.getPrograms())){
		 * sanOrdCrit.add(Restrictions.in("sanOrdDtl.orderProgramName",
		 * sanOrdFilterDTO.getPrograms().split(","))); } if(
		 * !"All".equalsIgnoreCase(sanOrdFilterDTO.getCategories())){
		 * sanOrdCrit.add(Restrictions.in("sanOrdDtl.orderCategory",
		 * sanOrdFilterDTO.getCategories().split(","))); } if(
		 * !"All".equalsIgnoreCase(sanOrdFilterDTO.getYear() ) ){
		 * sanOrdCrit.add(Restrictions.eq("year", sanOrdFilterDTO.getYear())); }
		 * sanOrdCrit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 * Collection<SanctionOrderEntity> result = new
		 * LinkedHashSet<SanctionOrderEntity>(sanOrdCrit.list());
		 * System.out.println("result["+result+"]"); List<SanctionOrderEntity>
		 * finalResult = new ArrayList<SanctionOrderEntity>();
		 * finalResult.addAll(result);
		 * 
		 * return finalResult;
		 */
	}

	public String getInClauseValue(String arg) {
		StringBuilder inQueryValue = new StringBuilder();
		arg.split(",");
		return inQueryValue.toString();
	}

	@Override
	public DocumentEntity findDocumentByRid(String rid) {
		Criteria criteria = getSession().createCriteria(DocumentEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (DocumentEntity) criteria.uniqueResult();
	}

	@Override
	public void deleteBankDetail(BankDetailEntity bankDetail) {
		delete(bankDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDetailEntity> getAllBankDetails(List<String> rids) {
		Criteria criteria = getSession().createCriteria(BankDetailEntity.class);
		criteria.add(Restrictions.in("rid", rids)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@Override
	public BankDetailEntity getAllBankDetails(String grSanOrdDtlRid, String shareType) {
		Criteria criteria = getSession().createCriteria(BankDetailEntity.class);
		criteria.add(Restrictions.eq("grSanOrdDtlRid", grSanOrdDtlRid));
		criteria.add(Restrictions.eq("share", shareType));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.sum( "amt" ), "amt");
		projList.add(Projections.sum( "amtInLakh" ), "amtInLakh");
		projList.add(Projections.groupProperty( "grSanOrdDtlRid" ), "grSanOrdDtlRid");
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(BankDetailEntity.class));
		List list = criteria.list();
		if(list.size() > 0 ) {
			return (BankDetailEntity) list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDetailEntity> getBankDetailsOfGrsanOrdDtlRidUsingGroupBy(String grSanOrdDtlRid, PendingOrderFilterDTO pendingOrderFilterDTO) {
		Criteria criteria = getSession().createCriteria(BankDetailEntity.class);
		criteria.add(Restrictions.eq("grSanOrdDtlRid", grSanOrdDtlRid));
		if( !StringUtils.equals("All", pendingOrderFilterDTO.getShareType())) {
			criteria.add(Restrictions.eq("share", pendingOrderFilterDTO.getShareType()));
		}
		if (pendingOrderFilterDTO.getFromDate() != null && pendingOrderFilterDTO.getToDate() != null) {
			criteria.add(Restrictions.between("depositDate", pendingOrderFilterDTO.getFromDate(),
					pendingOrderFilterDTO.getToDate()));
		}
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property( "category" ), "category");
		projList.add(Projections.property( "share" ), "share");
		projList.add(Projections.sum( "amt" ), "amt");
		projList.add(Projections.sum( "amtInLakh" ), "amtInLakh");
		projList.add(Projections.groupProperty( "grSanOrdDtlRid" ), "grSanOrdDtlRid");
		projList.add(Projections.groupProperty( "category" ), "category");
		
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(BankDetailEntity.class));
		List<BankDetailEntity> list = criteria.list();
		return list;
	}

	@Override
	public BankDetailEntity findByBankDtlRid(String rid) {
		Criteria criteria = getSession().createCriteria(BankDetailEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (BankDetailEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDetailEntity> findBankDtlByGrSanOrdDtlRid(String grSanOrdDtlRid) {
		Criteria criteria = getSession().createCriteria(BankDetailEntity.class);
		criteria.add(Restrictions.eq("grSanOrdDtlRid", grSanOrdDtlRid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public BankDetailEntity getBankDetailsUsingGroupByOfGrsanOrdDtlRid(String grSanOrdDtlRid, String shareType) {
		Criteria criteria = getSession().createCriteria(BankDetailEntity.class);
		criteria.add(Restrictions.eq("grSanOrdDtlRid", grSanOrdDtlRid));
		criteria.add(Restrictions.eq("share", shareType));
		criteria.add(Restrictions.eq("completed", 'N'));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("completed" ), "completed");
		projList.add(Projections.sum( "amt" ), "amt");
		projList.add(Projections.sum( "amtInLakh" ), "amtInLakh");
		projList.add(Projections.groupProperty( "grSanOrdDtlRid" ), "grSanOrdDtlRid");
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(BankDetailEntity.class));
		List list = criteria.list();
		if(list.size() > 0 ) {
			return (BankDetailEntity) list.get(0);
		} else {
			return null;
		}
	}
}
