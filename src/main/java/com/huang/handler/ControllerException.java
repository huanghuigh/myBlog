package com.huang.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author guangtou
 * @create 2020--02--05--16:51
 */
@ControllerAdvice
public class ControllerException {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 捕获错误，跳转至错误页面
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception
    {
//        打印错误日志
        logger.error("Request URL :{} , Exception : {}",request.getRequestURL(),e.getMessage());
//        如果此异常不含状态码
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
//        返回错误视图
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL())
          .addObject("exception",e)
          .setViewName("error/error");
        return mv;
    }
}
