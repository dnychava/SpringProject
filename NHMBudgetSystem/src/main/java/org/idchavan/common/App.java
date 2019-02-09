package org.idchavan.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.idchavan.bo.SanctionOrderBO;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		SanctionOrderBO sanctionOrderBO = (SanctionOrderBO)applicationContext.getBean( "sanctionOrderBO" );
		
		SanctionOrderEntity sanctionOrder = new SanctionOrderEntity();
		
		SanctionOrderDetailEntity sanctionOrderDetail = new SanctionOrderDetailEntity();
		sanctionOrderDetail.setOrderAmtInLakh(BigDecimal.TEN);
		sanctionOrderDetail.setOrderDate(new Date());

		SanctionOrderDetailEntity sanctionOrderDetail1 = new SanctionOrderDetailEntity();
		sanctionOrderDetail.setOrderAmtInLakh(BigDecimal.TEN);
		sanctionOrderDetail1.setOrderDate(new Date());

		List<SanctionOrderDetailEntity> listOrdDetails = new ArrayList<SanctionOrderDetailEntity>();
		listOrdDetails.add(sanctionOrderDetail);

		listOrdDetails.add(sanctionOrderDetail1);

		sanctionOrder.setSanctionOrderDetails(listOrdDetails);
		
		//sanctionOrderBO.createSanctionOrder(sanctionOrder);
		
		System.out.println("======DONE======");

	}

}
