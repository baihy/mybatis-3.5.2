/**
 * Copyright 2009-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.session;

/**
 * @author Clinton Begin
 */
public enum ExecutorType {
    SIMPLE, REUSE, BATCH
    /**
     * 默认的执行类是SIMPLE
     *
     * SIMPLE：这个类型不做特殊的事情，它只为每个语句创建一个PreparedStatement。
     * REUSE：这种类型将重复使用PreparedStatements。
     * BATCH：这个类型批量更新，且必要地区别开其中的select 语句，确保动作易于理解。
     */
}
