package com.cardif.satelite.seguridad.bean;

import org.springframework.security.core.GrantedAuthority;

public class SyncconAuthority implements GrantedAuthority {

  /**
   * 
   */
  private static final long serialVersionUID = 9021904570078567134L;
  private String authority;

  public SyncconAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  @Override
  public int hashCode() {
    return authority.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!(obj instanceof SyncconAuthority))
      return false;
    return ((SyncconAuthority) obj).getAuthority().equals(authority);
  }

  // ROLE_ADMIN, ROLE_COBRANZA_JEFE, ROLE_IMPUESTOS_JEFE, ROLE_SUSCRIPCION_JEFE,
  // ROLE_ACTUARIAL_JEFE, ROLE_RRHH_JEFE, ROLE_TLMK_JEFE, ROLE_TI_JEFE,
  // ROLE_COBRANZA, ROLE_IMPUESTOS, ROLE_SUSCRIPCION, ROLE_RRHH, ROLE_ACTUARIAL,
  // ROLE_TLMK, ROLE_TI, ROLE_SAC_JEFE, ROLE_SAC, ROLE_SINI_SEG_JEFE,
  // ROLE_SINI_SEG, ROLE_SINI_EXTRA_JEFE, ROLE_SINI_SALUD_JEFE,
  // ROLE_CONT_OPERA_JEFE, ROLE_CONT_FINAN_JEFE, ROLE_TESO_JEFE,
  // ROLE_SINI_EXTRA, ROLE_SINI_SALUD, ROLE_CONT_OPERA, ROLE_CONT_FINAN,
  // ROLE_TESO;

}
