package com.cy.community.advice;

import com.alibaba.fastjson.JSON;
import com.cy.community.dto.ResultDTO;
import com.cy.community.exception.CustomizeErrorCode;
import com.cy.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cy
 * @since 2019-11-02 9:19
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO ;

            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException)e);
            }else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200 );
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
            }catch (IOException ioe){

            }
            return  null;
        }


        if(e instanceof CustomizeException){

            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
        }
        return new ModelAndView("error");
    }


}
