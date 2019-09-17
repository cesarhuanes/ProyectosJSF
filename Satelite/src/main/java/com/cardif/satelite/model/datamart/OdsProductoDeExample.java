package com.cardif.satelite.model.datamart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OdsProductoDeExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public OdsProductoDeExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
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

    protected void addCriterionForJDBCDate(String condition, Date value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      addCriterion(condition, new java.sql.Date(value.getTime()), property);
    }

    protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
      if (values == null || values.size() == 0) {
        throw new RuntimeException("Value list for " + property + " cannot be null or empty");
      }
      List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
      Iterator<Date> iter = values.iterator();
      while (iter.hasNext()) {
        dateList.add(new java.sql.Date(iter.next().getTime()));
      }
      addCriterion(condition, dateList, property);
    }

    protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
    }

    public Criteria andDesProductoIsNull() {
      addCriterion("DES_PRODUCTO is null");
      return (Criteria) this;
    }

    public Criteria andDesProductoIsNotNull() {
      addCriterion("DES_PRODUCTO is not null");
      return (Criteria) this;
    }

    public Criteria andDesProductoEqualTo(String value) {
      addCriterion("DES_PRODUCTO =", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoNotEqualTo(String value) {
      addCriterion("DES_PRODUCTO <>", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoGreaterThan(String value) {
      addCriterion("DES_PRODUCTO >", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoGreaterThanOrEqualTo(String value) {
      addCriterion("DES_PRODUCTO >=", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoLessThan(String value) {
      addCriterion("DES_PRODUCTO <", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoLessThanOrEqualTo(String value) {
      addCriterion("DES_PRODUCTO <=", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoLike(String value) {
      addCriterion("DES_PRODUCTO like", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoNotLike(String value) {
      addCriterion("DES_PRODUCTO not like", value, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoIn(List<String> values) {
      addCriterion("DES_PRODUCTO in", values, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoNotIn(List<String> values) {
      addCriterion("DES_PRODUCTO not in", values, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoBetween(String value1, String value2) {
      addCriterion("DES_PRODUCTO between", value1, value2, "desProducto");
      return (Criteria) this;
    }

    public Criteria andDesProductoNotBetween(String value1, String value2) {
      addCriterion("DES_PRODUCTO not between", value1, value2, "desProducto");
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

    public Criteria andCodProductoEqualTo(BigDecimal value) {
      addCriterion("COD_PRODUCTO =", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoNotEqualTo(BigDecimal value) {
      addCriterion("COD_PRODUCTO <>", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoGreaterThan(BigDecimal value) {
      addCriterion("COD_PRODUCTO >", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("COD_PRODUCTO >=", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoLessThan(BigDecimal value) {
      addCriterion("COD_PRODUCTO <", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoLessThanOrEqualTo(BigDecimal value) {
      addCriterion("COD_PRODUCTO <=", value, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoIn(List<BigDecimal> values) {
      addCriterion("COD_PRODUCTO in", values, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoNotIn(List<BigDecimal> values) {
      addCriterion("COD_PRODUCTO not in", values, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COD_PRODUCTO between", value1, value2, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodProductoNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("COD_PRODUCTO not between", value1, value2, "codProducto");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsIsNull() {
      addCriterion("COD_RIESGOSBS is null");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsIsNotNull() {
      addCriterion("COD_RIESGOSBS is not null");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsEqualTo(Double value) {
      addCriterion("COD_RIESGOSBS =", value, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsNotEqualTo(Double value) {
      addCriterion("COD_RIESGOSBS <>", value, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsGreaterThan(Double value) {
      addCriterion("COD_RIESGOSBS >", value, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsGreaterThanOrEqualTo(Double value) {
      addCriterion("COD_RIESGOSBS >=", value, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsLessThan(Double value) {
      addCriterion("COD_RIESGOSBS <", value, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsLessThanOrEqualTo(Double value) {
      addCriterion("COD_RIESGOSBS <=", value, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsIn(List<Double> values) {
      addCriterion("COD_RIESGOSBS in", values, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsNotIn(List<Double> values) {
      addCriterion("COD_RIESGOSBS not in", values, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsBetween(Double value1, Double value2) {
      addCriterion("COD_RIESGOSBS between", value1, value2, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodRiesgosbsNotBetween(Double value1, Double value2) {
      addCriterion("COD_RIESGOSBS not between", value1, value2, "codRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsIsNull() {
      addCriterion("DES_RIESGOSBS is null");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsIsNotNull() {
      addCriterion("DES_RIESGOSBS is not null");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsEqualTo(String value) {
      addCriterion("DES_RIESGOSBS =", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsNotEqualTo(String value) {
      addCriterion("DES_RIESGOSBS <>", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsGreaterThan(String value) {
      addCriterion("DES_RIESGOSBS >", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsGreaterThanOrEqualTo(String value) {
      addCriterion("DES_RIESGOSBS >=", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsLessThan(String value) {
      addCriterion("DES_RIESGOSBS <", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsLessThanOrEqualTo(String value) {
      addCriterion("DES_RIESGOSBS <=", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsLike(String value) {
      addCriterion("DES_RIESGOSBS like", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsNotLike(String value) {
      addCriterion("DES_RIESGOSBS not like", value, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsIn(List<String> values) {
      addCriterion("DES_RIESGOSBS in", values, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsNotIn(List<String> values) {
      addCriterion("DES_RIESGOSBS not in", values, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsBetween(String value1, String value2) {
      addCriterion("DES_RIESGOSBS between", value1, value2, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andDesRiesgosbsNotBetween(String value1, String value2) {
      addCriterion("DES_RIESGOSBS not between", value1, value2, "desRiesgosbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsIsNull() {
      addCriterion("COD_SBS is null");
      return (Criteria) this;
    }

    public Criteria andCodSbsIsNotNull() {
      addCriterion("COD_SBS is not null");
      return (Criteria) this;
    }

    public Criteria andCodSbsEqualTo(String value) {
      addCriterion("COD_SBS =", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsNotEqualTo(String value) {
      addCriterion("COD_SBS <>", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsGreaterThan(String value) {
      addCriterion("COD_SBS >", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsGreaterThanOrEqualTo(String value) {
      addCriterion("COD_SBS >=", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsLessThan(String value) {
      addCriterion("COD_SBS <", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsLessThanOrEqualTo(String value) {
      addCriterion("COD_SBS <=", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsLike(String value) {
      addCriterion("COD_SBS like", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsNotLike(String value) {
      addCriterion("COD_SBS not like", value, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsIn(List<String> values) {
      addCriterion("COD_SBS in", values, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsNotIn(List<String> values) {
      addCriterion("COD_SBS not in", values, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsBetween(String value1, String value2) {
      addCriterion("COD_SBS between", value1, value2, "codSbs");
      return (Criteria) this;
    }

    public Criteria andCodSbsNotBetween(String value1, String value2) {
      addCriterion("COD_SBS not between", value1, value2, "codSbs");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoIsNull() {
      addCriterion("FEC_LANZAMIENTO is null");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoIsNotNull() {
      addCriterion("FEC_LANZAMIENTO is not null");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoEqualTo(Date value) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO =", value, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoNotEqualTo(Date value) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO <>", value, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoGreaterThan(Date value) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO >", value, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO >=", value, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoLessThan(Date value) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO <", value, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO <=", value, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoIn(List<Date> values) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO in", values, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoNotIn(List<Date> values) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO not in", values, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO between", value1, value2, "fecLanzamiento");
      return (Criteria) this;
    }

    public Criteria andFecLanzamientoNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("FEC_LANZAMIENTO not between", value1, value2, "fecLanzamiento");
      return (Criteria) this;
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated do_not_delete_during_merge Tue Feb 04 14:23:56 COT 2014
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
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