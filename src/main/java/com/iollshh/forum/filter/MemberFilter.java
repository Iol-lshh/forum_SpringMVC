package com.iollshh.forum.filter;

import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberFilter implements Filter {

//    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String httpUri = req.getRequestURI();
        String httpMethod = req.getMethod();
        String authorization = req.getHeader("authorization");

        //1. filter write request
        if(httpUri.matches(".*/new")
                ||httpMethod.equals("POST")
                ||httpMethod.equals("PUT")
                ||httpMethod.equals("DELETE")) {
            if (!(authorization.isEmpty())) {

                //2. 체크 데이터 가공
                String[] authorizationArr = authorization.split(" ");
                String accountType = authorizationArr[0];

                //3. accountType 확인
                List<String> accountTypeList = new ArrayList<>(Arrays.asList("MEMBER", "CUSTOMER"));
                long res = accountTypeList.stream().filter(e->e.equals(accountType)).count();
                if(res>0) {
                    chain.doFilter(request, response);
                }
            }
        }else{
            chain.doFilter(request,response);
        }
    }
}


