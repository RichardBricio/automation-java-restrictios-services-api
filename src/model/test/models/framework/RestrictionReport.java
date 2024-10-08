package models.framework;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import models.framework.RestrictionReportRestrictions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * RestrictionReport
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-08T16:19:00.622-03:00")
public class RestrictionReport   {
  @JsonProperty("restrictions")
  private RestrictionReportRestrictions restrictions = null;

  public RestrictionReport restrictions(RestrictionReportRestrictions restrictions) {
    this.restrictions = restrictions;
    return this;
  }

  /**
   * Get restrictions
   * @return restrictions
   **/
  @JsonProperty("restrictions")
  @ApiModelProperty(value = "")
  public RestrictionReportRestrictions getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(RestrictionReportRestrictions restrictions) {
    this.restrictions = restrictions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestrictionReport restrictionReport = (RestrictionReport) o;
    return Objects.equals(this.restrictions, restrictionReport.restrictions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(restrictions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestrictionReport {\n");
    
    sb.append("    restrictions: ").append(toIndentedString(restrictions)).append("\n");
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

