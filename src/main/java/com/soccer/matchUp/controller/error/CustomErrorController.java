package com.soccer.matchUp.controller.error;

import com.soccer.matchUp.dto.response.ApiErrorResponse;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addAllObjects(
                Map.of(
                        "status", errorCode.getHttpStatus(),
                        "message", errorCode.getHttpStatus()
                )
        );
        return modelAndView;
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiErrorResponse> error(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiErrorResponse.of(false, errorCode));
    }
}
