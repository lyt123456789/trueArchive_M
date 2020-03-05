package cn.com.trueway.workflow.log;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;
public class MyJDBCAppender extends JDBCAppender{
	/* private JdbcSupport jdbcSupport;

	    @Override
	    protected void closeConnection(Connection conn) {
	        try {
	            if (conn != null && !conn.isClosed())
	                conn.close();
	        } catch (SQLException e) {
	            errorHandler.error("Error closing connection", e, ErrorCode.GENERIC_FAILURE);
	        }
	    }

	    @Override
	    protected Connection getConnection() throws SQLException {
	        if (jdbcSupport == null) {
	            jdbcSupport = SpringUtils.getBean(JdbcSupport.class, "jdbcSupport");
	        }
	        return jdbcSupport == null ? null : jdbcSupport.getJt().getDataSource().getConnection();
	    }

	    @Override
	    protected void execute(String sql) throws SQLException {
	        Connection conn = getConnection();
	        if (conn != null) {
	            Statement stmt = null;
	            try {
	                stmt = conn.createStatement();
	                stmt.executeUpdate(sql);
	            } catch (SQLException e) {
	                if (stmt != null)
	                    stmt.close();
	                throw e;
	            }
	            stmt.close();
	            closeConnection(conn);
	        }
	    }
	    */
	    @Override
	    protected String getLogStatement(LoggingEvent event) {
	        String fqnOfCategoryClass=event.fqnOfCategoryClass;
	        Category logger= event.getLogger();
	        Priority level=event.getLevel();
	        Object message=event.getMessage();
	        Throwable throwable=null;
	        MyLoggingEvent bEvent=new MyLoggingEvent(fqnOfCategoryClass,logger,level,message,throwable);
	        return super.getLogStatement(bEvent);
	    }
}
