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

import type { IJsonItem } from '@/components/dynamic-form/types'

export function useCDCList(item: any) {
  const { t } = useLocaleHooks()

  const synchronizationTypeOptions = [
    {
      label: t('cdc.single_table_synchronization'),
      value: 0,
    },
    {
      label: t('cdc.whole_database_synchronization'),
      value: 1,
    },
  ]

  const dataDelayModelOptions = [
    {
      label: '1min',
      value: 60 * 1000,
    },
    {
      label: '5min',
      value: 5 * 60 * 1000,
    },
    {
      label: '10min',
      value: 10 * 60 * 1000,
    },
    {
      label: '15min',
      value: 15 * 60 * 1000,
    },
    {
      label: '30min',
      value: 30 * 60 * 1000,
    },
    {
      label: '1hour',
      value: 60 * 60 * 1000,
    },
  ]

  const data = item.data
  const model = reactive({
    name: data?.name,
    description: data?.description,
    cdcType: data?.cdcType || 0,
    dataDelay: data?.dataDelay || 60 * 1000,
  })

  return {
    json: [
      {
        type: 'input',
        field: 'name',
        name: t('cdc.synchronization_job_name'),
        props: {
          placeholder: '',
        },
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
        type: 'input',
        field: 'description',
        name: t('cdc.task_description'),
        props: {
          placeholder: '',
          type: 'textarea',
        },
      },
      {
        type: 'select',
        field: 'dataDelay',
        name: t('cdc.data_delay_option'),
        options: dataDelayModelOptions,
        value: model.dataDelay,
      },
      {
        type: 'radio',
        field: 'cdcType',
        name: t('cdc.synchronization_type'),
        options: synchronizationTypeOptions,
        value: model.cdcType,
      },
    ] as IJsonItem[],
    model,
  }
}
