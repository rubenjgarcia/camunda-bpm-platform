/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.report.ReportResult;
import org.camunda.bpm.engine.query.Report;
import org.camunda.bpm.engine.query.ReportPeriodUnit;
import org.camunda.bpm.engine.rest.dto.converter.DateConverter;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.engine.rest.exception.RestException;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Roman Smirnov
 *
 */
public abstract class AbstractReportDto<T extends Report<?, ?>> extends AbstractSearchQueryDto {

  public static final String DURATION_REPORT_TYPE = "duration";

  public static final List<String> VALID_REPORT_TYPE_VALUES;
  static {
    VALID_REPORT_TYPE_VALUES = new ArrayList<String>();
    VALID_REPORT_TYPE_VALUES.add(DURATION_REPORT_TYPE);
  }

  protected Date startedBefore;
  protected Date startedAfter;

  protected String reportType;
  protected ReportPeriodUnit periodUnit;

  // required for populating via jackson
  public AbstractReportDto() {

  }

  public AbstractReportDto(ObjectMapper objectMapper, MultivaluedMap<String, String> queryParameters) {
    super(objectMapper, queryParameters);
  }

  @CamundaQueryParam(value = "startedAfter", converter = DateConverter.class)
  public void setStartedAfter(Date startedAfter) {
    this.startedAfter = startedAfter;
  }

  @CamundaQueryParam(value = "startedBefore", converter = DateConverter.class)
  public void setStartedBefore(Date startedBefore) {
    this.startedBefore = startedBefore;
  }

  @CamundaQueryParam("reportType")
  public void setReportType(String reportType) {
    if (!VALID_REPORT_TYPE_VALUES.contains(reportType)) {
      throw new InvalidRequestException(Status.BAD_REQUEST, "reportType parameter has invalid value: " + reportType);
    }
    this.reportType = reportType;
  }

  @CamundaQueryParam("periodUnit")
  public void setPeriodUnit(String periodUnit) {
    // TODO: this feels bad! do it in a different! ;)
    ReportPeriodUnit unit = null;
    try {
      unit = ReportPeriodUnit.valueOf(periodUnit.toUpperCase());
    }
    catch (Exception e) {
      throw new InvalidRequestException(Status.BAD_REQUEST, e, "periodUnit parameter has invalid value: " + periodUnit);
    }

    this.periodUnit = unit;
  }

  @SuppressWarnings("unchecked")
  public List<ReportResult> executeReport(ProcessEngine engine) {
    T reportQuery = createNewReportQuery(engine);
    applyStandardFilters(reportQuery);
    applyFilters(reportQuery);

    if (periodUnit == null) {
      throw new RestException("Period unit is null");
    }

    if (DURATION_REPORT_TYPE.equals(reportType)) {
      return (List<ReportResult>) reportQuery.duration(periodUnit);
    }

    throw new RestException("Unknown query report type " + reportType);
  }

  protected void applyStandardFilters(T reportQuery) {
    if (startedAfter != null) {
      reportQuery.startedAfter(startedAfter);
    }

    if (startedBefore != null) {
      reportQuery.startedBefore(startedBefore);
    }
  }

  protected abstract T createNewReportQuery(ProcessEngine engine);

  protected abstract void applyFilters(T reportQuery);

}
