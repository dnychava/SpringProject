package org.idchavan.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.idchavan.dao.AbstractDAO;
import org.idchavan.dao.SanctionOrderDAO;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.stereotype.Repository;

@Repository("sanctionOrderDAO")
public class SanctionOrderDAOImpl extends AbstractDAO implements SanctionOrderDAO {

	public SanctionOrderDAOImpl() {
		System.out.println(" Constructor Called SanctionOrderDAOImpl");
	}

	@Override
	public void saveOrUpdate(SanctionOrderEntity sanctionOrder) {
		super.saveOrUpdate(sanctionOrder);
	}

	@Override
	public SanctionOrderEntity updateSanctionOrder(SanctionOrderEntity sanctionOrder) {
		getSession().update(sanctionOrder);
		return sanctionOrder;
	}

	@Override
	public void deleteSanctionOrder(SanctionOrderEntity sanctionOrder) {
		super.delete(sanctionOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderEntity> getAllSanctionOrders() {
		Criteria criteria = getSession().createCriteria(SanctionOrderEntity.class);
		criteria.addOrder(Order.desc("createdDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public SanctionOrderEntity findByRid(String rid) {
		Criteria criteria = getSession().createCriteria(SanctionOrderEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (SanctionOrderEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderEntity> getSanctionOrdersReport(SanctionOrderFilterDTO sanOrdFilterDTO) {

		StringBuilder hqlQuery = new StringBuilder(
				"select {s.*}, {d.*} from SAN_ORD s, SAN_ORD_DTL d where s.SAN_ORD_RID=d.SAN_ORD_DTL__SAN_ORD_RID ");
		if (!"All".equalsIgnoreCase(sanOrdFilterDTO.getYear())) {
			hqlQuery.append(" and s.SAN_ORD_YEAR in( :sanYear ) ");
		}
		if (!"All".equalsIgnoreCase(sanOrdFilterDTO.getPrograms())) {
			hqlQuery.append(" and d.SAN_ORD_DTL_PROGRAM_NAME in( :sanProgram ) ");
		}
		if (!"All".equalsIgnoreCase(sanOrdFilterDTO.getCategories())) {
			hqlQuery.append(" and d.SAN_ORD_DTL_CATEGORY in( :sanCategory ) ");
		}
		SQLQuery nativeQuery = getSession().createSQLQuery(hqlQuery.toString());
		nativeQuery.addEntity("s", SanctionOrderEntity.class);
		nativeQuery.addJoin("d", "s.sanctionOrderDetails");

		if (!"All".equalsIgnoreCase(sanOrdFilterDTO.getYear())) {
			nativeQuery.setParameterList("sanYear", sanOrdFilterDTO.getYear().split(","));
		}
		if (!"All".equalsIgnoreCase(sanOrdFilterDTO.getPrograms())) {
			nativeQuery.setParameterList("sanProgram", sanOrdFilterDTO.getPrograms().split(","));
		}
		if (!"All".equalsIgnoreCase(sanOrdFilterDTO.getCategories())) {
			nativeQuery.setParameterList("sanCategory", sanOrdFilterDTO.getCategories().split(","));
		}

		List<Object[]> rows = nativeQuery.list();

		List<SanctionOrderEntity> resultList = new ArrayList<SanctionOrderEntity>();
		for (Object[] row : rows) {
			SanctionOrderEntity san = (SanctionOrderEntity) row[0];
			SanctionOrderDetailEntity sanDtl = (SanctionOrderDetailEntity) row[1];
			List<SanctionOrderDetailEntity> sanDtlList = new ArrayList<SanctionOrderDetailEntity>();
			sanDtlList.add(sanDtl);
			san.setSanctionOrderDetails(sanDtlList);
			resultList.add(san);
		}

		return resultList;
		
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
	public void deleteSanctionOrderDetails(SanctionOrderDetailEntity sanctionOrderDetailEntity) {
		delete(sanctionOrderDetailEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderDetailEntity> getAllSanctionOrderDetails(List<String> rids) {
		Criteria criteria = getSession().createCriteria(SanctionOrderDetailEntity.class);
		criteria.add(Restrictions.in("rid", rids)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderDetailEntity> getAllSanctionOrderDetails(OrderDetailFilterDTO detailFilterDTO) {
		Criteria criteria = getSession().createCriteria(SanctionOrderDetailEntity.class);
		if (StringUtils.isNotBlank(detailFilterDTO.getRid())) {
			criteria.add(Restrictions.eq("rid", detailFilterDTO.getRid()));
		}
		if (StringUtils.isNotEmpty(detailFilterDTO.getProgramName())) {
			criteria.add(Restrictions.eq("orderProgramName", detailFilterDTO.getProgramName()));
		}
		if (StringUtils.isNotEmpty(detailFilterDTO.getCategory())) {
			criteria.add(Restrictions.eq("orderCategory", detailFilterDTO.getCategory()));
		}
		if (StringUtils.isNotEmpty(detailFilterDTO.getCategory())) {
			criteria.add(Restrictions.eq("orderCategory", detailFilterDTO.getCategory()));
		}
		if (StringUtils.isNotEmpty(detailFilterDTO.getShareType())) {
			if (StringUtils.equals("State", detailFilterDTO.getShareType())) {
				if (StringUtils.isNotEmpty(Character.toString(detailFilterDTO.getCompleted()))) {
					criteria.add(Restrictions.eq("stateShareCompleted", detailFilterDTO.getCompleted()));
				}
			} else if (StringUtils.equals("Central", detailFilterDTO.getShareType())) {
				if (StringUtils.isNotEmpty(Character.toString(detailFilterDTO.getCompleted()))) {
					criteria.add(Restrictions.eq("completed", detailFilterDTO.getCompleted()));
				}
			}
		}
		criteria.add(Restrictions.in("sanctionOrder.rid", detailFilterDTO.getRidListForDTLTbl()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public SanctionOrderDetailEntity findSanctionOrderDetailByRid(String rid) {
		Criteria criteria = getSession().createCriteria(SanctionOrderDetailEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (SanctionOrderDetailEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderEntity> getSactionOrdGroubyProgGroupName(PendingOrderFilterDTO pendingOrderFilterDTO) {
		Criteria criteria = getSession().createCriteria(SanctionOrderEntity.class);
		if (!StringUtils.equals("All", pendingOrderFilterDTO.getYear())) {
			criteria.add(Restrictions.eq("year", pendingOrderFilterDTO.getYear()));
		}
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("rid"), "rid");
		projList.add(Projections.property("documentEntity"), "documentEntity");
		projList.add(Projections.property("year"), "year");
		projList.add(Projections.groupProperty("programGroupName"), "programGroupName");
		projList.add(Projections.groupProperty("rid"), "rid");
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(SanctionOrderEntity.class));
		List list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderDetailEntity> getSanOrdDtlOfGroupByProgramName( String sanOrdRid,
			PendingOrderFilterDTO pendingOrderFilterDTO) {
		Criteria criteria = getSession().createCriteria(SanctionOrderDetailEntity.class);
		criteria.add(Restrictions.eq("sanctionOrder.rid", sanOrdRid));
		if (pendingOrderFilterDTO.getPrograms() != null
				&& !StringUtils.equals("All", pendingOrderFilterDTO.getPrograms())) {
			criteria.add(Restrictions.in("orderProgramName", pendingOrderFilterDTO.getPrograms().split(",")));
		}
		if (pendingOrderFilterDTO.getCategories() != null
				&& !StringUtils.equals("All", pendingOrderFilterDTO.getCategories())) {
			criteria.add(Restrictions.in("orderCategory", pendingOrderFilterDTO.getCategories().split(",")));
		}
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("rid"), "rid");
		projList.add(Projections.property("orderCategory"), "orderCategory");
		projList.add(Projections.sum("orderAmt"), "orderAmt");
		projList.add(Projections.sum("orderAmtInLakh"), "orderAmtInLakh");
		projList.add(Projections.sum("orderStateShareAmt"), "orderStateShareAmt");
		projList.add(Projections.sum("orderStateShareAmtInLakh"), "orderStateShareAmtInLakh");
		projList.add(Projections.groupProperty("orderProgramName"), "orderProgramName");
		projList.add(Projections.groupProperty("orderCategory"), "orderCategory");
		projList.add(Projections.groupProperty("rid"), "rid");
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(SanctionOrderDetailEntity.class));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionOrderEntity> findSanOrdByYear(String year) {
		Criteria criteria = getSession().createCriteria(SanctionOrderEntity.class);
		criteria.add(Restrictions.eq("year", year)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
