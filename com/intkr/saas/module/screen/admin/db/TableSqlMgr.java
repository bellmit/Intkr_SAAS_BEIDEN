package com.intkr.saas.module.screen.admin.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intkr.saas.client.log.UserLogClient;
import com.intkr.saas.domain.bo.db.DatabaseBO;
import com.intkr.saas.domain.bo.db.DatasourceBO;
import com.intkr.saas.domain.bo.db.TableBO;
import com.intkr.saas.manager.db.DatabaseManager;
import com.intkr.saas.manager.db.DatasourceManager;
import com.intkr.saas.manager.db.TableManager;
import com.intkr.saas.module.screen.admin.system.appearance.menu.MenuMgr;
import com.intkr.saas.util.RequestUtil;
import com.intkr.saas.util.claz.IOC;
import com.intkr.saas.util.db.DBUtil;

/**
 * 
 * 
 * @table database_tab
 * 
 * @author Beiden
 * @date 2020-04-02 19:03:12
 * @version 1.0
 */
public class TableSqlMgr {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private DatabaseManager databaseManager = IOC.get(DatabaseManager.class);

	private TableManager tableManager = IOC.get(TableManager.class);

	private DatasourceManager datasourceManager = IOC.get(DatasourceManager.class);

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		Long tableId = RequestUtil.getParam(request, "tableId", Long.class);
		Long databaseId = RequestUtil.getParam(request, "databaseId", Long.class);
		DatabaseBO database = databaseManager.get(databaseId);
		List<DatasourceBO> datasourceList = databaseManager.selectDatasource(databaseId);
		TableBO table = tableManager.getFull(tableId);
		DatasourceBO datasource = getDatasource(request);
		Map tableMap = getCreateTable(request, database, table, datasource);
		request.setAttribute("tableMap", tableMap);
		request.setAttribute("menuList", getMenu(request));
		request.setAttribute("datasourceList", datasourceList);
		request.setAttribute("datasource", datasource);
		request.setAttribute("database", database);
		request.setAttribute("table", table);
		request.setAttribute("query", table);
		UserLogClient.log(request, "打开", "管理");
	}

	private Map<String, Object> getCreateTable(HttpServletRequest request, DatabaseBO database, TableBO table, DatasourceBO datasource) {
		Connection connection = datasource.getConnection(database.getDbName());
		try {
			String sql = "show create table " + table.getDbName();
			PreparedStatement stmt = connection.prepareStatement(sql);
			List<Object> params = table.getSelectSqlParam(request);
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("table", rs.getObject("Table"));
				map.put("createTable", rs.getObject("Create Table"));
				return map;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.close(connection);
		}
	}

	private void count(HttpServletRequest request, DatabaseBO database, TableBO table, DatasourceBO datasource) {
		Connection connection = datasource.getConnection(database.getDbName());
		try {
			String sql = table.getCountSql(request);
			PreparedStatement stmt = connection.prepareStatement(sql);
			List<Object> params = table.getSelectSqlParam(request);
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer count = rs.getInt(1);
				table.set_count(count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
		}
	}

	private DatasourceBO getDatasource(HttpServletRequest request) {
		if (RequestUtil.existParam(request, "datasourceId")) {
			Long datasourceId = RequestUtil.getParam(request, "datasourceId", Long.class);
			DatasourceBO datasource = datasourceManager.get(datasourceId);
			return datasource;
		}
		Long databaseId = RequestUtil.getParam(request, "databaseId", Long.class);
		DatasourceBO datasource = databaseManager.getDefaultDatasource(databaseId);
		return datasource;
	}

	public static List<TableBO> getMenu(HttpServletRequest request) {
		String referer = request.getRequestURI();
		if (referer.contains("tableDataInfoMgr.html")) {
			request.setAttribute("tableUrlReferer", "tableDataInfoMgr.html");
		} else {
			request.setAttribute("tableUrlReferer", "tableInfoMgr.html");
		}
		Long databaseId = RequestUtil.getParam(request, "databaseId", Long.class);
		DatabaseManager databaseManager = IOC.get(DatabaseManager.class);
		List<TableBO> menuList = databaseManager.getMenu(databaseId);
		return menuList;
	}
	
}
