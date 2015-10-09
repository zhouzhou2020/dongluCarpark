package com.donglu.carpark.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.CriteriaUtils;
import org.criteria4jpa.criterion.Restrictions;

import com.donglu.carpark.service.SystemUserServiceI;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkSystemUser;
import com.dongluhitec.card.service.MapperConfig;
import com.dongluhitec.card.service.impl.DatabaseOperation;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.UnitOfWork;

public class SystemUserServiceImpl implements SystemUserServiceI {
	@Inject
	private Provider<EntityManager> emprovider;

	@Inject
	private UnitOfWork unitOfWork;

	@Inject
	private MapperConfig mapper;
	
	@Override
	public List<SingleCarparkSystemUser> findAll() {
		unitOfWork.begin();
		try {
			Criteria c=CriteriaUtils.createCriteria(emprovider.get(), SingleCarparkSystemUser.class);
			return c.getResultList();
		}finally{
			unitOfWork.end();
		}
	}

	@Override
	public SingleCarparkSystemUser findByNameAndPassword(String userName, String password) {
		unitOfWork.begin();
		try {
			Criteria c=CriteriaUtils.createCriteria(emprovider.get(), SingleCarparkSystemUser.class);
			c.add(Restrictions.eq("userName", userName));
			c.add(Restrictions.eq("password", password));
			
			Object singleResultOrNull = c.getSingleResultOrNull();
			if (singleResultOrNull!=null) {
				return (SingleCarparkSystemUser) singleResultOrNull;
			}
			return null;
		}finally{
			unitOfWork.end();
		}
	}

	@Transactional
	public Long removeSystemUser(SingleCarparkSystemUser systemUser) {
		DatabaseOperation<SingleCarparkSystemUser> dom = DatabaseOperation.forClass(SingleCarparkSystemUser.class, emprovider.get());
		if (!systemUser.getUserName().equals("admin")) {
			dom.remove(systemUser);
		}
		return systemUser.getId();
	}

	@Transactional
	public Long saveSystemUser(SingleCarparkSystemUser systemUser) {
		DatabaseOperation<SingleCarparkSystemUser> dom = DatabaseOperation.forClass(SingleCarparkSystemUser.class, emprovider.get());
		if (systemUser.getId() == null) {
			dom.insert(systemUser);
		} else {
			dom.save(systemUser);
		}
		return systemUser.getId();
	}

	@Transactional
	public void init() {
		DatabaseOperation<SingleCarparkSystemUser> dom = DatabaseOperation.forClass(SingleCarparkSystemUser.class, emprovider.get());
		SingleCarparkSystemUser systemUser=new SingleCarparkSystemUser();
		systemUser.setUserName("admin");
		systemUser.setPassword("admin");
		systemUser.setType("系统管理员");
		systemUser.setCreateDate(new Date());
		dom.insert(systemUser);
	}

}
