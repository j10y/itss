package com.zsgj.itil.project.service;

import java.util.List;

import com.zsgj.itil.config.extlist.entity.SRProjectPlan;

/**
 * ��Ŀ�ƻ�����
 * @Class Name ProjectPlanService
 * @Author lee
 * @Create In Mar 13, 2009
 */
public interface SRProjectPlanService {
	/**
	 * ͨ��ID��ȡ��Ӧ��Ŀ�ƻ�
	 * @Methods Name findProjectPlanById
	 * @Create In Mar 13, 2009 By lee
	 * @param id
	 * @return ProjectPlan
	 */
	SRProjectPlan findProjectPlanById(String id);
	/**
	 * ��������ʵ��ID���������ȡ����Ŀ�ƻ�
	 * @Methods Name findRootProjectPlanByReq
	 * @Create In Mar 15, 2009 By lee
	 * @param requireId
	 * @param clazz
	 * @return ProjectPlan
	 */
	SRProjectPlan findRootProjectPlanByReq(String requireId);
	/**
	 * ��������ʵ��ID���������ȡ��������Ŀ�ƻ�
	 * @Methods Name findAllProjectPlanByReq
	 * @Create In Mar 26, 2009 By Administrator
	 * @param requireId
	 * @param clazz
	 * @return List<ProjectPlan>
	 */
	List<SRProjectPlan> findAllProjectPlanByReq(String requireId);
	/**
	 * ͨ�����ƻ���ȡһ������Ŀ�ƻ�
	 * @Methods Name findChildPlans
	 * @Create In Mar 13, 2009 By lee
	 * @param projectPlan
	 * @return List<ProjectPlan>
	 */
	List<SRProjectPlan> findChildPlans(SRProjectPlan projectPlan);
}
