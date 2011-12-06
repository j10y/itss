package com.xpsoft.oa.action.bandpoor;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.service.bandpoor.BandService;

public class BandAction extends BaseAction {
	@Resource
	private BandService bandService;
	private Band band;
	private Long id;
	
	public BandService getBandService() {
		return bandService;
	}
	public void setBandService(BandService bandService) {
		this.bandService = bandService;
	}
	public Band getBand() {
		return band;
	}
	public void setBand(Band band) {
		this.band = band;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
