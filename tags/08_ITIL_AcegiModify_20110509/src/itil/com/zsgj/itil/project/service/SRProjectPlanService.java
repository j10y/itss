package com.zsgj.itil.project.service;

import java.util.List;

import com.zsgj.itil.config.extlist.entity.SRProjectPlan;

/**
 * 项目计划服务
 * @Class Name ProjectPlanService
 * @Author lee
 * @Create In Mar 13, 2009
 */
public interface SRProjectPlanService {
	/**
	 * 通过ID获取对应项目计划
	 * @Methods Name findProjectPlanById
	 * @Create In Mar 13, 2009 By lee
	 * @param id
	 * @return ProjectPlan
	 */
	SRProjectPlan findProjectPlanById(String id);
	/**
	 * 根据需求实体ID和需求类获取根项目计划
	 * @Methods Name findRootProjectPlanByReq
	 * @Create In Mar 15, 2009 By lee
	 * @param requireId
	 * @param clazz
	 * @return ProjectPlan
	 */
	SRProjectPlan findRootProjectPlanByReq(String requireId);
	/**
	 * 根据需求实体ID和需求类获取其所有项目计划
	 * @Methods Name findAllProjectPlanByReq
	 * @Create In Mar 26, 2009 By Administrator
	 * @param requireId
	 * @param clazz
	 * @return List<ProjectPlan>
	 */
	List<SRProjectPlan> findAllProjectPlanByReq(String requireId);
	/**
	 * 通过父计划获取一级子项目计划
	 * @Methods Name findChildPlans
	 * @Create In Mar 13, 2009 By lee
	 * @param projectPlan
	 * @return List<ProjectPlan>
	 */
	List<SRProjectPlan> findChildPlans(SRProjectPlan projectPlan);
}
