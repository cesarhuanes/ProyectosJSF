package com.cardif.satelite.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SiniPolizaExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public SiniPolizaExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
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

    public Criteria andNroSiniestroIsNull() {
      addCriterion("NRO_SINIESTRO is null");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroIsNotNull() {
      addCriterion("NRO_SINIESTRO is not null");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroEqualTo(String value) {
      addCriterion("NRO_SINIESTRO =", value, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroNotEqualTo(String value) {
      addCriterion("NRO_SINIESTRO <>", value, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroGreaterThan(String value) {
      addCriterion("NRO_SINIESTRO >", value, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroGreaterThanOrEqualTo(String value) {
      addCriterion("NRO_SINIESTRO >=", value, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroLessThan(String value) {
      addCriterion("NRO_SINIESTRO <", value, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroLessThanOrEqualTo(String value) {
      addCriterion("NRO_SINIESTRO <=", value, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroIn(List<String> values) {
      addCriterion("NRO_SINIESTRO in", values, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroNotIn(List<String> values) {
      addCriterion("NRO_SINIESTRO not in", values, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroBetween(String value1, String value2) {
      addCriterion("NRO_SINIESTRO between", value1, value2, "nroSiniestro");
      return (Criteria) this;
    }

    public Criteria andNroSiniestroNotBetween(String value1, String value2) {
      addCriterion("NRO_SINIESTRO not between", value1, value2, "nroSiniestro");
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

    public Criteria andNroProductoIsNull() {
      addCriterion("NRO_PRODUCTO is null");
      return (Criteria) this;
    }

    public Criteria andNroProductoIsNotNull() {
      addCriterion("NRO_PRODUCTO is not null");
      return (Criteria) this;
    }

    public Criteria andNroProductoEqualTo(String value) {
      addCriterion("NRO_PRODUCTO =", value, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoNotEqualTo(String value) {
      addCriterion("NRO_PRODUCTO <>", value, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoGreaterThan(String value) {
      addCriterion("NRO_PRODUCTO >", value, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoGreaterThanOrEqualTo(String value) {
      addCriterion("NRO_PRODUCTO >=", value, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoLessThan(String value) {
      addCriterion("NRO_PRODUCTO <", value, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoLessThanOrEqualTo(String value) {
      addCriterion("NRO_PRODUCTO <=", value, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoIn(List<String> values) {
      addCriterion("NRO_PRODUCTO in", values, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoNotIn(List<String> values) {
      addCriterion("NRO_PRODUCTO not in", values, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoBetween(String value1, String value2) {
      addCriterion("NRO_PRODUCTO between", value1, value2, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andNroProductoNotBetween(String value1, String value2) {
      addCriterion("NRO_PRODUCTO not between", value1, value2, "nroProducto");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaIsNull() {
      addCriterion("PLAN_POLIZA is null");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaIsNotNull() {
      addCriterion("PLAN_POLIZA is not null");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaEqualTo(String value) {
      addCriterion("PLAN_POLIZA =", value, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaNotEqualTo(String value) {
      addCriterion("PLAN_POLIZA <>", value, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaGreaterThan(String value) {
      addCriterion("PLAN_POLIZA >", value, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaGreaterThanOrEqualTo(String value) {
      addCriterion("PLAN_POLIZA >=", value, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaLessThan(String value) {
      addCriterion("PLAN_POLIZA <", value, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaLessThanOrEqualTo(String value) {
      addCriterion("PLAN_POLIZA <=", value, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaIn(List<String> values) {
      addCriterion("PLAN_POLIZA in", values, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaNotIn(List<String> values) {
      addCriterion("PLAN_POLIZA not in", values, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaBetween(String value1, String value2) {
      addCriterion("PLAN_POLIZA between", value1, value2, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andPlanPolizaNotBetween(String value1, String value2) {
      addCriterion("PLAN_POLIZA not between", value1, value2, "planPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaIsNull() {
      addCriterion("NRO_POLIZA is null");
      return (Criteria) this;
    }

    public Criteria andNroPolizaIsNotNull() {
      addCriterion("NRO_POLIZA is not null");
      return (Criteria) this;
    }

    public Criteria andNroPolizaEqualTo(String value) {
      addCriterion("NRO_POLIZA =", value, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaNotEqualTo(String value) {
      addCriterion("NRO_POLIZA <>", value, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaGreaterThan(String value) {
      addCriterion("NRO_POLIZA >", value, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaGreaterThanOrEqualTo(String value) {
      addCriterion("NRO_POLIZA >=", value, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaLessThan(String value) {
      addCriterion("NRO_POLIZA <", value, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaLessThanOrEqualTo(String value) {
      addCriterion("NRO_POLIZA <=", value, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaIn(List<String> values) {
      addCriterion("NRO_POLIZA in", values, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaNotIn(List<String> values) {
      addCriterion("NRO_POLIZA not in", values, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaBetween(String value1, String value2) {
      addCriterion("NRO_POLIZA between", value1, value2, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroPolizaNotBetween(String value1, String value2) {
      addCriterion("NRO_POLIZA not between", value1, value2, "nroPoliza");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoIsNull() {
      addCriterion("NRO_CERTIFICADO is null");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoIsNotNull() {
      addCriterion("NRO_CERTIFICADO is not null");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoEqualTo(String value) {
      addCriterion("NRO_CERTIFICADO =", value, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoNotEqualTo(String value) {
      addCriterion("NRO_CERTIFICADO <>", value, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoGreaterThan(String value) {
      addCriterion("NRO_CERTIFICADO >", value, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoGreaterThanOrEqualTo(String value) {
      addCriterion("NRO_CERTIFICADO >=", value, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoLessThan(String value) {
      addCriterion("NRO_CERTIFICADO <", value, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoLessThanOrEqualTo(String value) {
      addCriterion("NRO_CERTIFICADO <=", value, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoIn(List<String> values) {
      addCriterion("NRO_CERTIFICADO in", values, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoNotIn(List<String> values) {
      addCriterion("NRO_CERTIFICADO not in", values, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoBetween(String value1, String value2) {
      addCriterion("NRO_CERTIFICADO between", value1, value2, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andNroCertificadoNotBetween(String value1, String value2) {
      addCriterion("NRO_CERTIFICADO not between", value1, value2, "nroCertificado");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaIsNull() {
      addCriterion("FEC_INI_VIGENCIA is null");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaIsNotNull() {
      addCriterion("FEC_INI_VIGENCIA is not null");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaEqualTo(Date value) {
      addCriterion("FEC_INI_VIGENCIA =", value, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaNotEqualTo(Date value) {
      addCriterion("FEC_INI_VIGENCIA <>", value, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaGreaterThan(Date value) {
      addCriterion("FEC_INI_VIGENCIA >", value, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_INI_VIGENCIA >=", value, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaLessThan(Date value) {
      addCriterion("FEC_INI_VIGENCIA <", value, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaLessThanOrEqualTo(Date value) {
      addCriterion("FEC_INI_VIGENCIA <=", value, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaIn(List<Date> values) {
      addCriterion("FEC_INI_VIGENCIA in", values, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaNotIn(List<Date> values) {
      addCriterion("FEC_INI_VIGENCIA not in", values, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaBetween(Date value1, Date value2) {
      addCriterion("FEC_INI_VIGENCIA between", value1, value2, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecIniVigenciaNotBetween(Date value1, Date value2) {
      addCriterion("FEC_INI_VIGENCIA not between", value1, value2, "fecIniVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaIsNull() {
      addCriterion("FEC_FIN_VIGENCIA is null");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaIsNotNull() {
      addCriterion("FEC_FIN_VIGENCIA is not null");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaEqualTo(Date value) {
      addCriterion("FEC_FIN_VIGENCIA =", value, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaNotEqualTo(Date value) {
      addCriterion("FEC_FIN_VIGENCIA <>", value, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaGreaterThan(Date value) {
      addCriterion("FEC_FIN_VIGENCIA >", value, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_FIN_VIGENCIA >=", value, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaLessThan(Date value) {
      addCriterion("FEC_FIN_VIGENCIA <", value, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaLessThanOrEqualTo(Date value) {
      addCriterion("FEC_FIN_VIGENCIA <=", value, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaIn(List<Date> values) {
      addCriterion("FEC_FIN_VIGENCIA in", values, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaNotIn(List<Date> values) {
      addCriterion("FEC_FIN_VIGENCIA not in", values, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaBetween(Date value1, Date value2) {
      addCriterion("FEC_FIN_VIGENCIA between", value1, value2, "fecFinVigencia");
      return (Criteria) this;
    }

    public Criteria andFecFinVigenciaNotBetween(Date value1, Date value2) {
      addCriterion("FEC_FIN_VIGENCIA not between", value1, value2, "fecFinVigencia");
      return (Criteria) this;
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated do_not_delete_during_merge Mon Jul 21 21:14:50 COT 2014
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.SINI_POLIZA
   *
   * @mbggenerated Mon Jul 21 21:14:50 COT 2014
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