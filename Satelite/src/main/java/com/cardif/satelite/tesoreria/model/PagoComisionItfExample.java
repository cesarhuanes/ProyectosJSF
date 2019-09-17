package com.cardif.satelite.tesoreria.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagoComisionItfExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public PagoComisionItfExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

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

    public Criteria andNom_excelIsNull() {
      addCriterion("nom_excel is null");
      return (Criteria) this;
    }

    public Criteria andNom_excelIsNotNull() {
      addCriterion("nom_excel is not null");
      return (Criteria) this;
    }

    public Criteria andNom_excelEqualTo(String value) {
      addCriterion("nom_excel =", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelNotEqualTo(String value) {
      addCriterion("nom_excel <>", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelGreaterThan(String value) {
      addCriterion("nom_excel >", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelGreaterThanOrEqualTo(String value) {
      addCriterion("nom_excel >=", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelLessThan(String value) {
      addCriterion("nom_excel <", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelLessThanOrEqualTo(String value) {
      addCriterion("nom_excel <=", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelLike(String value) {
      addCriterion("nom_excel like", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelNotLike(String value) {
      addCriterion("nom_excel not like", value, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelIn(List<String> values) {
      addCriterion("nom_excel in", values, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelNotIn(List<String> values) {
      addCriterion("nom_excel not in", values, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelBetween(String value1, String value2) {
      addCriterion("nom_excel between", value1, value2, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andNom_excelNotBetween(String value1, String value2) {
      addCriterion("nom_excel not between", value1, value2, "nom_excel");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionIsNull() {
      addCriterion("usu_creacion is null");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionIsNotNull() {
      addCriterion("usu_creacion is not null");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionEqualTo(String value) {
      addCriterion("usu_creacion =", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionNotEqualTo(String value) {
      addCriterion("usu_creacion <>", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionGreaterThan(String value) {
      addCriterion("usu_creacion >", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionGreaterThanOrEqualTo(String value) {
      addCriterion("usu_creacion >=", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionLessThan(String value) {
      addCriterion("usu_creacion <", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionLessThanOrEqualTo(String value) {
      addCriterion("usu_creacion <=", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionLike(String value) {
      addCriterion("usu_creacion like", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionNotLike(String value) {
      addCriterion("usu_creacion not like", value, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionIn(List<String> values) {
      addCriterion("usu_creacion in", values, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionNotIn(List<String> values) {
      addCriterion("usu_creacion not in", values, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionBetween(String value1, String value2) {
      addCriterion("usu_creacion between", value1, value2, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_creacionNotBetween(String value1, String value2) {
      addCriterion("usu_creacion not between", value1, value2, "usu_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionIsNull() {
      addCriterion("fec_creacion is null");
      return (Criteria) this;
    }

    public Criteria andFec_creacionIsNotNull() {
      addCriterion("fec_creacion is not null");
      return (Criteria) this;
    }

    public Criteria andFec_creacionEqualTo(Date value) {
      addCriterion("fec_creacion =", value, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionNotEqualTo(Date value) {
      addCriterion("fec_creacion <>", value, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionGreaterThan(Date value) {
      addCriterion("fec_creacion >", value, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionGreaterThanOrEqualTo(Date value) {
      addCriterion("fec_creacion >=", value, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionLessThan(Date value) {
      addCriterion("fec_creacion <", value, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionLessThanOrEqualTo(Date value) {
      addCriterion("fec_creacion <=", value, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionIn(List<Date> values) {
      addCriterion("fec_creacion in", values, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionNotIn(List<Date> values) {
      addCriterion("fec_creacion not in", values, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionBetween(Date value1, Date value2) {
      addCriterion("fec_creacion between", value1, value2, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andFec_creacionNotBetween(Date value1, Date value2) {
      addCriterion("fec_creacion not between", value1, value2, "fec_creacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionIsNull() {
      addCriterion("usu_modificacion is null");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionIsNotNull() {
      addCriterion("usu_modificacion is not null");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionEqualTo(String value) {
      addCriterion("usu_modificacion =", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionNotEqualTo(String value) {
      addCriterion("usu_modificacion <>", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionGreaterThan(String value) {
      addCriterion("usu_modificacion >", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionGreaterThanOrEqualTo(String value) {
      addCriterion("usu_modificacion >=", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionLessThan(String value) {
      addCriterion("usu_modificacion <", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionLessThanOrEqualTo(String value) {
      addCriterion("usu_modificacion <=", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionLike(String value) {
      addCriterion("usu_modificacion like", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionNotLike(String value) {
      addCriterion("usu_modificacion not like", value, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionIn(List<String> values) {
      addCriterion("usu_modificacion in", values, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionNotIn(List<String> values) {
      addCriterion("usu_modificacion not in", values, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionBetween(String value1, String value2) {
      addCriterion("usu_modificacion between", value1, value2, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andUsu_modificacionNotBetween(String value1, String value2) {
      addCriterion("usu_modificacion not between", value1, value2, "usu_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionIsNull() {
      addCriterion("fec_modificacion is null");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionIsNotNull() {
      addCriterion("fec_modificacion is not null");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionEqualTo(Date value) {
      addCriterion("fec_modificacion =", value, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionNotEqualTo(Date value) {
      addCriterion("fec_modificacion <>", value, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionGreaterThan(Date value) {
      addCriterion("fec_modificacion >", value, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionGreaterThanOrEqualTo(Date value) {
      addCriterion("fec_modificacion >=", value, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionLessThan(Date value) {
      addCriterion("fec_modificacion <", value, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionLessThanOrEqualTo(Date value) {
      addCriterion("fec_modificacion <=", value, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionIn(List<Date> values) {
      addCriterion("fec_modificacion in", values, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionNotIn(List<Date> values) {
      addCriterion("fec_modificacion not in", values, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionBetween(Date value1, Date value2) {
      addCriterion("fec_modificacion between", value1, value2, "fec_modificacion");
      return (Criteria) this;
    }

    public Criteria andFec_modificacionNotBetween(Date value1, Date value2) {
      addCriterion("fec_modificacion not between", value1, value2, "fec_modificacion");
      return (Criteria) this;
    }
  }

  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

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