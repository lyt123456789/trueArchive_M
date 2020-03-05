
package cn.com.trueway.base.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ClassName: JsonUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年4月26日 上午10:05:59 <br/>
 *
 * @author adolph.jiang
 * @version 
 * @since JDK 1.6
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonUtil{

	static String string2Json(String s){
		StringBuilder sb = new StringBuilder(s.length() + 20);
		sb.append('\"');
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			switch(c){
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
			}
		}
		sb.append('\"');
		return sb.toString();
	}

	static String number2Json(Number number){
		return number.toString();
	}

	static String boolean2Json(Boolean bool){
		return bool.toString();
	}
	

	static String time2Json(Timestamp time){
		String dayTime = time.toString().split("\\s")[0];
		return "'"+dayTime+"'";
	}


	static String map2Json(Map<String, Object> map){
		if(map.isEmpty()) return "{}";
		StringBuilder sb = new StringBuilder(map.size() << 4);
		sb.append('{');
		Set<String> keys = map.keySet();
		for(String key: keys){
			Object value = map.get(key);
			sb.append('\"');
			sb.append(key);
			sb.append('\"');
			sb.append(':');
			sb.append(toJson(value));
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	static String list2Json(List<Object[]> list){
		if(list.size() == 0) return "[]";
		StringBuilder sb = new StringBuilder(list.size() << 4);
		sb.append('[');
		for(Object o: list){
			sb.append(toJson(o));
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String array2Json(Object[] array){
		if(array.length == 0) return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for(Object o: array){
			sb.append(toJson(o));
			sb.append(',');
		}
		// �������ӵ� ',' ��Ϊ ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String toJson(Object o){
		if(o == null) return "null";
		if(o instanceof Timestamp) return time2Json((Timestamp)o);
		if(o instanceof String) return string2Json((String)o);
		if(o instanceof Boolean) return boolean2Json((Boolean)o);
		if(o instanceof Number) return number2Json((Number)o);
		if(o instanceof Map) return map2Json((Map<String, Object>)o);
		if(o instanceof List) return list2Json((List<Object[]>)o);
		if(o instanceof Object[]) return array2Json((Object[])o);
		throw new RuntimeException("Unsupported type: " + o.getClass().getName());
	}

	/**
	 * JSON
	 * @param json
	 *        { "name" : { "first" : "Joe", "last" : "Sixpack" }, "gender" : "MALE", "verified" : false,
	 *        "userImage" : "Rm9vYmFyIQ==" }
	 * @return Map
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Map<String, Object> json2Map(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Map.class);
	}

	/**
	 * 
	 * @param json
	 *        [{"ID":"0","COL":{"money":33}},{"ID":"1","COL":{"money":123,"type":"����"}}]
	 * @return List
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<Map> json2List(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, List.class);
	}

	public static String list2JsonNew(List<Map<String, Object>> list) {
		if(list.size() == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder(list.size() << 4);
		sb.append('[');
		for(Map<String, Object> map: list){
			sb.append(JSONObject.fromObject(map));
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}
	
	public static String list2JsonN(List<Map<String, String>> list) {
		if(list.size() == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder(list.size() << 4);
		sb.append('[');
		for(Map<String, String> map: list){
			sb.append(JSONObject.fromObject(map));
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}
	
	
	 /**
     * xml转json
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlStr) throws DocumentException{
        Document doc= DocumentHelper.parseText(xmlStr);
        JSONObject json=new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     * @param element
     * @param json
     */
    public static void dom4j2Json(Element element,JSONObject json){
        //如果是属性
        for(Object o:element.attributes()){
            Attribute attr=(Attribute)o;
            if(!isEmpty(attr.getValue())){
                json.put("@"+attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl=element.elements();
        if(chdEl.isEmpty()&&!isEmpty(element.getText())){//如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }

        for(Element e:chdEl){//有子元素
            if(!e.elements().isEmpty()){//子元素也有子元素
                JSONObject chdjson=new JSONObject();
                dom4j2Json(e,chdjson);
                Object o=json.get(e.getName());
                if(o!=null){
                    JSONArray jsona=null;
                    if(o instanceof JSONObject){//如果此元素已存在,则转为jsonArray
                        JSONObject jsono=(JSONObject)o;
                        json.remove(e.getName());
                        jsona=new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if(o instanceof JSONArray){
                        jsona=(JSONArray)o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                }else{
                    if(!chdjson.isEmpty()){
                        json.put(e.getName(), chdjson);
                    }
                }


            }else{//子元素没有子元素
                for(Object o:element.attributes()){
                    Attribute attr=(Attribute)o;
                    if(!isEmpty(attr.getValue())){
                        json.put("@"+attr.getName(), attr.getValue());
                    }
                }
                if(!e.getText().isEmpty()){
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    public static boolean isEmpty(String str) {

        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
}
