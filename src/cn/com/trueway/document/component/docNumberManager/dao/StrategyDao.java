package cn.com.trueway.document.component.docNumberManager.dao;

import java.util.List;

import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;


public interface StrategyDao {

	public abstract List<DocNumberStrategy> getStrategyList();

	public abstract DocNumberStrategy getSingnStrategy(String id);

}