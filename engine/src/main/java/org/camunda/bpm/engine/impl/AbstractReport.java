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
package org.camunda.bpm.engine.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.history.report.DurationReportResult;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.db.AuthorizationCheck;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.util.EnsureUtil;
import org.camunda.bpm.engine.query.Report;
import org.camunda.bpm.engine.query.ReportPeriodUnit;

/**
 * @author Roman Smirnov
 *
 */
public abstract class AbstractReport<T extends Report<?,?>, D extends DurationReportResult> extends AuthorizationCheck implements Command<Object>, Report<T,D>, Serializable {

  private static final long serialVersionUID = 1L;

  private static enum ReportType {
    DURATION
  }
  protected transient CommandExecutor commandExecutor;
  protected ReportType reportType;

  protected ReportPeriodUnit reportPeriodUnit;
  protected Date startedAfter;
  protected Date startedBefore;

  protected AbstractReport() {
  }

  protected AbstractReport(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  public Object execute(CommandContext commandContext) {
    return executeDuration(commandContext);
  }

  @SuppressWarnings("unchecked")
  public List<D> duration(ReportPeriodUnit periodUnit) {
    reportType = ReportType.DURATION;
    reportPeriodUnit = periodUnit;
    if (commandExecutor!=null) {
      return (List<D>) commandExecutor.execute(this);
    }
    return executeDuration(Context.getCommandContext());
  }

  public abstract List<D> executeDuration(CommandContext commandContext);

  @SuppressWarnings("unchecked")
  public T startedAfter(Date startedAfter) {
    EnsureUtil.ensureNotNull(NotValidException.class, "startedAfter", startedAfter);
    this.startedAfter = startedAfter;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T startedBefore(Date startedBefore) {
    EnsureUtil.ensureNotNull(NotValidException.class, "startedBefore", startedBefore);
    this.startedBefore = startedBefore;
    return (T) this;
  }

  // getter / setter ///////////////////////////////////////////////////

  public CommandExecutor getCommandExecutor() {
    return commandExecutor;
  }

  public AbstractReport<T, D> setCommandExecutor(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
    return this;
  }

  public ReportPeriodUnit getReportPeriodUnit() {
    return reportPeriodUnit;
  }

  public String getReportPeriodUnitName() {
    return getReportPeriodUnit().toString();
  }

  public ReportType getReportType() {
    return reportType;
  }

  public String getReportTypeName() {
    return getReportType().toString();
  }

  public Date getStartedAfter() {
    return startedAfter;
  }

  public Date getStartedBefore() {
    return startedBefore;
  }

}
