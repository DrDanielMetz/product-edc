/*
 * Copyright (c) 2022 Mercedes-Benz Tech Innovation GmbH
 * Copyright (c) 2021,2022 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.tractusx.edc.tests;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.tractusx.edc.tests.data.ContractDefinition;

public class ContractDefinitionStepDefs {

  @Given("'{connector}' has the following contract definitions")
  public void hasPolicies(Connector connector, DataTable table) throws Exception {
    final DataManagementAPI api = connector.getDataManagementAPI();
    final List<ContractDefinition> contractDefinitions = parseDataTable(table);

    for (ContractDefinition contractDefinition : contractDefinitions)
      api.createContractDefinition(contractDefinition);
  }

  private List<ContractDefinition> parseDataTable(DataTable table) {
    final List<ContractDefinition> contractDefinitions = new ArrayList<>();

    for (Map<String, String> map : table.asMaps()) {
      String id = map.get("id");
      String accessPolicyId = map.get("access policy");
      String contractPolicyId = map.get("contract policy");
      String assetId = map.get("asset");
      // If the tests do not have a specified validity, the value will be 1 Year in seconds.
      String mapValidity = map.get("validity");
      Long validity = mapValidity.equals(null) ? 32140800 : Long.parseLong(mapValidity);
      List<String> assetIds = assetId == null ? new ArrayList<>() : List.of(assetId);

      contractDefinitions.add(
          new ContractDefinition(id, contractPolicyId, accessPolicyId, validity, assetIds));
    }

    return contractDefinitions;
  }
}
