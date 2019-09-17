package com.cardif.satelite.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

public class RichFacesFirefox11Filter implements Filter {

  static final Logger LOGGER = Logger.getLogger(RichFacesFirefox11Filter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {
      @Override
      public String getRequestURI() {
        try {

          String url = URLDecoder.decode(super.getRequestURI(), "UTF-8");
          LOGGER.info(url);
          return url;
        } catch (UnsupportedEncodingException e) {

          throw new IllegalStateException("Cannot decode request URI.", e);
        }
      }
    }, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // do nothing
  }

  @Override
  public void destroy() {
    // do nothing
  }

}