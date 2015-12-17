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

import java.util.List;

import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.history.report.DurationReportResult;
import org.camunda.bpm.engine.history.report.HistoricProcessInstanceReport;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.util.EnsureUtil;

/**
 * @author Roman Smirnov
 *
 */
public class HistoricProcessInstanceReportImpl extends AbstractReport<HistoricProcessInstanceReport, DurationReportResult> implements HistoricProcessInstanceReport {

  private static final long serialVersionUID = 1L;

  protected String[] processDefinitionIds;
  protected String[] processDefinitionKeys;

  public HistoricProcessInstanceReportImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  // query parameter ///////////////////////////////////////////////

  public HistoricProcessInstanceReport processDefinitionIdIn(String... processDefinitionIds) {
    EnsureUtil.ensureNotNull(NotValidException.class, "", "processDefinitionIdIn", (Object[]) processDefinitionIds);
    this.processDefinitionIds = processDefinitionIds;
    return this;
  }

  public HistoricProcessInstanceReport processDefinitionKeyIn(String... processDefinitionKeys) {
    EnsureUtil.ensureNotNull(NotValidException.class, "", "processDefinitionKeyIn", (Object[]) processDefinitionKeys);
    this.processDefinitionKeys = processDefinitionKeys;
    return this;
  }

  // report execution /////////////////////////////////////////////

  public List<DurationReportResult> executeDuration(CommandContext commandContext) {
    return commandContext
      .getHistoricReportManager()
      .createHistoricProcessInstanceDurationReport(this);
  }

  // getter //////////////////////////////////////////////////////

  public String[] getProcessDefinitionIds() {
    return processDefinitionIds;
  }

  public String[] getProcessDefinitionKeys() {
    return processDefinitionKeys;
  }

}
