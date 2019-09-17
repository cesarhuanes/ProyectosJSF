package com.cardif.satelite.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CabLibroExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public CabLibroExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
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

    public Criteria andSecArchivoIsNull() {
      addCriterion("SEC_ARCHIVO is null");
      return (Criteria) this;
    }

    public Criteria andSecArchivoIsNotNull() {
      addCriterion("SEC_ARCHIVO is not null");
      return (Criteria) this;
    }

    public Criteria andSecArchivoEqualTo(Long value) {
      addCriterion("SEC_ARCHIVO =", value, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoNotEqualTo(Long value) {
      addCriterion("SEC_ARCHIVO <>", value, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoGreaterThan(Long value) {
      addCriterion("SEC_ARCHIVO >", value, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoGreaterThanOrEqualTo(Long value) {
      addCriterion("SEC_ARCHIVO >=", value, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoLessThan(Long value) {
      addCriterion("SEC_ARCHIVO <", value, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoLessThanOrEqualTo(Long value) {
      addCriterion("SEC_ARCHIVO <=", value, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoIn(List<Long> values) {
      addCriterion("SEC_ARCHIVO in", values, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoNotIn(List<Long> values) {
      addCriterion("SEC_ARCHIVO not in", values, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoBetween(Long value1, Long value2) {
      addCriterion("SEC_ARCHIVO between", value1, value2, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andSecArchivoNotBetween(Long value1, Long value2) {
      addCriterion("SEC_ARCHIVO not between", value1, value2, "secArchivo");
      return (Criteria) this;
    }

    public Criteria andPeriodoIsNull() {
      addCriterion("PERIODO is null");
      return (Criteria) this;
    }

    public Criteria andPeriodoIsNotNull() {
      addCriterion("PERIODO is not null");
      return (Criteria) this;
    }

    public Criteria andPeriodoEqualTo(Integer value) {
      addCriterion("PERIODO =", value, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoNotEqualTo(Integer value) {
      addCriterion("PERIODO <>", value, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoGreaterThan(Integer value) {
      addCriterion("PERIODO >", value, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoGreaterThanOrEqualTo(Integer value) {
      addCriterion("PERIODO >=", value, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoLessThan(Integer value) {
      addCriterion("PERIODO <", value, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoLessThanOrEqualTo(Integer value) {
      addCriterion("PERIODO <=", value, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoIn(List<Integer> values) {
      addCriterion("PERIODO in", values, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoNotIn(List<Integer> values) {
      addCriterion("PERIODO not in", values, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoBetween(Integer value1, Integer value2) {
      addCriterion("PERIODO between", value1, value2, "periodo");
      return (Criteria) this;
    }

    public Criteria andPeriodoNotBetween(Integer value1, Integer value2) {
      addCriterion("PERIODO not between", value1, value2, "periodo");
      return (Criteria) this;
    }

    public Criteria andCodCiaIsNull() {
      addCriterion("COD_CIA is null");
      return (Criteria) this;
    }

    public Criteria andCodCiaIsNotNull() {
      addCriterion("COD_CIA is not null");
      return (Criteria) this;
    }

    public Criteria andCodCiaEqualTo(String value) {
      addCriterion("COD_CIA =", value, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaNotEqualTo(String value) {
      addCriterion("COD_CIA <>", value, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaGreaterThan(String value) {
      addCriterion("COD_CIA >", value, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaGreaterThanOrEqualTo(String value) {
      addCriterion("COD_CIA >=", value, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaLessThan(String value) {
      addCriterion("COD_CIA <", value, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaLessThanOrEqualTo(String value) {
      addCriterion("COD_CIA <=", value, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaIn(List<String> values) {
      addCriterion("COD_CIA in", values, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaNotIn(List<String> values) {
      addCriterion("COD_CIA not in", values, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaBetween(String value1, String value2) {
      addCriterion("COD_CIA between", value1, value2, "codCia");
      return (Criteria) this;
    }

    public Criteria andCodCiaNotBetween(String value1, String value2) {
      addCriterion("COD_CIA not between", value1, value2, "codCia");
      return (Criteria) this;
    }

    public Criteria andTipLibroIsNull() {
      addCriterion("TIP_LIBRO is null");
      return (Criteria) this;
    }

    public Criteria andTipLibroIsNotNull() {
      addCriterion("TIP_LIBRO is not null");
      return (Criteria) this;
    }

    public Criteria andTipLibroEqualTo(String value) {
      addCriterion("TIP_LIBRO =", value, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroNotEqualTo(String value) {
      addCriterion("TIP_LIBRO <>", value, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroGreaterThan(String value) {
      addCriterion("TIP_LIBRO >", value, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroGreaterThanOrEqualTo(String value) {
      addCriterion("TIP_LIBRO >=", value, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroLessThan(String value) {
      addCriterion("TIP_LIBRO <", value, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroLessThanOrEqualTo(String value) {
      addCriterion("TIP_LIBRO <=", value, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroIn(List<String> values) {
      addCriterion("TIP_LIBRO in", values, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroNotIn(List<String> values) {
      addCriterion("TIP_LIBRO not in", values, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroBetween(String value1, String value2) {
      addCriterion("TIP_LIBRO between", value1, value2, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andTipLibroNotBetween(String value1, String value2) {
      addCriterion("TIP_LIBRO not between", value1, value2, "tipLibro");
      return (Criteria) this;
    }

    public Criteria andNomArchivoIsNull() {
      addCriterion("NOM_ARCHIVO is null");
      return (Criteria) this;
    }

    public Criteria andNomArchivoIsNotNull() {
      addCriterion("NOM_ARCHIVO is not null");
      return (Criteria) this;
    }

    public Criteria andNomArchivoEqualTo(String value) {
      addCriterion("NOM_ARCHIVO =", value, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoNotEqualTo(String value) {
      addCriterion("NOM_ARCHIVO <>", value, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoGreaterThan(String value) {
      addCriterion("NOM_ARCHIVO >", value, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoGreaterThanOrEqualTo(String value) {
      addCriterion("NOM_ARCHIVO >=", value, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoLessThan(String value) {
      addCriterion("NOM_ARCHIVO <", value, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoLessThanOrEqualTo(String value) {
      addCriterion("NOM_ARCHIVO <=", value, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoIn(List<String> values) {
      addCriterion("NOM_ARCHIVO in", values, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoNotIn(List<String> values) {
      addCriterion("NOM_ARCHIVO not in", values, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoBetween(String value1, String value2) {
      addCriterion("NOM_ARCHIVO between", value1, value2, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNomArchivoNotBetween(String value1, String value2) {
      addCriterion("NOM_ARCHIVO not between", value1, value2, "nomArchivo");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosIsNull() {
      addCriterion("NUM_REGISTROS is null");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosIsNotNull() {
      addCriterion("NUM_REGISTROS is not null");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosEqualTo(Long value) {
      addCriterion("NUM_REGISTROS =", value, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosNotEqualTo(Long value) {
      addCriterion("NUM_REGISTROS <>", value, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosGreaterThan(Long value) {
      addCriterion("NUM_REGISTROS >", value, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosGreaterThanOrEqualTo(Long value) {
      addCriterion("NUM_REGISTROS >=", value, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosLessThan(Long value) {
      addCriterion("NUM_REGISTROS <", value, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosLessThanOrEqualTo(Long value) {
      addCriterion("NUM_REGISTROS <=", value, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosIn(List<Long> values) {
      addCriterion("NUM_REGISTROS in", values, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosNotIn(List<Long> values) {
      addCriterion("NUM_REGISTROS not in", values, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosBetween(Long value1, Long value2) {
      addCriterion("NUM_REGISTROS between", value1, value2, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andNumRegistrosNotBetween(Long value1, Long value2) {
      addCriterion("NUM_REGISTROS not between", value1, value2, "numRegistros");
      return (Criteria) this;
    }

    public Criteria andEstadoIsNull() {
      addCriterion("ESTADO is null");
      return (Criteria) this;
    }

    public Criteria andEstadoIsNotNull() {
      addCriterion("ESTADO is not null");
      return (Criteria) this;
    }

    public Criteria andEstadoEqualTo(String value) {
      addCriterion("ESTADO =", value, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoNotEqualTo(String value) {
      addCriterion("ESTADO <>", value, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoGreaterThan(String value) {
      addCriterion("ESTADO >", value, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoGreaterThanOrEqualTo(String value) {
      addCriterion("ESTADO >=", value, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoLessThan(String value) {
      addCriterion("ESTADO <", value, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoLessThanOrEqualTo(String value) {
      addCriterion("ESTADO <=", value, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoIn(List<String> values) {
      addCriterion("ESTADO in", values, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoNotIn(List<String> values) {
      addCriterion("ESTADO not in", values, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoBetween(String value1, String value2) {
      addCriterion("ESTADO between", value1, value2, "estado");
      return (Criteria) this;
    }

    public Criteria andEstadoNotBetween(String value1, String value2) {
      addCriterion("ESTADO not between", value1, value2, "estado");
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
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated do_not_delete_during_merge Tue Jul 30 17:12:14 COT 2013
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.CAB_LIBRO
   *
   * @mbggenerated Tue Jul 30 17:12:14 COT 2013
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