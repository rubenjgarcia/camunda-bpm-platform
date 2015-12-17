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
package org.camunda.bpm.engine.test.history.report;

import java.text.SimpleDateFormat;
import java.util.List;

import org.camunda.bpm.engine.history.report.DurationReportResult;
import org.camunda.bpm.engine.impl.test.PluggableProcessEngineTestCase;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.query.ReportPeriodUnit;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

/**
 * @author Roman Smirnov
 *
 */
public class HistoricProcessInstanceDurationTest extends PluggableProcessEngineTestCase {

  public void testDurationReport() throws Exception {

    Deployment deployment = repositoryService
        .createDeployment()
        .addModelInstance("path/to/my/process.bpmn", createProcessWithUserTask("process"))
        .deploy();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    ClockUtil.setCurrentTime(sdf.parse("03/01/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("04/01/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("04/01/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("04/12/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/02/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/02/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("06/02/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("09/02/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/03/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/04/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/03/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/03/2015 02:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/04/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("06/04/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/04/2015 01:05:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/04/2015 01:10:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/05/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/05/2015 15:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("15/05/2015 01:05:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("16/05/2015 01:10:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/06/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/08/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/06/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/08/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/07/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/07/2015 20:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/08/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/09/2015 05:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/09/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/09/2015 11:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/10/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/10/2015 10:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/11/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/11/2015 08:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    ClockUtil.setCurrentTime(sdf.parse("03/12/2015 01:01:01.000"));
    runtimeService.startProcessInstanceByKey("process");
    ClockUtil.setCurrentTime(sdf.parse("03/12/2015 08:30:01.000"));
    taskService.complete(taskService.createTaskQuery().singleResult().getId());

    List<DurationReportResult> result = historyService
        .createHistoricProcessInstanceReport()
        .processDefinitionKeyIn("process")
        .duration(ReportPeriodUnit.QUARTER);

    repositoryService.deleteDeployment(deployment.getId(), true);
  }

  protected BpmnModelInstance createProcessWithUserTask(String key) {
    return Bpmn.createExecutableProcess(key)
      .startEvent()
      .userTask()
      .endEvent()
    .done();
  }

}
