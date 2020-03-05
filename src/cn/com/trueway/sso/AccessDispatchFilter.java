package cn.com.trueway.sso;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trueway.client.session.HashMapBackedSessionMappingStorage;
import com.trueway.client.session.SessionMappingStorage;
import com.trueway.client.util.AbstractConfigurationFilter;
import com.trueway.client.util.CommonUtils;
import com.trueway.client.util.XmlUtils;

import egov.appservice.sso.SingleSignOutFilter;

/**
 * 会话过期访问跳转
 * @author yank
 */
public class AccessDispatchFilter  extends AbstractConfigurationFilter{
	private String artifactParameterName = "ticket";
private static SessionMappingStorage SESSION_MAPPING_STORAGE = new HashMapBackedSessionMappingStorage();
private static Log log = LogFactory.getLog(SingleSignOutFilter.class);

public void init(FilterConfig filterConfig)
  throws ServletException
{
  if (!isIgnoreInitConfiguration()) {
    setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
  }
  init();
}

public void init()
{
  CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
  CommonUtils.assertNotNull(SESSION_MAPPING_STORAGE, "sessionMappingStorage cannote be null.");
}

public void setArtifactParameterName(String artifactParameterName)
{
  this.artifactParameterName = artifactParameterName;
}

public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
  throws IOException, ServletException
{
  HttpServletRequest request = (HttpServletRequest)servletRequest;
  if ("POST".equals(request.getMethod()))
  {
    String logoutRequest = CommonUtils.safeGetParameter(request, "logoutRequest");
    if (CommonUtils.isNotBlank(logoutRequest))
    {
      if (log.isTraceEnabled()) {
        log.trace("Logout request=[" + logoutRequest + "]");
      }
      String sessionIdentifier = XmlUtils.getTextForElement(logoutRequest, "SessionIndex");
      if (CommonUtils.isNotBlank(sessionIdentifier))
      {
        HttpSession session = SESSION_MAPPING_STORAGE.removeSessionByMappingId(sessionIdentifier);
        System.out.println("==================" + session);
        if (session != null)
        {
          String sessionID = session.getId();
          if (log.isDebugEnabled()) {
            log.debug("Invalidating session [" + sessionID + "] for ST [" + sessionIdentifier + "]");
          }
          if (session != null) {
            session.removeAttribute("_const_cas_assertion_");
          }
          doWhenLogout(session);
          try
          {
            session.invalidate();
          }
          catch (IllegalStateException e)
          {
            log.debug(e, e);
          }
        }
      }
    }
  }
  else
  {
    String artifact = CommonUtils.safeGetParameter(request, this.artifactParameterName);
    if (CommonUtils.isNotBlank(artifact))
    {
      HttpSession session = request.getSession(true);
      if (log.isDebugEnabled()) {
        log.debug("Storing session identifier for " + session.getId());
      }
      try
      {
        SESSION_MAPPING_STORAGE.removeBySessionById(session.getId());
      }
      catch (Exception localException) {}
      SESSION_MAPPING_STORAGE.addSessionById(artifact, session);
    }
    else
    {
      log.debug("No Artifact Provided; no action taking place.");
    }
  }
  filterChain.doFilter(servletRequest, servletResponse);
}

public void setSessionMappingStorage(SessionMappingStorage storage)
{
  SESSION_MAPPING_STORAGE = storage;
}

public static SessionMappingStorage getSessionMappingStorage()
{
  return SESSION_MAPPING_STORAGE;
}

public void destroy() {}

public void doWhenLogout(HttpSession session) {}}
