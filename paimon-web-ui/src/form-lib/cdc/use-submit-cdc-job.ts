/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License. */

import type { SelectOption } from 'naive-ui'
import type { CdcJobSubmit } from '@/api/models/cdc'
import { getClusterListByType } from '@/api/models/cluster'
import type { Cluster } from '@/api/models/cluster/types'
import type { IJsonItem } from '@/components/dynamic-form/types'

export function useSumbitCdcJob(item: any) {
  const { t } = useLocaleHooks()

  const model = reactive<CdcJobSubmit>({
    flinkSessionUrl: item.flinkSessionUrl,
    startupMode: item.startupMode,
    startupTimestamp: item.startupTimestamp,
  })

  const flinkSessionClusterOptions = ref<SelectOption[]>([])
  getClusterListByType ('Flink', 1, Number.MAX_SAFE_INTEGER).then((response) => {
    if (response && response.data) {
      const clusterList = response.data as Cluster[]
      flinkSessionClusterOptions.value = clusterList.map(cluster => ({
        label: cluster.clusterName,
        value: cluster.id.toString(),
      }))
    }
  })

  const synchronizationModeOptions = [
    {
      label: t('cdc.inc_synchronization'),
      value: 0,
    },
    {
      label: t('cdc.full_synchronization'),
      value: 1,
    },
    {
      label: t('cdc.ts_synchronization'),
      value: 2,
    }

  ]

  return {
    json: [
      {
        type: 'select',
        field: 'clusterId',
        name: t('common.flink_session_url'),
        props: {
          placeholder: '',
        },
        options: flinkSessionClusterOptions,
        validate: {
          trigger: ['input', 'blur'],
          required: true,
          message: 'error',
          validator: (validator: any, value: string) => {
            if (!value)
              return new Error('error')
          },
        },
      },
      {
        type: 'radio',
        field: 'startupMode',
        name: t('cdc.synchronization_mode'),
        options: synchronizationModeOptions,
        value: 0,
      },
      {
        type: 'input',
        field: 'startupTimestamp',
        name: '指定时间',
        props: {
          placeholder: '选择同步模式为指定时间时填写，13位时间戳'
        },
      },
    ] as IJsonItem[],
    model,
  }
}
