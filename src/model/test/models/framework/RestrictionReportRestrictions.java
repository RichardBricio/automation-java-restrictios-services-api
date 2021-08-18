package models.framework;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * RestrictionReportRestrictions
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-08T16:19:00.622-03:00")
public class RestrictionReportRestrictions   {
  @JsonProperty("all")
  private Boolean all = null;

  public RestrictionReportRestrictions all(Boolean all) {
    this.all = all;
    return this;
  }

  /**
   * Consumer is restrict, so his data should not be shown.
   * @return all
   **/
  @JsonProperty("all")
  @ApiModelProperty(value = "Consumer is restrict, so his data should not be shown.")
  public Boolean isAll() {
    return all;
  }

  public void setAll(Boolean all) {
    this.all = all;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestrictionReportRestrictions restrictionReportRestrictions = (RestrictionReportRestrictions) o;
    return Objects.equals(this.all, restrictionReportRestrictions.all);
  }

  @Override
  public int hashCode() {
    return Objects.hash(all);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestrictionReportRestrictions {\n");
    
    sb.append("    all: ").append(toIndentedString(all)).append("\n");
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

