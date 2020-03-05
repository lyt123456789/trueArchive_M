package cn.com.trueway.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncDatabase extends JDBCBase{
	//异库数据库连接名 (相对) 注意改动 
//	String databaselinkName="CCQOA";
	String databaselinkName="ccqoa";
	//触发器文件名
//	String path = "d:\\trigger_ccqoa.txt";
	String path = "d:\\trigger_workflow_ccq.txt";
	
	public void addTrigger() throws Exception{
//		start();
		String sql = "select t.table_name,t1.column_name from user_tables t left outer join\n" +
						"(\n" + 
						"select cu.table_name, cu.column_name\n" + 
						"  from user_cons_columns cu, user_constraints au\n" + 
						" where cu.constraint_name = au.constraint_name\n" + 
						"   and au.constraint_type = 'P'\n" + 
						") t1 on t.TABLE_NAME=t1.table_name";
				;
        String mapColumn="table_name,column_name";
        
        
        //查询获取所有的表及表的主键
        List<Map<String, Object>> list= querySQL(sql,mapColumn);
        
        //目前只对有主键的表 建立触发器 
        
        if (list!=null) {
        	int allcount=list.size();//所有表个数
        	int hasIndexCount=0;//有主键的个数
        	int successCount=0;//执行成功个数
        	List<String> failTables=new ArrayList<String>();
        	String sqlAll = "";
			for (int i = 0; i < list.size(); i++) {
				String tablename=list.get(i).get("table_name").toString();
				String columnname=list.get(i).get("column_name")==null?null:list.get(i).get("column_name").toString();
				//触发器名称长度不能超过30   tr_EM12319_INF_CASE_DEPSUPERVISE
				String temptablename=tablename.length()>27?tablename.substring(0,27):tablename;
				if (columnname==null) {
					continue;
				}
				hasIndexCount++;
				String trigger_sql=
						"CREATE OR REPLACE TRIGGER tr_"+temptablename+"\n" +
								"  before INSERT or update  OR DELETE ON "+tablename+"\n" + 
								"  FOR EACH ROW\n" + 
								"  /*AFTER*/\n" + 
								"DECLARE\n" + 
								"   operate_tableName  varchar2(400);\n" + 
								"BEGIN\n" + 
								"  operate_tableName:='"+tablename+"';/*表名*/\n" + 
								"    CASE\n" + 
								"      WHEN inserting THEN\n" + 
								"        if :new.OPERATE_LOG_ID is null then--表示本地操作\n" + 
								"          insert into operate_logs\n" + 
								"            (\n" + 
								"            id,\n" + 
								"            operate_id,\n" + 
								"            operate_table,\n" + 
								"            operate_type,\n" + 
								"            operate_date,\n" + 
								"            operate_state,\n" + 
								"            OPERATE_ID_NAME\n" + 
								"             )\n" + 
								"          values\n" + 
								"            (\n" + 
								"            sys_guid(),\n" + 
								"            :new."+columnname+",/*主键*/\n" + 
								"            operate_tableName,\n" + 
								"            'insert',\n" + 
								"            systimestamp,\n" + 
								"            '0',\n" + 
								"            '"+columnname+"'/*主键*/\n" + 
								"             );\n" + 
								"        end if;\n" + 
								"        if :new.OPERATE_LOG_ID is not null then--表示异库同步操作\n" + 
								"          --回写异库log中间表操作状态\n" + 
								"          update operate_logs@"+databaselinkName+" set OPERATE_STATE='1'/*异库数据库连接名*/\n" + 
								"            where id=:new.OPERATE_LOG_ID and (OPERATE_TYPE='insert' or OPERATE_TYPE='update');\n" + 
								"            --非常重要 OPERATE_LOG_ID至为空 表示短暂的一次操作结束\n" + 
								"          :new.OPERATE_LOG_ID:=null;\n" + 
								"        end if;\n" + 
								"        WHEN updating THEN\n" + 
								"          if :new.OPERATE_LOG_ID is null then--表示本地操作\n" + 
								"            insert into operate_logs\n" + 
								"            (\n" + 
								"            id,\n" + 
								"            operate_id,\n" + 
								"            operate_table,\n" + 
								"            operate_type,\n" + 
								"            operate_date,\n" + 
								"            operate_state,\n" + 
								"            OPERATE_ID_NAME\n" + 
								"             )\n" + 
								"          values\n" + 
								"            (\n" + 
								"            sys_guid(),\n" + 
								"            :new."+columnname+",/*主键*/\n" + 
								"             operate_tableName,\n" + 
								"            'update',\n" + 
								"            systimestamp,\n" + 
								"            '0',\n" + 
								"            '"+columnname+"'/*主键*/\n" + 
								"             );\n" + 
								"          end if;\n" + 
								"\n" + 
								"        WHEN deleting THEN\n" + 
								"          if :old.OPERATE_LOG_ID is null then--表示本地操作\n" + 
								"             --此处做特殊处理  针对1次同步周期内(5分钟)同时出现insert和delete操作\n" + 
								"              --即如果同一条数据在同步周期内有delete操作，则删除delete操作外的数据\n" + 
								"              delete from operate_logs where operate_id=:old."+columnname+";/*主键*/\n" + 
								"\n" + 
								"             insert into operate_logs\n" + 
								"              (\n" + 
								"              id,\n" + 
								"              operate_id,\n" + 
								"              operate_table,\n" + 
								"              operate_type,\n" + 
								"              operate_date,\n" + 
								"              operate_state,\n" + 
								"              OPERATE_ID_NAME\n" + 
								"               )\n" + 
								"            values\n" + 
								"              (\n" + 
								"              sys_guid(),\n" + 
								"              :old."+columnname+",/*主键*/\n" + 
								"               operate_tableName,\n" + 
								"              'delete',\n" + 
								"              systimestamp,\n" + 
								"              '0',\n" + 
								"              '"+columnname+"'/*主键*/\n" + 
								"               );\n" + 
								"\n" + 
								"          end if;\n" + 
								"          if :old.OPERATE_LOG_ID is not null then--表示异库同步操作\n" + 
								"             --回写异库log中间表操作状态\n" + 
								"            update operate_logs@"+databaselinkName+" set OPERATE_STATE='1'/*异库数据库连接名*/\n" + 
								"              where id=:old.OPERATE_LOG_ID and OPERATE_TYPE='delete';\n" + 
								"            --非常重要 OPERATE_LOG_ID至为空 表示短暂的一次操作结束\n" + 
								"            --:old.OPERATE_LOG_ID:=null;\n" + 
								"          end if;\n" + 
								"\n" + 
								"    END CASE;\n" + 
								"\n" + 
								"END;";

				//因触发器变量:new. 为sql预编译关键字  所有无法正确执行sql
				//改为些txt文件执行
				//executeSQL(trigger_sql,":new."+columnname,":new."+columnname,":old."+columnname);
				try {
					sqlAll += trigger_sql +"\n/\n-------------------------------------------------\n";
//					executeSQL(trigger_sql);
					successCount++;
				} catch (Exception e) {
					failTables.add("表名:"+tablename+"    主键:"+columnname);
					e.printStackTrace();
				}
				//hjzxbjService.excuteSql(trigger_sql);
				
				

			}
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			if(!file.exists()){
				file.createNewFile();
			}
			
			byte[] bytes = sqlAll.getBytes();
			System.out.println("生成触发器文件成功:"+path);
			fos.write(bytes);
			fos.flush();
			fos.close();
			
			
			System.out.println("所有表个数:"+allcount);
			System.out.println("表有主键的个数:"+hasIndexCount);
			System.out.println("添加触发器执行成功个数:"+successCount);
			if (failTables.size()>0) {
				System.out.println("添加触发器执行失败个数:"+failTables.size());
				System.out.println("执行失败的表名如下：");
				for (int i = 0; i < failTables.size(); i++) {
					System.out.println(failTables.get(i));
				}
			}
			//Select count(*) From user_objects Where object_type='TRIGGER' 查询数据库触发器的个数
			
		}
        
	}
	
	
	public void addColumn(){
//		start();
		String sql = "select t.table_name,t1.column_name from user_tables t left outer join\n" +
				"(\n" + 
				"select cu.table_name, cu.column_name\n" + 
				"  from user_cons_columns cu, user_constraints au\n" + 
				" where cu.constraint_name = au.constraint_name\n" + 
				"   and au.constraint_type = 'P'\n" + 
				") t1 on t.TABLE_NAME=t1.table_name";
		;
		String mapColumn="table_name,column_name";
		
		
		//查询获取所有的表及表的主键
		List<Map<String, Object>> list= querySQL(sql,mapColumn);
		
		//目前只对有主键的表 添加字段
        
        if (list!=null) {
        	int allcount=list.size();//所有表个数
        	int hasIndexCount=0;//有主键的个数
        	int successCount=0;//执行成功个数
        	List<String> failTables=new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				String tablename=list.get(i).get("table_name").toString();
				String columnname=list.get(i).get("column_name")==null?null:list.get(i).get("column_name").toString();
				if (columnname==null) {
					continue;
				}
				hasIndexCount++;
				String trigger_sql=
						"declare\n" +
								"  p_table_name varchar2(30);\n" + 
								"  p_column_name varchar2(30);\n" + 
								"  p_data_type varchar2(30);\n" + 
								"  p_cnt number;\n" + 
								"  p_sql varchar2(4000);\n" + 
								"begin\n" + 
								"  p_table_name := '"+tablename+"';\n" + 
								"  p_column_name:='OPERATE_LOG_ID';\n" +
								"  select count(1)\n" + 
								"    into p_cnt\n" + 
								"    from user_tab_cols\n" + 
								"   where table_name = p_table_name\n" + 
								"     and column_name = p_column_name;\n" + 
								"  if p_cnt = 0 then\n" + 
								"    p_sql := 'alter table ' || p_table_name || ' add OPERATE_LOG_ID VARCHAR2(50)';\n" + 
								"    execute immediate p_sql;\n" + 
								"    p_sql := 'comment on column '||p_table_name||'.OPERATE_LOG_ID is ''操作日志id(用途1、区分异库同步操作或是本库操作 2、确认是否同步成功，异库回写判断)''';\n" + 
								"    execute immediate p_sql;\n" + 
								"  end if;\n" + 
								"end;";
;

				//因触发器变量:new. 为sql预编译关键字  所有无法正确执行sql
				//改为些txt文件执行
				//executeSQL(trigger_sql,":new."+columnname,":new."+columnname,":old."+columnname);
				try {
					executeSQL(trigger_sql);
					successCount++;
				} catch (Exception e) {
					failTables.add("表名:"+tablename);
					e.printStackTrace();
				}
				//hjzxbjService.excuteSql(trigger_sql);
				
				

			}
			System.out.println("所有表个数:"+allcount);
			System.out.println("表有主键的个数:"+hasIndexCount);
			System.out.println("添加字段OPERATE_LOG_ID执行成功个数:"+successCount);
			if (failTables.size()>0) {
				System.out.println("添加字段OPERATE_LOG_ID执行失败个数:"+failTables.size());
				System.out.println("执行失败的表名如下：");
				for (int i = 0; i < failTables.size(); i++) {
					System.out.println(failTables.get(i));
				}
			}
			//Select count(*) From user_objects Where object_type='TRIGGER' 查询数据库触发器的个数
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		SyncDatabase syd = new SyncDatabase();
		syd.addColumn();
		//syd.addTrigger();
	}
}
