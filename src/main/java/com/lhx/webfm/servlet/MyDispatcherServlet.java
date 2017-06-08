package com.lhx.webfm.servlet;

import com.alibaba.fastjson.JSONObject;
import com.lhx.webfm.bean.MyData;
import com.lhx.webfm.bean.MyHandler;
import com.lhx.webfm.bean.MyParam;
import com.lhx.webfm.bean.MyView;
import com.lhx.webfm.constant.ConfigConst;
import com.lhx.webfm.helper.BeanHelper;
import com.lhx.webfm.helper.ControllerHelper;
import com.lhx.webfm.helper.LoaderHelper;
import com.lhx.webfm.util.MyCodeUtil;
import com.lhx.webfm.util.MyStreamUtil;
import com.lhx.webfm.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(urlPatterns = {"/*"},loadOnStartup = 0)
public class MyDispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关Helper类
        LoaderHelper.init();
        //获取ServletContext对象(用于注册servlet)
        ServletContext sc = config.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspServlet = sc.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigConst.JSP_PATH+"*");
        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet=sc.getServletRegistration("default");
        defaultServlet.addMapping(ConfigConst.ASSET_PATH+"*");
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取请求方法和请求路径
        String requestMethod=req.getMethod();
        String requestPath=req.getPathInfo();
        //获取action处理器
        MyHandler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            //获取Controller类及Bean实例
            Class<?> ctrlClass = handler.getCtrlClass();
            Object ctrlBean = BeanHelper.getBean(ctrlClass);

            //创建请求参数对象
            Map<String,Object> paramMap=new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName=paramNames.nextElement();
                String paramValue=req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }

            String body= MyCodeUtil.decodeURL(MyStreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params=body.split("&");
                for (String param : params) {
                    String[] array = param.split("=");
                    String paramName=array[0];
                    String paramValue=array[1];
                    paramMap.put(paramName,paramValue);
                }
            }
            MyParam myParam=new MyParam(paramMap);

            //调用action方法
            Method actionMethod = handler.getActionMethod();
            //这里传参数尚未解决myParam
//            Object result = ReflectionUtil.invokeMethod(ctrlBean, actionMethod,myParam);
            Object result = ReflectionUtil.invokeMethod(ctrlBean, actionMethod,new Object[]{});
            //处理action方法返回值
            if(result instanceof MyView){
                //返回jsp页面
                MyView view= (MyView) result;
                String path = view.getPath();
                if (path.startsWith("/")) {
                    res.sendRedirect(req.getContextPath()+path);
                }else {
                    Map<String, Object> model = view.getModel();
                    for (String key : model.keySet()) {
                        req.setAttribute(key,model.get(key));
                    }
                    req.getRequestDispatcher(ConfigConst.JSP_PATH+path).forward(req,res);
                }
            }else if(result instanceof MyData){
                //返回json数据
                MyData data= (MyData) result;
                Object model = data.getModel();
                if (model != null) {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    PrintWriter writer = res.getWriter();
                    writer.write(JSONObject.toJSONString(model));
                    writer.flush();
                    writer.close();
                }
            }


        }

    }
}
