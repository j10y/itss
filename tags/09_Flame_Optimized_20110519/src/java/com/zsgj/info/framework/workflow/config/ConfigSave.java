package com.zsgj.info.framework.workflow.config;

import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.entity.NodeConfig;

public interface ConfigSave {
	public void save(NodeConfig nodeConfig,int wfVersion);
	//public void save(ExecutionContext ec, NodeConfig nodeConfig,int wfVersion, int cfgVersion);
}
