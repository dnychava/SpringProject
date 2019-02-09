package org.idchavan.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.idchavan.dao.AbstractDAO;
import org.idchavan.dao.GrSanctionOrderDAO;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.GrSanctionOrderEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

@Repository("grSanctionOrderDAO")
public class GrSanctionOrderDAOImpl extends AbstractDAO implements GrSanctionOrderDAO {

	public GrSanctionOrderDAOImpl() {
		System.out.println(" Constructor Called GRSanctionOrderDAOImpl");
	}

	@Override
	public void saveOrUpdate(GrSanctionOrderEntity sanctionOrder) {
		super.saveOrUpdate(sanctionOrder);
	}

	@Override
	public GrSanctionOrderEntity updateSanctionOrder(GrSanctionOrderEntity sanctionOrder) {
		getSession().update(sanctionOrder);
		return sanctionOrder;
	}

	@Override
	public void deleteGrSanctionOrder(GrSanctionOrderEntity sanctionOrder) {
		super.delete(sanctionOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrSanctionOrderEntity> getAllGrSanctionOrders() {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderEntity.class);
		criteria.addOrder(Order.desc("createdDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public GrSanctionOrderEntity findByRid(String rid) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (GrSanctionOrderEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrSanctionOrderEntity> getGrSanctionOrdersReport(SanctionOrderFilterDTO sanOrdFilterDTO) {

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
	public void deleteGrSanctionOrderDetails(GrSanctionOrderDetailEntity sanctionOrderDetailEntity) {
		delete(sanctionOrderDetailEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrSanctionOrderDetailEntity> getAllGrSanctionOrderDetails(List<String> rids) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderDetailEntity.class);
		criteria.add(Restrictions.in("rid", rids)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public GrSanctionOrderDetailEntity getGrDetailsUsingGroupBy(String sanOrdDtlRid, String shareType) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderDetailEntity.class);
		criteria.add(Restrictions.eq("sanOrdDtlRid", sanOrdDtlRid));
		criteria.add(Restrictions.eq("share", shareType));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("rid" ), "rid");
		projList.add(Projections.property("completed" ), "completed");
		projList.add(Projections.sum( "orderAmt" ), "orderAmt");
		projList.add(Projections.sum( "orderAmtInLakh" ), "orderAmtInLakh");
		projList.add(Projections.groupProperty( "sanOrdDtlRid" ), "sanOrdDtlRid");
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(GrSanctionOrderDetailEntity.class));
		List list = criteria.list();
		if(list.size() > 0 ) {
			return (GrSanctionOrderDetailEntity) list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrSanctionOrderDetailEntity> getAllGrSanctionOrderDetails(OrderDetailFilterDTO detailFilterDTO) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderDetailEntity.class);
		if(StringUtils.isNotBlank( detailFilterDTO.getRid())) {
			criteria.add( Restrictions.eq( "rid", detailFilterDTO.getRid()));
		}
		if(StringUtils.isNotEmpty( detailFilterDTO.getProgramName())) {
			criteria.add( Restrictions.eq( "orderProgramName", detailFilterDTO.getProgramName()));
		}
		if(StringUtils.isNotEmpty( detailFilterDTO.getCategory())) {
			criteria.add( Restrictions.eq( "orderCategory", detailFilterDTO.getCategory()));
		}
		if(StringUtils.isNotEmpty( detailFilterDTO.getShareType())) {
			criteria.add( Restrictions.eq( "share", detailFilterDTO.getShareType()));
		}
		if(StringUtils.isNotEmpty( Character.toString(detailFilterDTO.getCompleted()))) {
			criteria.add( Restrictions.eq( "completed", detailFilterDTO.getCompleted()));
		}
		criteria.add(Restrictions.in("grSanctionOrder.rid", detailFilterDTO.getRidListForDTLTbl()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@Override
	public GrSanctionOrderDetailEntity findGrSanctionOrderDetailByRid(String rid) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderDetailEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (GrSanctionOrderDetailEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrSanctionOrderDetailEntity> getAllGrSanOrdDtlBySanOrdDtlRid(String sanOrdDtlRid) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderDetailEntity.class);
		criteria.add(Restrictions.eq("sanOrdDtlRid", sanOrdDtlRid) ).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrSanctionOrderEntity> findGrSanOrdByYear(String year) {
		Criteria criteria = getSession().createCriteria(GrSanctionOrderEntity.class);
		criteria.add(Restrictions.eq("year", year)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
