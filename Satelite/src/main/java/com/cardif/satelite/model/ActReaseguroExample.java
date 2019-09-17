package com.cardif.satelite.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActReaseguroExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public ActReaseguroExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  protected abstract static class GeneratedCriteria {
    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
    }

    public boolean isValid() {
      return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      return criteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
    }

    public Criteria andCodReaseguroIsNull() {
      addCriterion("COD_REASEGURO is null");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroIsNotNull() {
      addCriterion("COD_REASEGURO is not null");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroEqualTo(Long value) {
      addCriterion("COD_REASEGURO =", value, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroNotEqualTo(Long value) {
      addCriterion("COD_REASEGURO <>", value, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroGreaterThan(Long value) {
      addCriterion("COD_REASEGURO >", value, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroGreaterThanOrEqualTo(Long value) {
      addCriterion("COD_REASEGURO >=", value, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroLessThan(Long value) {
      addCriterion("COD_REASEGURO <", value, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroLessThanOrEqualTo(Long value) {
      addCriterion("COD_REASEGURO <=", value, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroIn(List<Long> values) {
      addCriterion("COD_REASEGURO in", values, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroNotIn(List<Long> values) {
      addCriterion("COD_REASEGURO not in", values, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroBetween(Long value1, Long value2) {
      addCriterion("COD_REASEGURO between", value1, value2, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodReaseguroNotBetween(Long value1, Long value2) {
      addCriterion("COD_REASEGURO not between", value1, value2, "codReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroIsNull() {
      addCriterion("PCT_REASEGURO is null");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroIsNotNull() {
      addCriterion("PCT_REASEGURO is not null");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroEqualTo(BigDecimal value) {
      addCriterion("PCT_REASEGURO =", value, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroNotEqualTo(BigDecimal value) {
      addCriterion("PCT_REASEGURO <>", value, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroGreaterThan(BigDecimal value) {
      addCriterion("PCT_REASEGURO >", value, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("PCT_REASEGURO >=", value, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroLessThan(BigDecimal value) {
      addCriterion("PCT_REASEGURO <", value, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroLessThanOrEqualTo(BigDecimal value) {
      addCriterion("PCT_REASEGURO <=", value, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroIn(List<BigDecimal> values) {
      addCriterion("PCT_REASEGURO in", values, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroNotIn(List<BigDecimal> values) {
      addCriterion("PCT_REASEGURO not in", values, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("PCT_REASEGURO between", value1, value2, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andPctReaseguroNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("PCT_REASEGURO not between", value1, value2, "pctReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroIsNull() {
      addCriterion("FEC_INI_REASEGURO is null");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroIsNotNull() {
      addCriterion("FEC_INI_REASEGURO is not null");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroEqualTo(Date value) {
      addCriterion("FEC_INI_REASEGURO =", value, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroNotEqualTo(Date value) {
      addCriterion("FEC_INI_REASEGURO <>", value, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroGreaterThan(Date value) {
      addCriterion("FEC_INI_REASEGURO >", value, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_INI_REASEGURO >=", value, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroLessThan(Date value) {
      addCriterion("FEC_INI_REASEGURO <", value, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroLessThanOrEqualTo(Date value) {
      addCriterion("FEC_INI_REASEGURO <=", value, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroIn(List<Date> values) {
      addCriterion("FEC_INI_REASEGURO in", values, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroNotIn(List<Date> values) {
      addCriterion("FEC_INI_REASEGURO not in", values, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroBetween(Date value1, Date value2) {
      addCriterion("FEC_INI_REASEGURO between", value1, value2, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecIniReaseguroNotBetween(Date value1, Date value2) {
      addCriterion("FEC_INI_REASEGURO not between", value1, value2, "fecIniReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroIsNull() {
      addCriterion("FEC_FIN_REASEGURO is null");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroIsNotNull() {
      addCriterion("FEC_FIN_REASEGURO is not null");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroEqualTo(Date value) {
      addCriterion("FEC_FIN_REASEGURO =", value, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroNotEqualTo(Date value) {
      addCriterion("FEC_FIN_REASEGURO <>", value, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroGreaterThan(Date value) {
      addCriterion("FEC_FIN_REASEGURO >", value, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_FIN_REASEGURO >=", value, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroLessThan(Date value) {
      addCriterion("FEC_FIN_REASEGURO <", value, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroLessThanOrEqualTo(Date value) {
      addCriterion("FEC_FIN_REASEGURO <=", value, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroIn(List<Date> values) {
      addCriterion("FEC_FIN_REASEGURO in", values, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroNotIn(List<Date> values) {
      addCriterion("FEC_FIN_REASEGURO not in", values, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroBetween(Date value1, Date value2) {
      addCriterion("FEC_FIN_REASEGURO between", value1, value2, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andFecFinReaseguroNotBetween(Date value1, Date value2) {
      addCriterion("FEC_FIN_REASEGURO not between", value1, value2, "fecFinReaseguro");
      return (Criteria) this;
    }

    public Criteria andCodSistemaIsNull() {
      addCriterion("COD_SISTEMA is null");
      return (Criteria) this;
    }

    public Criteria andCodSistemaIsNotNull() {
      addCriterion("COD_SISTEMA is not null");
      return (Criteria) this;
    }

    public Criteria andCodSistemaEqualTo(Long value) {
      addCriterion("COD_SISTEMA =", value, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaNotEqualTo(Long value) {
      addCriterion("COD_SISTEMA <>", value, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaGreaterThan(Long value) {
      addCriterion("COD_SISTEMA >", value, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaGreaterThanOrEqualTo(Long value) {
      addCriterion("COD_SISTEMA >=", value, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaLessThan(Long value) {
      addCriterion("COD_SISTEMA <", value, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaLessThanOrEqualTo(Long value) {
      addCriterion("COD_SISTEMA <=", value, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaIn(List<Long> values) {
      addCriterion("COD_SISTEMA in", values, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaNotIn(List<Long> values) {
      addCriterion("COD_SISTEMA not in", values, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaBetween(Long value1, Long value2) {
      addCriterion("COD_SISTEMA between", value1, value2, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSistemaNotBetween(Long value1, Long value2) {
      addCriterion("COD_SISTEMA not between", value1, value2, "codSistema");
      return (Criteria) this;
    }

    public Criteria andCodSocioIsNull() {
      addCriterion("COD_SOCIO is null");
      return (Criteria) this;
    }

    public Criteria andCodSocioIsNotNull() {
      addCriterion("COD_SOCIO is not null");
      return (Criteria) this;
    }

    public Criteria andCodSocioEqualTo(String value) {
      addCriterion("COD_SOCIO =", value, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioNotEqualTo(String value) {
      addCriterion("COD_SOCIO <>", value, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioGreaterThan(String value) {
      addCriterion("COD_SOCIO >", value, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioGreaterThanOrEqualTo(String value) {
      addCriterion("COD_SOCIO >=", value, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioLessThan(String value) {
      addCriterion("COD_SOCIO <", value, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioLessThanOrEqualTo(String value) {
      addCriterion("COD_SOCIO <=", value, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioIn(List<String> values) {
      addCriterion("COD_SOCIO in", values, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioNotIn(List<String> values) {
      addCriterion("COD_SOCIO not in", values, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioBetween(String value1, String value2) {
      addCriterion("COD_SOCIO between", value1, value2, "codSocio");
      return (Criteria) this;
    }

    public Criteria andCodSocioNotBetween(String value1, String value2) {
      addCriterion("COD_SOCIO not between", value1, value2, "codSocio");
      return (Criteria) this;
    }

    public Criteria andSocioIsNull() {
      addCriterion("SOCIO is null");
      return (Criteria) this;
    }

    public Criteria andSocioIsNotNull() {
      addCriterion("SOCIO is not null");
      return (Criteria) this;
    }

    public Criteria andSocioEqualTo(String value) {
      addCriterion("SOCIO =", value, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioNotEqualTo(String value) {
      addCriterion("SOCIO <>", value, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioGreaterThan(String value) {
      addCriterion("SOCIO >", value, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioGreaterThanOrEqualTo(String value) {
      addCriterion("SOCIO >=", value, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioLessThan(String value) {
      addCriterion("SOCIO <", value, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioLessThanOrEqualTo(String value) {
      addCriterion("SOCIO <=", value, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioIn(List<String> values) {
      addCriterion("SOCIO in", values, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioNotIn(List<String> values) {
      addCriterion("SOCIO not in", values, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioBetween(String value1, String value2) {
      addCriterion("SOCIO between", value1, value2, "socio");
      return (Criteria) this;
    }

    public Criteria andSocioNotBetween(String value1, String value2) {
      addCriterion("SOCIO not between", value1, value2, "socio");
      return (Criteria) this;
    }

    public Criteria andCodProductoIsNull() {
      addCriterion("COD_PRODUCTO is null");
      return (Criteria) this;
    }

    public Criteria andCodProductoIsNotNull() {
      addCriterion("COD_PRODUCTO is not null");
      return (Criteria) this;
    }

    public Criteria andCodProductoEqualTo(Long value) {
      addCriterion("COD_PRODUCTO =", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoNotEqualTo(Long value) {
      addCriterion("COD_PRODUCTO <>", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoGreaterThan(Long value) {
      addCriterion("COD_PRODUCTO >", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoGreaterThanOrEqualTo(Long value) {
      addCriterion("COD_PRODUCTO >=", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoLessThan(Long value) {
      addCriterion("COD_PRODUCTO <", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoLessThanOrEqualTo(Long value) {
      addCriterion("COD_PRODUCTO <=", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoIn(List<Long> values) {
      addCriterion("COD_PRODUCTO in", values, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoNotIn(List<Long> values) {
      addCriterion("COD_PRODUCTO not in", values, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoBetween(Long value1, Long value2) {
      addCriterion("COD_PRODUCTO between", value1, value2, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoNotBetween(Long value1, Long value2) {
      addCriterion("COD_PRODUCTO not between", value1, value2, "codProducto");
      return (Criteria) this;
    }

    public Criteria andProductoIsNull() {
      addCriterion("PRODUCTO is null");
      return (Criteria) this;
    }

    public Criteria andProductoIsNotNull() {
      addCriterion("PRODUCTO is not null");
      return (Criteria) this;
    }

    public Criteria andProductoEqualTo(String value) {
      addCriterion("PRODUCTO =", value, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoNotEqualTo(String value) {
      addCriterion("PRODUCTO <>", value, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoGreaterThan(String value) {
      addCriterion("PRODUCTO >", value, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoGreaterThanOrEqualTo(String value) {
      addCriterion("PRODUCTO >=", value, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoLessThan(String value) {
      addCriterion("PRODUCTO <", value, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoLessThanOrEqualTo(String value) {
      addCriterion("PRODUCTO <=", value, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoIn(List<String> values) {
      addCriterion("PRODUCTO in", values, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoNotIn(List<String> values) {
      addCriterion("PRODUCTO not in", values, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoBetween(String value1, String value2) {
      addCriterion("PRODUCTO between", value1, value2, "producto");
      return (Criteria) this;
    }

    public Criteria andProductoNotBetween(String value1, String value2) {
      addCriterion("PRODUCTO not between", value1, value2, "producto");
      return (Criteria) this;
    }

    public Criteria andFecCreacionIsNull() {
      addCriterion("FEC_CREACION is null");
      return (Criteria) this;
    }

    public Criteria andFecCreacionIsNotNull() {
      addCriterion("FEC_CREACION is not null");
      return (Criteria) this;
    }

    public Criteria andFecCreacionEqualTo(Date value) {
      addCriterion("FEC_CREACION =", value, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionNotEqualTo(Date value) {
      addCriterion("FEC_CREACION <>", value, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionGreaterThan(Date value) {
      addCriterion("FEC_CREACION >", value, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_CREACION >=", value, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionLessThan(Date value) {
      addCriterion("FEC_CREACION <", value, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionLessThanOrEqualTo(Date value) {
      addCriterion("FEC_CREACION <=", value, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionIn(List<Date> values) {
      addCriterion("FEC_CREACION in", values, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionNotIn(List<Date> values) {
      addCriterion("FEC_CREACION not in", values, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionBetween(Date value1, Date value2) {
      addCriterion("FEC_CREACION between", value1, value2, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andFecCreacionNotBetween(Date value1, Date value2) {
      addCriterion("FEC_CREACION not between", value1, value2, "fecCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionIsNull() {
      addCriterion("USU_CREACION is null");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionIsNotNull() {
      addCriterion("USU_CREACION is not null");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionEqualTo(String value) {
      addCriterion("USU_CREACION =", value, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionNotEqualTo(String value) {
      addCriterion("USU_CREACION <>", value, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionGreaterThan(String value) {
      addCriterion("USU_CREACION >", value, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionGreaterThanOrEqualTo(String value) {
      addCriterion("USU_CREACION >=", value, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionLessThan(String value) {
      addCriterion("USU_CREACION <", value, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionLessThanOrEqualTo(String value) {
      addCriterion("USU_CREACION <=", value, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionIn(List<String> values) {
      addCriterion("USU_CREACION in", values, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionNotIn(List<String> values) {
      addCriterion("USU_CREACION not in", values, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionBetween(String value1, String value2) {
      addCriterion("USU_CREACION between", value1, value2, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andUsuCreacionNotBetween(String value1, String value2) {
      addCriterion("USU_CREACION not between", value1, value2, "usuCreacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionIsNull() {
      addCriterion("FEC_MODIFICACION is null");
      return (Criteria) this;
    }

    public Criteria andFecModificacionIsNotNull() {
      addCriterion("FEC_MODIFICACION is not null");
      return (Criteria) this;
    }

    public Criteria andFecModificacionEqualTo(Date value) {
      addCriterion("FEC_MODIFICACION =", value, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionNotEqualTo(Date value) {
      addCriterion("FEC_MODIFICACION <>", value, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionGreaterThan(Date value) {
      addCriterion("FEC_MODIFICACION >", value, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_MODIFICACION >=", value, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionLessThan(Date value) {
      addCriterion("FEC_MODIFICACION <", value, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionLessThanOrEqualTo(Date value) {
      addCriterion("FEC_MODIFICACION <=", value, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionIn(List<Date> values) {
      addCriterion("FEC_MODIFICACION in", values, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionNotIn(List<Date> values) {
      addCriterion("FEC_MODIFICACION not in", values, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionBetween(Date value1, Date value2) {
      addCriterion("FEC_MODIFICACION between", value1, value2, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andFecModificacionNotBetween(Date value1, Date value2) {
      addCriterion("FEC_MODIFICACION not between", value1, value2, "fecModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionIsNull() {
      addCriterion("USU_MODIFICACION is null");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionIsNotNull() {
      addCriterion("USU_MODIFICACION is not null");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionEqualTo(String value) {
      addCriterion("USU_MODIFICACION =", value, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionNotEqualTo(String value) {
      addCriterion("USU_MODIFICACION <>", value, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionGreaterThan(String value) {
      addCriterion("USU_MODIFICACION >", value, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionGreaterThanOrEqualTo(String value) {
      addCriterion("USU_MODIFICACION >=", value, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionLessThan(String value) {
      addCriterion("USU_MODIFICACION <", value, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionLessThanOrEqualTo(String value) {
      addCriterion("USU_MODIFICACION <=", value, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionIn(List<String> values) {
      addCriterion("USU_MODIFICACION in", values, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionNotIn(List<String> values) {
      addCriterion("USU_MODIFICACION not in", values, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionBetween(String value1, String value2) {
      addCriterion("USU_MODIFICACION between", value1, value2, "usuModificacion");
      return (Criteria) this;
    }

    public Criteria andUsuModificacionNotBetween(String value1, String value2) {
      addCriterion("USU_MODIFICACION not between", value1, value2, "usuModificacion");
      return (Criteria) this;
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated do_not_delete_during_merge Mon Jan 06 10:12:06 COT 2014
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.ACT_REASEGURO
   *
   * @mbggenerated Mon Jan 06 10:12:06 COT 2014
   */
  public static class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      } else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}