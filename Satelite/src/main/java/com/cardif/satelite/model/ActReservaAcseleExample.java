package com.cardif.satelite.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActReservaAcseleExample implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public ActReservaAcseleExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
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

    public Criteria andPkIsNull() {
      addCriterion("PK is null");
      return (Criteria) this;
    }

    public Criteria andPkIsNotNull() {
      addCriterion("PK is not null");
      return (Criteria) this;
    }

    public Criteria andPkEqualTo(Long value) {
      addCriterion("PK =", value, "pk");
      return (Criteria) this;
    }

    public Criteria andPkNotEqualTo(Long value) {
      addCriterion("PK <>", value, "pk");
      return (Criteria) this;
    }

    public Criteria andPkGreaterThan(Long value) {
      addCriterion("PK >", value, "pk");
      return (Criteria) this;
    }

    public Criteria andPkGreaterThanOrEqualTo(Long value) {
      addCriterion("PK >=", value, "pk");
      return (Criteria) this;
    }

    public Criteria andPkLessThan(Long value) {
      addCriterion("PK <", value, "pk");
      return (Criteria) this;
    }

    public Criteria andPkLessThanOrEqualTo(Long value) {
      addCriterion("PK <=", value, "pk");
      return (Criteria) this;
    }

    public Criteria andPkIn(List<Long> values) {
      addCriterion("PK in", values, "pk");
      return (Criteria) this;
    }

    public Criteria andPkNotIn(List<Long> values) {
      addCriterion("PK not in", values, "pk");
      return (Criteria) this;
    }

    public Criteria andPkBetween(Long value1, Long value2) {
      addCriterion("PK between", value1, value2, "pk");
      return (Criteria) this;
    }

    public Criteria andPkNotBetween(Long value1, Long value2) {
      addCriterion("PK not between", value1, value2, "pk");
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

    public Criteria andDescripcionProductoIsNull() {
      addCriterion("DESCRIPCION_PRODUCTO is null");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoIsNotNull() {
      addCriterion("DESCRIPCION_PRODUCTO is not null");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoEqualTo(String value) {
      addCriterion("DESCRIPCION_PRODUCTO =", value, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoNotEqualTo(String value) {
      addCriterion("DESCRIPCION_PRODUCTO <>", value, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoGreaterThan(String value) {
      addCriterion("DESCRIPCION_PRODUCTO >", value, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoGreaterThanOrEqualTo(String value) {
      addCriterion("DESCRIPCION_PRODUCTO >=", value, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoLessThan(String value) {
      addCriterion("DESCRIPCION_PRODUCTO <", value, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoLessThanOrEqualTo(String value) {
      addCriterion("DESCRIPCION_PRODUCTO <=", value, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoIn(List<String> values) {
      addCriterion("DESCRIPCION_PRODUCTO in", values, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoNotIn(List<String> values) {
      addCriterion("DESCRIPCION_PRODUCTO not in", values, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoBetween(String value1, String value2) {
      addCriterion("DESCRIPCION_PRODUCTO between", value1, value2, "descripcionProducto");
      return (Criteria) this;
    }

    public Criteria andDescripcionProductoNotBetween(String value1, String value2) {
      addCriterion("DESCRIPCION_PRODUCTO not between", value1, value2, "descripcionProducto");
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

    public Criteria andCodTipoVentaIsNull() {
      addCriterion("COD_TIPO_VENTA is null");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaIsNotNull() {
      addCriterion("COD_TIPO_VENTA is not null");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaEqualTo(String value) {
      addCriterion("COD_TIPO_VENTA =", value, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaNotEqualTo(String value) {
      addCriterion("COD_TIPO_VENTA <>", value, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaGreaterThan(String value) {
      addCriterion("COD_TIPO_VENTA >", value, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaGreaterThanOrEqualTo(String value) {
      addCriterion("COD_TIPO_VENTA >=", value, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaLessThan(String value) {
      addCriterion("COD_TIPO_VENTA <", value, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaLessThanOrEqualTo(String value) {
      addCriterion("COD_TIPO_VENTA <=", value, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaIn(List<String> values) {
      addCriterion("COD_TIPO_VENTA in", values, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaNotIn(List<String> values) {
      addCriterion("COD_TIPO_VENTA not in", values, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaBetween(String value1, String value2) {
      addCriterion("COD_TIPO_VENTA between", value1, value2, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andCodTipoVentaNotBetween(String value1, String value2) {
      addCriterion("COD_TIPO_VENTA not between", value1, value2, "codTipoVenta");
      return (Criteria) this;
    }

    public Criteria andFecCierreIsNull() {
      addCriterion("FEC_CIERRE is null");
      return (Criteria) this;
    }

    public Criteria andFecCierreIsNotNull() {
      addCriterion("FEC_CIERRE is not null");
      return (Criteria) this;
    }

    public Criteria andFecCierreEqualTo(Date value) {
      addCriterion("FEC_CIERRE =", value, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreNotEqualTo(Date value) {
      addCriterion("FEC_CIERRE <>", value, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreGreaterThan(Date value) {
      addCriterion("FEC_CIERRE >", value, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_CIERRE >=", value, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreLessThan(Date value) {
      addCriterion("FEC_CIERRE <", value, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreLessThanOrEqualTo(Date value) {
      addCriterion("FEC_CIERRE <=", value, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreIn(List<Date> values) {
      addCriterion("FEC_CIERRE in", values, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreNotIn(List<Date> values) {
      addCriterion("FEC_CIERRE not in", values, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreBetween(Date value1, Date value2) {
      addCriterion("FEC_CIERRE between", value1, value2, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecCierreNotBetween(Date value1, Date value2) {
      addCriterion("FEC_CIERRE not between", value1, value2, "fecCierre");
      return (Criteria) this;
    }

    public Criteria andFecInicioIsNull() {
      addCriterion("FEC_INICIO is null");
      return (Criteria) this;
    }

    public Criteria andFecInicioIsNotNull() {
      addCriterion("FEC_INICIO is not null");
      return (Criteria) this;
    }

    public Criteria andFecInicioEqualTo(Date value) {
      addCriterion("FEC_INICIO =", value, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioNotEqualTo(Date value) {
      addCriterion("FEC_INICIO <>", value, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioGreaterThan(Date value) {
      addCriterion("FEC_INICIO >", value, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_INICIO >=", value, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioLessThan(Date value) {
      addCriterion("FEC_INICIO <", value, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioLessThanOrEqualTo(Date value) {
      addCriterion("FEC_INICIO <=", value, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioIn(List<Date> values) {
      addCriterion("FEC_INICIO in", values, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioNotIn(List<Date> values) {
      addCriterion("FEC_INICIO not in", values, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioBetween(Date value1, Date value2) {
      addCriterion("FEC_INICIO between", value1, value2, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecInicioNotBetween(Date value1, Date value2) {
      addCriterion("FEC_INICIO not between", value1, value2, "fecInicio");
      return (Criteria) this;
    }

    public Criteria andFecFinIsNull() {
      addCriterion("FEC_FIN is null");
      return (Criteria) this;
    }

    public Criteria andFecFinIsNotNull() {
      addCriterion("FEC_FIN is not null");
      return (Criteria) this;
    }

    public Criteria andFecFinEqualTo(Date value) {
      addCriterion("FEC_FIN =", value, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinNotEqualTo(Date value) {
      addCriterion("FEC_FIN <>", value, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinGreaterThan(Date value) {
      addCriterion("FEC_FIN >", value, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinGreaterThanOrEqualTo(Date value) {
      addCriterion("FEC_FIN >=", value, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinLessThan(Date value) {
      addCriterion("FEC_FIN <", value, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinLessThanOrEqualTo(Date value) {
      addCriterion("FEC_FIN <=", value, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinIn(List<Date> values) {
      addCriterion("FEC_FIN in", values, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinNotIn(List<Date> values) {
      addCriterion("FEC_FIN not in", values, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinBetween(Date value1, Date value2) {
      addCriterion("FEC_FIN between", value1, value2, "fecFin");
      return (Criteria) this;
    }

    public Criteria andFecFinNotBetween(Date value1, Date value2) {
      addCriterion("FEC_FIN not between", value1, value2, "fecFin");
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
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated do_not_delete_during_merge Fri Jan 03 13:36:12 COT 2014
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table dbo.ACT_RESERVA_ACSELE
   *
   * @mbggenerated Fri Jan 03 13:36:12 COT 2014
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