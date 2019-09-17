package com.cardif.satelite.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SiniRentaHospExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public SiniRentaHospExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
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

    public Criteria andCanDiasCnIsNull() {
      addCriterion("CAN_DIAS_CN is null");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnIsNotNull() {
      addCriterion("CAN_DIAS_CN is not null");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnEqualTo(Short value) {
      addCriterion("CAN_DIAS_CN =", value, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnNotEqualTo(Short value) {
      addCriterion("CAN_DIAS_CN <>", value, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnGreaterThan(Short value) {
      addCriterion("CAN_DIAS_CN >", value, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnGreaterThanOrEqualTo(Short value) {
      addCriterion("CAN_DIAS_CN >=", value, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnLessThan(Short value) {
      addCriterion("CAN_DIAS_CN <", value, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnLessThanOrEqualTo(Short value) {
      addCriterion("CAN_DIAS_CN <=", value, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnIn(List<Short> values) {
      addCriterion("CAN_DIAS_CN in", values, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnNotIn(List<Short> values) {
      addCriterion("CAN_DIAS_CN not in", values, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnBetween(Short value1, Short value2) {
      addCriterion("CAN_DIAS_CN between", value1, value2, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasCnNotBetween(Short value1, Short value2) {
      addCriterion("CAN_DIAS_CN not between", value1, value2, "canDiasCn");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciIsNull() {
      addCriterion("CAN_DIAS_UCI is null");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciIsNotNull() {
      addCriterion("CAN_DIAS_UCI is not null");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciEqualTo(Short value) {
      addCriterion("CAN_DIAS_UCI =", value, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciNotEqualTo(Short value) {
      addCriterion("CAN_DIAS_UCI <>", value, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciGreaterThan(Short value) {
      addCriterion("CAN_DIAS_UCI >", value, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciGreaterThanOrEqualTo(Short value) {
      addCriterion("CAN_DIAS_UCI >=", value, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciLessThan(Short value) {
      addCriterion("CAN_DIAS_UCI <", value, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciLessThanOrEqualTo(Short value) {
      addCriterion("CAN_DIAS_UCI <=", value, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciIn(List<Short> values) {
      addCriterion("CAN_DIAS_UCI in", values, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciNotIn(List<Short> values) {
      addCriterion("CAN_DIAS_UCI not in", values, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciBetween(Short value1, Short value2) {
      addCriterion("CAN_DIAS_UCI between", value1, value2, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andCanDiasUciNotBetween(Short value1, Short value2) {
      addCriterion("CAN_DIAS_UCI not between", value1, value2, "canDiasUci");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidIsNull() {
      addCriterion("IMP_CALC_LIQUID is null");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidIsNotNull() {
      addCriterion("IMP_CALC_LIQUID is not null");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidEqualTo(BigDecimal value) {
      addCriterion("IMP_CALC_LIQUID =", value, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidNotEqualTo(BigDecimal value) {
      addCriterion("IMP_CALC_LIQUID <>", value, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidGreaterThan(BigDecimal value) {
      addCriterion("IMP_CALC_LIQUID >", value, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("IMP_CALC_LIQUID >=", value, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidLessThan(BigDecimal value) {
      addCriterion("IMP_CALC_LIQUID <", value, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidLessThanOrEqualTo(BigDecimal value) {
      addCriterion("IMP_CALC_LIQUID <=", value, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidIn(List<BigDecimal> values) {
      addCriterion("IMP_CALC_LIQUID in", values, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidNotIn(List<BigDecimal> values) {
      addCriterion("IMP_CALC_LIQUID not in", values, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("IMP_CALC_LIQUID between", value1, value2, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andImpCalcLiquidNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("IMP_CALC_LIQUID not between", value1, value2, "impCalcLiquid");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalIsNull() {
      addCriterion("CAN_DIAS_HOSPITAL is null");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalIsNotNull() {
      addCriterion("CAN_DIAS_HOSPITAL is not null");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalEqualTo(Short value) {
      addCriterion("CAN_DIAS_HOSPITAL =", value, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalNotEqualTo(Short value) {
      addCriterion("CAN_DIAS_HOSPITAL <>", value, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalGreaterThan(Short value) {
      addCriterion("CAN_DIAS_HOSPITAL >", value, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalGreaterThanOrEqualTo(Short value) {
      addCriterion("CAN_DIAS_HOSPITAL >=", value, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalLessThan(Short value) {
      addCriterion("CAN_DIAS_HOSPITAL <", value, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalLessThanOrEqualTo(Short value) {
      addCriterion("CAN_DIAS_HOSPITAL <=", value, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalIn(List<Short> values) {
      addCriterion("CAN_DIAS_HOSPITAL in", values, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalNotIn(List<Short> values) {
      addCriterion("CAN_DIAS_HOSPITAL not in", values, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalBetween(Short value1, Short value2) {
      addCriterion("CAN_DIAS_HOSPITAL between", value1, value2, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andCanDiasHospitalNotBetween(Short value1, Short value2) {
      addCriterion("CAN_DIAS_HOSPITAL not between", value1, value2, "canDiasHospital");
      return (Criteria) this;
    }

    public Criteria andExcepcionIsNull() {
      addCriterion("EXCEPCION is null");
      return (Criteria) this;
    }

    public Criteria andExcepcionIsNotNull() {
      addCriterion("EXCEPCION is not null");
      return (Criteria) this;
    }

    public Criteria andExcepcionEqualTo(String value) {
      addCriterion("EXCEPCION =", value, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionNotEqualTo(String value) {
      addCriterion("EXCEPCION <>", value, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionGreaterThan(String value) {
      addCriterion("EXCEPCION >", value, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionGreaterThanOrEqualTo(String value) {
      addCriterion("EXCEPCION >=", value, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionLessThan(String value) {
      addCriterion("EXCEPCION <", value, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionLessThanOrEqualTo(String value) {
      addCriterion("EXCEPCION <=", value, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionIn(List<String> values) {
      addCriterion("EXCEPCION in", values, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionNotIn(List<String> values) {
      addCriterion("EXCEPCION not in", values, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionBetween(String value1, String value2) {
      addCriterion("EXCEPCION between", value1, value2, "excepcion");
      return (Criteria) this;
    }

    public Criteria andExcepcionNotBetween(String value1, String value2) {
      addCriterion("EXCEPCION not between", value1, value2, "excepcion");
      return (Criteria) this;
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated do_not_delete_during_merge Mon Jul 21 17:21:12 COT 2014
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.SINI_RENTA_HOSP
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
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