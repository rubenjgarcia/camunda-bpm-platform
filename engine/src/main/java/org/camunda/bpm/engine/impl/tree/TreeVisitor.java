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
package org.camunda.bpm.engine.impl.tree;

/**
 * A visitor for {@link TreeWalker}.
 *
 * @author Thorben Lindhauer
 *
 */
public interface TreeVisitor<T> {

  /**
   * Invoked for a node in tree.
   *
   * @param obj a reference to the node
   */
  void visit(T obj);
}