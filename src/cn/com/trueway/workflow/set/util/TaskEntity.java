package cn.com.trueway.workflow.set.util;


import java.util.Map;
/**
 * Ҫִ��������ࡢ����������
 * @author jiang.li
 * @date 2013-12-17 13:17
 */
public class TaskEntity {
    
    private Class<?> taskClass;
    private String taskMethod;
    private Map<?, ?> taskParam;
    public TaskEntity(Class<?> taskClass,String taskMethod,Map<?, ?> taskParam){
        this.taskClass = taskClass;
        this.taskMethod = taskMethod;
        this.taskParam = taskParam;
    }
    public Class<?> getTaskClass() {
        return taskClass;
    }
    public void setTaskClass(Class<?> taskClass) {
        this.taskClass = taskClass;
    }
    public String getTaskMethod() {
        return taskMethod;
    }
    public void setTaskMethod(String taskMethod) {
        this.taskMethod = taskMethod;
    }
    public Map<?, ?> getTaskParam() {
        return taskParam;
    }
    public void setTaskParam(Map<?, ?> taskParam) {
        this.taskParam = taskParam;
    }
}