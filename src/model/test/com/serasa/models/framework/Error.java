/*
 * CS-BusinessRestrictions-BR
 * Exposes consumer data restrictions for third party consults.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: suporte-api-credit-services@br.experian.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.serasa.models.framework;

import java.util.Objects;
import com.serasa.models.framework.ErrorInner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

/**
 * Error
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-08T16:19:00.622-03:00")
public class Error extends ArrayList<ErrorInner>  {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

